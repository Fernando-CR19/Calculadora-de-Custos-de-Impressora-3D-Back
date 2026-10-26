package com.Geek3DShop.Calculadora_3D.energia.dto;

import com.Geek3DShop.Calculadora_3D.energia.entity.Energia;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record EnergiaResponseDto(OffsetDateTime updatedAt, BigDecimal valorFatura, Integer consumoKwh, BigDecimal precoKwh) {

    public static EnergiaResponseDto from(Energia energia) {
        return new EnergiaResponseDto(
                energia.getUpdatedAt(),
                energia.getValorFatura(),
                energia.getConsumoKwh(),
                energia.calcularPrecoKwh()
        );
    }
}
