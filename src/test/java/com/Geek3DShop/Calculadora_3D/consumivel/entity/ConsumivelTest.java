package com.Geek3DShop.Calculadora_3D.consumivel.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

class ConsumivelTest {

    @Test
    void deveDividirOPrecoDoKitPelaQuantidade() {
        // PREPARA
        String nomeConsumivel = "Pitão";
        BigDecimal precoConsumivel = new BigDecimal("10");
        Integer quantidadeKit = 5;

        Consumivel meuConsumivel = new Consumivel(null, nomeConsumivel, precoConsumivel, quantidadeKit);

        // EXECUTA
        var precoUnitario = meuConsumivel.calcularPrecoUnitario();

        // VERIFICA
        BigDecimal precoEsperado = new BigDecimal("2");

        assertThat(precoUnitario).isEqualByComparingTo(precoEsperado);
    }

    @Test
    void deveArredondarOPrecoUnitarioParaQuatroCasas() {
        // PREPARA
        String nomeConsumivel = "Pitão";
        BigDecimal precoConsumivel = new BigDecimal("23.30");
        Integer quantidadeKit = 30;

        Consumivel meuConsumivel = new Consumivel(null, nomeConsumivel, precoConsumivel, quantidadeKit);

        // EXECUTA
        var precoUnitario = meuConsumivel.calcularPrecoUnitario();

        // VERIFICA
        BigDecimal precoEsperado = new BigDecimal("0.7767");

        assertThat(precoUnitario.scale()).isEqualTo(4);
        assertThat(precoUnitario).isEqualByComparingTo(precoEsperado);
    }

    @Test
    void deveRetornarOValorDoConsumivelQuandoAQuantidadeForUm() {
        // PREPARA
        String nomeConsumivel = "Pitão";
        BigDecimal precoConsumivel = new BigDecimal("10");
        Integer quantidadeKit = 1;

        Consumivel meuConsumivel = new Consumivel(null, nomeConsumivel, precoConsumivel, quantidadeKit);

        // EXECUTA
        var precoUnitario = meuConsumivel.calcularPrecoUnitario();

        // VERIFICA
        BigDecimal precoEsperado = new BigDecimal("10");

        assertThat(precoUnitario).isEqualByComparingTo(precoEsperado);
    }
}