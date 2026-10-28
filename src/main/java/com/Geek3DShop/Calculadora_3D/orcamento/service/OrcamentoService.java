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


    // Trocar object por OrcamentoResponseDto depois, Object temporário para o código compilar.
    @Transactional(readOnly = true)
    public Object calcularOrcamento(OrcamentoRequestDto orcamentoRequestDto) {
        var resultadoFilamentos = this.calcularFilamentos(orcamentoRequestDto.filamentos());


        return 0;
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

    private record ResultadoFilamento(
            List<CustoFilamentoDto> custosFilamento,
            BigDecimal custoTotalFilamento,
            Material material
    ) {
    }
}
