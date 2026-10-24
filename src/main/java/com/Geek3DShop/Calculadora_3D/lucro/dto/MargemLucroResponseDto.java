package com.Geek3DShop.Calculadora_3D.lucro.dto;

import com.Geek3DShop.Calculadora_3D.lucro.entity.MargemLucro;

import java.math.BigDecimal;
import java.util.UUID;

public record MargemLucroResponseDto(UUID uuid, String nome, BigDecimal percentual, BigDecimal fator) {

    public static MargemLucroResponseDto from(MargemLucro margemLucro) {
        return new MargemLucroResponseDto(
                margemLucro.getUuid(),
                margemLucro.getNome(),
                margemLucro.getPercentual(),
                margemLucro.calcularFatorLucro()
        );
    }
}
