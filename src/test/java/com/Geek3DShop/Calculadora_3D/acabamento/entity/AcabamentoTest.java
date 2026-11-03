package com.Geek3DShop.Calculadora_3D.acabamento.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

class AcabamentoTest {

    @Test
    void deveDividirOValorDaHoraPorSessenta() {
        // PREPARA
        BigDecimal valorHora = new BigDecimal("60");

        Acabamento meuAcabamento = new Acabamento(null, valorHora);

        // EXECUTA
        var valorDoAcabamento = meuAcabamento.calcularValorPorMinuto();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("1");

        assertThat(valorDoAcabamento).isEqualByComparingTo(valorEsperado);
    }

    @Test
    void deveArredondarOValorPorMinutoParaQuatroCasas() {
        // PREPARA
        BigDecimal valorHora = new BigDecimal("25");

        Acabamento meuAcabamento = new Acabamento(null, valorHora);

        // EXECUTA
        var valorDoAcabamento = meuAcabamento.calcularValorPorMinuto();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("0.4167");

        assertThat(valorDoAcabamento.scale()).isEqualTo(4);
        assertThat(valorDoAcabamento).isEqualByComparingTo(valorEsperado);
    }
}