package com.Geek3DShop.Calculadora_3D.lucro.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MargemLucroTest {

    @Test
    void deveSomarUmAoPercentualDivididoPorCem() {
        // PREPARA
        BigDecimal meuPercentual = new BigDecimal("250");

        MargemLucro minhaMargem = new MargemLucro(null, null, meuPercentual);

        // EXECUTA
        var fatorLucro = minhaMargem.calcularFatorLucro();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("3.50");

        assertThat(fatorLucro.scale()).isEqualTo(4);
        assertThat(fatorLucro).isEqualByComparingTo(valorEsperado);
    }

    @Test
    void deveManterAsCasasDecimaisDoPercentualNoFator() {
        // PREPARA
        BigDecimal meuPercentual = new BigDecimal("225.50");

        MargemLucro minhaMargem = new MargemLucro(null, null, meuPercentual);

        // EXECUTA
        var fatorLucro = minhaMargem.calcularFatorLucro();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("3.255");

        assertThat(fatorLucro.scale()).isEqualTo(4);
        assertThat(fatorLucro).isEqualByComparingTo(valorEsperado);
    }
}