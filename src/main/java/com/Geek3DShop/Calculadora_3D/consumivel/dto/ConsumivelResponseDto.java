package com.Geek3DShop.Calculadora_3D.consumivel.dto;

import com.Geek3DShop.Calculadora_3D.consumivel.entity.Consumivel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public record ConsumivelResponseDto(UUID uuid, String nome, BigDecimal precoKit, Integer quantidadeKit, BigDecimal precoUnitario) {

    public static ConsumivelResponseDto from(Consumivel consumivel) {
        return new ConsumivelResponseDto(
                consumivel.getUuid(),
                consumivel.getNome(),
                consumivel.getPrecoKit(),
                consumivel.getQuantidadeKit(),
                consumivel.calcularPrecoUnitario().setScale(2, RoundingMode.HALF_UP)
        );
    }
}
