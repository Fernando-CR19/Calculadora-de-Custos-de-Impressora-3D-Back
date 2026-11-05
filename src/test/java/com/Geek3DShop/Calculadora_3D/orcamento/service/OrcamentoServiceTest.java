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
import com.Geek3DShop.Calculadora_3D.orcamento.dto.FilamentoUsadoDto;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.OrcamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.potencia.entity.PotenciaImpressora;
import com.Geek3DShop.Calculadora_3D.potencia.repository.PotenciaImpressoraRepository;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
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

        // EXECUTA

        // VERIFICA

    }
}