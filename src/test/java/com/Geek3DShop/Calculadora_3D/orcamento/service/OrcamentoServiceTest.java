package com.Geek3DShop.Calculadora_3D.orcamento.service;

import com.Geek3DShop.Calculadora_3D.acabamento.repository.AcabamentoRepository;
import com.Geek3DShop.Calculadora_3D.consumivel.entity.Consumivel;
import com.Geek3DShop.Calculadora_3D.consumivel.repository.ConsumivelRepository;
import com.Geek3DShop.Calculadora_3D.desperdicio.entity.NivelDesperdicio;
import com.Geek3DShop.Calculadora_3D.desperdicio.repository.NivelDesperdicioRepository;
import com.Geek3DShop.Calculadora_3D.energia.entity.Energia;
import com.Geek3DShop.Calculadora_3D.energia.repository.EnergiaRepository;
import com.Geek3DShop.Calculadora_3D.filamento.entity.Filamento;
import com.Geek3DShop.Calculadora_3D.filamento.repository.FilamentoRepository;
import com.Geek3DShop.Calculadora_3D.impressora.entity.Impressora;
import com.Geek3DShop.Calculadora_3D.impressora.repository.ImpressoraRepository;
import com.Geek3DShop.Calculadora_3D.lucro.entity.MargemLucro;
import com.Geek3DShop.Calculadora_3D.lucro.repository.MargemLucroRepository;
import com.Geek3DShop.Calculadora_3D.material.entity.Material;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.CustoFilamentoDto;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.FilamentoUsadoDto;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.OrcamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.OrcamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.potencia.entity.PotenciaImpressora;
import com.Geek3DShop.Calculadora_3D.potencia.repository.PotenciaImpressoraRepository;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrcamentoServiceTest {

    @InjectMocks
    private OrcamentoService orcamentoService;

    @Mock
    private AcabamentoRepository acabamentoRepository;

    @Mock
    private ConsumivelRepository consumivelRepository;

    @Mock
    private NivelDesperdicioRepository nivelDesperdicioRepository;

    @Mock
    private EnergiaRepository energiaRepository;

    @Mock
    private FilamentoRepository filamentoRepository;

    @Mock
    private ImpressoraRepository impressoraRepository;

    @Mock
    private MargemLucroRepository margemLucroRepository;

    @Mock
    private PotenciaImpressoraRepository potenciaImpressoraRepository;

    @Test
    void deveCalcularOValorSugeridoComTodasAsEtapas() {
        // PREPARA
        Usuario usuario = Usuario.builder().build();

        Integer idImpressora = 1;
        UUID uuidImpressora = UUID.randomUUID();
        BigDecimal custoAquisicaoImpressora = new BigDecimal("1409");
        Integer vidaUtilImpressora = 10000;
        BigDecimal potenciaMediaAmsW = new BigDecimal("3.69");
        Impressora minhaImpressora = Impressora.builder()
                .custoAquisicao(custoAquisicaoImpressora)
                .vidaUtilHoras(vidaUtilImpressora)
                .potenciaMediaAmsW(potenciaMediaAmsW)
                .build();
        minhaImpressora.setId(idImpressora);
        minhaImpressora.setUuid(uuidImpressora);

        String nomeMaterial = "PETG";
        UUID uuidMaterial = UUID.randomUUID();
        Integer idMaterial = 1;
        Material meuMaterial = new Material(usuario, nomeMaterial);
        meuMaterial.setId(idMaterial);
        meuMaterial.setUuid(uuidMaterial);

        UUID uuidFilamento = UUID.randomUUID();
        String corFilamento = "Preto";
        BigDecimal precoKgFilamento = new BigDecimal("72.91");
        Filamento meuFilamento = new Filamento(usuario, corFilamento, precoKgFilamento, meuMaterial);

        BigDecimal gramasUsadas = new BigDecimal("18.21");
        List<FilamentoUsadoDto> filamentosUsados = List.of(new FilamentoUsadoDto(uuidFilamento, gramasUsadas));

        BigDecimal potenciaMediaPetg = new BigDecimal("75");
        PotenciaImpressora minhaPotencia = new PotenciaImpressora(meuMaterial, minhaImpressora, potenciaMediaPetg);

        BigDecimal valorFatura = new BigDecimal("100.66");
        Integer consumoKwh = 100;
        Energia minhaEnergia = new Energia(usuario, valorFatura, consumoKwh);

        BigDecimal percentualDesperdicio = new BigDecimal("3");
        NivelDesperdicio meuNivelDesperdicio = new NivelDesperdicio(usuario, null, percentualDesperdicio);

        BigDecimal percentualLucro = new BigDecimal("250");
        MargemLucro minhaMargem = new MargemLucro(usuario, null, percentualLucro);

        BigDecimal precoKitSacola = new BigDecimal("19.3");
        Integer quantidadeKitSacola = 100;
        Consumivel sacola = new Consumivel(usuario, null, precoKitSacola, quantidadeKitSacola);

        BigDecimal precoKitPitao = new BigDecimal("23.3");
        Integer quantidadeKitPitao = 30;
        Consumivel pitao = new Consumivel(usuario, null, precoKitPitao, quantidadeKitPitao);

        Integer horasImpressao = 1;

        Integer minutosImpressao = 18;

        UUID uuidNivelDesperdicio = UUID.randomUUID();

        UUID uuidMargemLucro = UUID.randomUUID();

        Integer horasAcabamento = 0;

        Integer minutosAcabamento = 0;

        UUID uuidPitao = UUID.randomUUID();
        UUID uuidSacola = UUID.randomUUID();
        Set<UUID> uuidsConsumiveis = Set.of(uuidPitao, uuidSacola);

        OrcamentoRequestDto requestDto = new OrcamentoRequestDto(uuidImpressora, filamentosUsados, horasImpressao, minutosImpressao, uuidNivelDesperdicio, uuidMargemLucro, horasAcabamento, minutosAcabamento, uuidsConsumiveis);

        // Mocks
        when(filamentoRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuidFilamento)).thenReturn(Optional.of(meuFilamento));
        when(impressoraRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuidImpressora)).thenReturn(Optional.of(minhaImpressora));
        when(potenciaImpressoraRepository.findByImpressoraIdAndMaterialUuidAndMaterialActiveTrueAndMaterialDeletedFalse(idImpressora, uuidMaterial)).thenReturn(Optional.of(minhaPotencia));
        when(energiaRepository.findByUsuario(usuario)).thenReturn(Optional.of(minhaEnergia));
        when(nivelDesperdicioRepository.findByUsuarioAndUuidAndDeletedFalseAndActiveTrue(usuario, uuidNivelDesperdicio)).thenReturn(Optional.of(meuNivelDesperdicio));
        when(consumivelRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuidSacola)).thenReturn(Optional.of(sacola));
        when(consumivelRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuidPitao)).thenReturn(Optional.of(pitao));
        when(margemLucroRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuidMargemLucro)).thenReturn(Optional.of(minhaMargem));

        // EXECUTA
        OrcamentoResponseDto meuOrcamento = orcamentoService.calcularOrcamento(usuario, requestDto);

        // VERIFICA

        // Valor sugerido
        BigDecimal valorSugerido = meuOrcamento.valorSugerido();
        BigDecimal valorSugeridoEsperado = new BigDecimal("9.18");
        assertThat(valorSugerido).isEqualByComparingTo(valorSugeridoEsperado);

        // Custo total
        BigDecimal custoTotal = meuOrcamento.custoTotal();
        BigDecimal custoTotalEsperado = new BigDecimal("2.62");
        assertThat(custoTotal).isEqualByComparingTo(custoTotalEsperado);

        // Custo com filamentos
        BigDecimal custoFilamentos = meuOrcamento.custoFilamentos();
        BigDecimal custoFilamentosEsperado = new BigDecimal("1.33");
        assertThat(custoFilamentos).isEqualByComparingTo(custoFilamentosEsperado);

        // Custo com desperdício
        BigDecimal custoDesperdicio = meuOrcamento.custoDesperdicio();
        BigDecimal custoDesperdicioEsperado = new BigDecimal("0.04");
        assertThat(custoDesperdicio).isEqualByComparingTo(custoDesperdicioEsperado);

        // Custo com energia
        BigDecimal custoEnergia = meuOrcamento.custoEnergia();
        BigDecimal custoEnergiaEsperado = new BigDecimal("0.10");
        assertThat(custoEnergia).isEqualByComparingTo(custoEnergiaEsperado);

        // Custo da depreciação
        BigDecimal custoDepreciacao = meuOrcamento.custoDepreciacao();
        BigDecimal custoDepreciacaoEsperado = new BigDecimal("0.18");
        assertThat(custoDepreciacao).isEqualByComparingTo(custoDepreciacaoEsperado);

        // Custo base
        BigDecimal custoBase = meuOrcamento.custoBase();
        BigDecimal custoBaseEsperado = new BigDecimal("1.65");
        assertThat(custoBase).isEqualByComparingTo(custoBaseEsperado);

        // Custo com o acabamento
        BigDecimal custoAcabamento = meuOrcamento.custoAcabamento();
        BigDecimal custoAcabamentoEsperado = new BigDecimal("0.00");
        assertThat(custoAcabamento).isEqualByComparingTo(custoAcabamentoEsperado);

        // Custo com consumíveis
        BigDecimal custoConsumiveis = meuOrcamento.custoConsumiveis();
        BigDecimal custoConsumiveisEsperado = new BigDecimal("0.97");
        assertThat(custoConsumiveis).isEqualByComparingTo(custoConsumiveisEsperado);

        // Tempo de impressão
        BigDecimal tempoImpressao = meuOrcamento.tempoImpressaoHoras();
        BigDecimal tempoImpressaoEsperado = new BigDecimal("1.3000");
        assertThat(tempoImpressao).isEqualByComparingTo(tempoImpressaoEsperado);

        // Consumo de energia em kWh
        BigDecimal consumoEnergia = meuOrcamento.consumoEnergiaKwh();
        BigDecimal consumoEnergiaEsperado = new BigDecimal("0.1023");
        assertThat(consumoEnergia).isEqualByComparingTo(consumoEnergiaEsperado);

        // Fator de lucro
        BigDecimal fatorLucro = meuOrcamento.fatorLucro();
        BigDecimal fatorLucroEsperado = new BigDecimal("3.5000");
        assertThat(fatorLucro).isEqualByComparingTo(fatorLucroEsperado);
    }
}