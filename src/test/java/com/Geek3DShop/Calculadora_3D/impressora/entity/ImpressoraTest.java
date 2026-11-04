package com.Geek3DShop.Calculadora_3D.impressora.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ImpressoraTest {

    @Test
    void deveDividirOCustoDeAquisicaoPelaVidaUtil() {
        // PREPARA
        BigDecimal custoAquisicao = new BigDecimal("2000");
        Integer vidaUtilImpressora = 4000;

        Impressora minhaImpressora = Impressora.builder()
                .custoAquisicao(custoAquisicao)
                .vidaUtilHoras(vidaUtilImpressora)
                .build();

        // EXECUTA
        var depreciacaoHora = minhaImpressora.calcularDepreciacaoPorHora();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("0.5");

        assertThat(depreciacaoHora).isEqualByComparingTo(valorEsperado);
    }

    @Test
    void deveArredondarOValorDaDepreciacaoParaQuatroCasas() {
        // PREPARA
        BigDecimal custoAquisicao = new BigDecimal("3706.33");
        Integer vidaUtilImpressora = 26298;

        Impressora minhaImpressora = Impressora.builder()
                .custoAquisicao(custoAquisicao)
                .vidaUtilHoras(vidaUtilImpressora)
                .build();

        // EXECUTA
        var depreciacaoHora = minhaImpressora.calcularDepreciacaoPorHora();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("0.1409");

        assertThat(depreciacaoHora.scale()).isEqualTo(4);
        assertThat(depreciacaoHora).isEqualByComparingTo(valorEsperado);
    }
}