package com.Geek3DShop.Calculadora_3D.desperdicio.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;


class NivelDesperdicioTest {

    @Test
    void deveSomarUmAoPercentualDivididoPorCem() {
        // PREPARA
        String nomeNivel = "Pouco";
        BigDecimal meuPercentual = new BigDecimal("3");

        NivelDesperdicio meuNivelDesperdicio = new NivelDesperdicio(null, nomeNivel, meuPercentual);

        // EXECUTA
        var fatorDesperdicio = meuNivelDesperdicio.calcularFatorDesperdicio();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("1.03");

        assertThat(fatorDesperdicio).isEqualByComparingTo(valorEsperado);
        assertThat(fatorDesperdicio.scale()).isEqualTo(4);
    }

    @Test
    void deveManterAsCasasDecimaisDoPercentualNoFator() {
        // PREPARA
        String nomeNivel = "Pouco";
        BigDecimal meuPercentual = new BigDecimal("2.5");

        NivelDesperdicio meuNivelDesperdicio = new NivelDesperdicio(null, nomeNivel, meuPercentual);

        // EXECUTA
        var fatorDesperdicio = meuNivelDesperdicio.calcularFatorDesperdicio();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("1.0250");

        assertThat(fatorDesperdicio.scale()).isEqualTo(4);
        assertThat(fatorDesperdicio).isEqualByComparingTo(valorEsperado);
    }
}