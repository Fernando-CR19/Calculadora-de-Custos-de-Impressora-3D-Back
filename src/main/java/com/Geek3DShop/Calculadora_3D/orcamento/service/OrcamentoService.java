package com.Geek3DShop.Calculadora_3D.orcamento.service;

import com.Geek3DShop.Calculadora_3D.acabamento.repository.AcabamentoRepository;
import com.Geek3DShop.Calculadora_3D.consumivel.repository.ConsumivelRepository;
import com.Geek3DShop.Calculadora_3D.desperdicio.repository.NivelDesperdicioRepository;
import com.Geek3DShop.Calculadora_3D.energia.repository.EnergiaRepository;
import com.Geek3DShop.Calculadora_3D.filamento.repository.FilamentoRepository;
import com.Geek3DShop.Calculadora_3D.impressora.repository.ImpressoraRepository;
import com.Geek3DShop.Calculadora_3D.lucro.repository.MargemLucroRepository;
import com.Geek3DShop.Calculadora_3D.material.entity.Material;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.CustoFilamentoDto;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.FilamentoUsadoDto;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.OrcamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.OrcamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.potencia.repository.PotenciaImpressoraRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.BusinessRuleException;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrcamentoService {
    private final AcabamentoRepository acabamentoRepository;
    private final ConsumivelRepository consumivelRepository;
    private final NivelDesperdicioRepository nivelDesperdicioRepository;
    private final EnergiaRepository energiaRepository;
    private final FilamentoRepository filamentoRepository;
    private final ImpressoraRepository impressoraRepository;
    private final MargemLucroRepository margemLucroRepository;
    private final PotenciaImpressoraRepository potenciaImpressoraRepository;

    @Transactional(readOnly = true)
    public OrcamentoResponseDto calcularOrcamento(OrcamentoRequestDto orcamentoRequestDto) {
        var resultadoFilamentos = this.calcularFilamentos(orcamentoRequestDto.filamentos());

        var resultadoImpressao = this.calcularCustoImpressao(
                orcamentoRequestDto.horasDeImpressao(),
                orcamentoRequestDto.minutosDeImpressao(),
                orcamentoRequestDto.uuidImpressora(),
                resultadoFilamentos.material()
        );

        var custoDesperdicio = this.calcularDesperdicio(orcamentoRequestDto.uuidNivelDesperdicio(), resultadoFilamentos.custoTotalFilamento());

        var custoBase = resultadoFilamentos.custoTotalFilamento()
                .add(custoDesperdicio)
                .add(resultadoImpressao.custoEnergia())
                .add(resultadoImpressao.depreciacao());

        var custoAcabamento = this.calcularAcabamento(orcamentoRequestDto.horasDeAcabamento(), orcamentoRequestDto.minutosDeAcabamento());

        var custoConsumiveis = this.calcularConsumiveis(orcamentoRequestDto.uuidsConsumiveis());

        var custoTotal = custoBase.add(custoAcabamento).add(custoConsumiveis);

        var fatorLucro = this.buscarFatorLucro(orcamentoRequestDto.uuidMargemLucro());

        var valorSugerido = custoTotal.multiply(fatorLucro);

        return new OrcamentoResponseDto(
                resultadoFilamentos.custosFilamento(),
                resultadoImpressao.tempoImpressao(),
                resultadoImpressao.energiaKwh(),
                resultadoFilamentos.custoTotalFilamento().setScale(2, RoundingMode.HALF_UP),
                custoDesperdicio.setScale(2, RoundingMode.HALF_UP),
                resultadoImpressao.custoEnergia().setScale(2, RoundingMode.HALF_UP),
                resultadoImpressao.depreciacao().setScale(2, RoundingMode.HALF_UP),
                custoBase.setScale(2, RoundingMode.HALF_UP),
                custoAcabamento.setScale(2, RoundingMode.HALF_UP),
                custoConsumiveis.setScale(2, RoundingMode.HALF_UP),
                custoTotal.setScale(2, RoundingMode.HALF_UP),
                fatorLucro,
                valorSugerido.setScale(2, RoundingMode.HALF_UP)
        );
    }

    private ResultadoFilamento calcularFilamentos(List<FilamentoUsadoDto> filamentosUsados) {
        var custoFilamentos = new ArrayList<CustoFilamentoDto>();
        var custoTotalFilamento = BigDecimal.ZERO;
        Material material = null;

        for (FilamentoUsadoDto filamentoUsado : filamentosUsados) {
            // Busca pelo filamento
            var filamentoUuid = filamentoUsado.uuid();
            var filamentoFound = filamentoRepository.findByUuidAndActiveTrueAndDeletedFalse(filamentoUuid).orElseThrow(
                    () -> new ResourceNotFoundException("Filamento: " + filamentoUuid + " não encontrado")
            );

            // Análise de mesmo material.
            if (material == null) {
                material = filamentoFound.getMaterial();
            } else if (!filamentoFound.getMaterial().getId().equals(material.getId())) {
                throw new BusinessRuleException("No momento, o orçamento aceita apenas filamentos do mesmo material, para peças com materiais diferentes, faça um orçamento para cada parte");
            }

            // Cálculo de custo por filamento
            var precoKg = filamentoFound.getPrecoKg();
            var gramasUsadas = filamentoUsado.gramas();
            BigDecimal divisor = new BigDecimal("1000");

            var custoDoFilamento = precoKg.multiply(gramasUsadas).divide(divisor, 4, RoundingMode.HALF_UP);
            custoTotalFilamento = custoTotalFilamento.add(custoDoFilamento);


            CustoFilamentoDto custoFilamento = new CustoFilamentoDto(
                    filamentoFound.getCor(),
                    filamentoFound.getMaterial().getNome(),
                    gramasUsadas,
                    custoDoFilamento.setScale(2, RoundingMode.HALF_UP)
            );

            custoFilamentos.add(custoFilamento);
        }

        return new ResultadoFilamento(custoFilamentos, custoTotalFilamento, material);
    }

    private ResultadoImpressao calcularCustoImpressao(Integer horasDeImpressao, Integer minutosDeImpressao, UUID uuidImpressora, Material material) {
        // Converter o tempo total de impressão para BigDecimal
        BigDecimal horasDecimal = BigDecimal.valueOf(horasDeImpressao);
        BigDecimal minutosDecimal = BigDecimal.valueOf(minutosDeImpressao);
        BigDecimal horaEmMinuto = new BigDecimal("60");

        var tempoImpressao = horasDecimal.add(minutosDecimal.divide(horaEmMinuto, 4, RoundingMode.HALF_UP));

        // Busca a impressora e potência
        var impressoraFound = impressoraRepository.findByUuidAndActiveTrueAndDeletedFalse(uuidImpressora).orElseThrow(
                () -> new ResourceNotFoundException("Impressora: " + uuidImpressora + " não foi encontrada")
        );
        var potenciaImpressoraFound = potenciaImpressoraRepository.findByImpressoraUuidAndImpressoraActiveTrueAndImpressoraDeletedFalseAndMaterialUuidAndMaterialActiveTrueAndMaterialDeletedFalse(impressoraFound.getUuid(), material.getUuid()).orElseThrow(
                () -> new ResourceNotFoundException("Não há potência cadastrada para: " + impressoraFound.getNome() + " com material: " + material.getNome())
        );

        // Cálculo da energia gasta durante a impressão
        var potenciaMediaImpressora = potenciaImpressoraFound.getPotenciaMediaW();
        var potenciaMediaAms = impressoraFound.getPotenciaMediaAmsW();
        var potenciaMediaW = potenciaMediaImpressora.add(potenciaMediaAms);

        BigDecimal divisor = new BigDecimal("1000");
        var energiaWh = potenciaMediaW.multiply(tempoImpressao);
        var energiaKwh = energiaWh.divide(divisor, 4, RoundingMode.HALF_UP);

        var energia = energiaRepository.findFirstByOrderByIdAsc().orElseThrow(
                () -> new ResourceNotFoundException("Não há fatura de energia cadastrada")
        );

        var precokwh = energia.calcularPrecoKwh();
        var custoEnergia = energiaKwh.multiply(precokwh);

        // Cálculo da depreciação
        var depreciacaoPorHora = impressoraFound.calcularDepreciacaoPorHora();
        var depreciacao = tempoImpressao.multiply(depreciacaoPorHora);

        return new ResultadoImpressao(tempoImpressao, energiaKwh, custoEnergia, depreciacao);
    }

    private BigDecimal calcularDesperdicio(UUID uuidNivelDesperdicio, BigDecimal custoTotalFilamento) {
        BigDecimal divisor = new BigDecimal("100");

        var nivelDesperdicio = nivelDesperdicioRepository.findByUuidAndDeletedFalseAndActiveTrue(uuidNivelDesperdicio).orElseThrow(
                () -> new ResourceNotFoundException("Nível de desperdício: " + uuidNivelDesperdicio + " não foi encontrado")
        );

        var desperdicio = custoTotalFilamento.multiply(nivelDesperdicio.getPercentual());
        return desperdicio.divide(divisor, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularAcabamento(Integer horasDeAcabamento, Integer minutosDeAcabamento) {
        if (horasDeAcabamento == 0 && minutosDeAcabamento == 0) {
            return BigDecimal.ZERO;
        }

        var valorAcabamento = acabamentoRepository.findFirstByOrderByIdAsc().orElseThrow(
                () -> new ResourceNotFoundException("O valor cobrado no acabamento ainda não foi cadastrado")
        );

        var valorPorMinuto = valorAcabamento.calcularValorPorMinuto();

        var tempoTrabalhado = (horasDeAcabamento * 60) + minutosDeAcabamento;
        BigDecimal tempoTrabalhadoDecimal = BigDecimal.valueOf(tempoTrabalhado);

        return tempoTrabalhadoDecimal.multiply(valorPorMinuto);
    }

    private BigDecimal calcularConsumiveis(Set<UUID> uuidsConsumiveis) {
        if (uuidsConsumiveis.isEmpty()) {
            return BigDecimal.ZERO;
        }

        var custoTotalConsumiveis = BigDecimal.ZERO;

        for (UUID uuidConsumivel : uuidsConsumiveis) {
            var consumivelFound = consumivelRepository.findByUuidAndActiveTrueAndDeletedFalse(uuidConsumivel).orElseThrow(
                    () -> new ResourceNotFoundException("O consumível: " + uuidConsumivel + " não foi encontrado")
            );

            var precoUnitario = consumivelFound.calcularPrecoUnitario();
            custoTotalConsumiveis = custoTotalConsumiveis.add(precoUnitario);
        }

        return custoTotalConsumiveis;
    }

    private BigDecimal buscarFatorLucro(UUID uuidMargemLucro) {
        var margemLucro = margemLucroRepository.findByUuidAndActiveTrueAndDeletedFalse(uuidMargemLucro).orElseThrow(
                () -> new ResourceNotFoundException("A margem de lucro: " + uuidMargemLucro + " não foi encontrada")
        );

        return margemLucro.calcularFatorLucro();
    }

    private record ResultadoFilamento(
            List<CustoFilamentoDto> custosFilamento,
            BigDecimal custoTotalFilamento,
            Material material
    ) {
    }

    private record ResultadoImpressao(
            BigDecimal tempoImpressao,
            BigDecimal energiaKwh,
            BigDecimal custoEnergia,
            BigDecimal depreciacao
    ) {
    }
}
