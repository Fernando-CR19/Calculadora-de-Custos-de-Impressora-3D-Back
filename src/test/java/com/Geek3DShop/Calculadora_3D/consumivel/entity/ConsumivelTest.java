package com.Geek3DShop.Calculadora_3D.consumivel.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

class ConsumivelTest {

    @Test
    void deveDividirOPrecoDoKitPelaQuantidade() {
        // Cria a entity
        String nomeConsumivel = "Pitão";
        BigDecimal precoConsumivel = new BigDecimal("10");
        Integer quantidadeKit = 5;

        Consumivel meuConsumivel = new Consumivel(null, nomeConsumivel, precoConsumivel, quantidadeKit);

        // Chama a função
        var precoUnitario = meuConsumivel.calcularPrecoUnitario();

        // Resultado esperado
        BigDecimal precoEsperado = new BigDecimal("2");

        // Comparar resultado
        assertThat(precoUnitario).isEqualByComparingTo(precoEsperado);
    }

    @Test
    void deveArredondarOPrecoUnitarioParaQuatroCasas() {
        // Cria a entity
        String nomeConsumivel = "Pitão";
        BigDecimal precoConsumivel = new BigDecimal("23.30");
        Integer quantidadeKit = 30;

        Consumivel meuConsumivel = new Consumivel(null, nomeConsumivel, precoConsumivel, quantidadeKit);

        // Chama a função
        var precoUnitario = meuConsumivel.calcularPrecoUnitario();

        // Resultado esperado
        BigDecimal precoEsperado = new BigDecimal("0.7767");

        // Comparar resultado
        assertThat(precoUnitario.scale()).isEqualTo(4);
        assertThat(precoUnitario).isEqualByComparingTo(precoEsperado);
    }

    @Test
    void deveRetornarOValorDoConsumivelQuandoAQuantidadeForUm() {
        // Cria a entity
        String nomeConsumivel = "Pitão";
        BigDecimal precoConsumivel = new BigDecimal("10");
        Integer quantidadeKit = 1;

        Consumivel meuConsumivel = new Consumivel(null, nomeConsumivel, precoConsumivel, quantidadeKit);

        // Chama a função
        var precoUnitario = meuConsumivel.calcularPrecoUnitario();

        // Resultado esperado
        BigDecimal precoEsperado = new BigDecimal("10");

        // Comparar resultado
        assertThat(precoUnitario).isEqualByComparingTo(precoEsperado);
    }
}