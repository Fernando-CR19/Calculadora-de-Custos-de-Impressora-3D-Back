package com.Geek3DShop.Calculadora_3D.energia.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class EnergiaTest {

    @Test
    void deveDividirOValorDaFaturaPeloConsumoEmKwh() {
        // PREPARA
        BigDecimal minhaFatura = new BigDecimal("150");
        Integer meuConsumo = 100;

        Energia minhaEnergia = new Energia(null, minhaFatura, meuConsumo);

        // EXECUTA
        var precoKwh = minhaEnergia.calcularPrecoKwh();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("1.5");

        assertThat(precoKwh).isEqualByComparingTo(valorEsperado);
    }

    @Test
    void deveArredondarOPrecoDoKwhParaQuatroCasas() {
        // PREPARA
        BigDecimal minhaFatura = new BigDecimal("165.08");
        Integer meuConsumo = 164;

        Energia minhaEnergia = new Energia(null, minhaFatura, meuConsumo);

        // EXECUTA
        var precoKwh = minhaEnergia.calcularPrecoKwh();

        // VERIFICA
        BigDecimal valorEsperado = new BigDecimal("1.0066");

        assertThat(precoKwh.scale()).isEqualTo(4);
        assertThat(precoKwh).isEqualByComparingTo(valorEsperado);
    }
}