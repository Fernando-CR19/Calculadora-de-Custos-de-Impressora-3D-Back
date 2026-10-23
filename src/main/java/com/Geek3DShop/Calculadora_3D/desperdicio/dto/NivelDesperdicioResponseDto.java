package com.Geek3DShop.Calculadora_3D.desperdicio.dto;

import com.Geek3DShop.Calculadora_3D.desperdicio.entity.NivelDesperdicio;

import java.math.BigDecimal;
import java.util.UUID;

public record NivelDesperdicioResponseDto(UUID uuid, String nome, BigDecimal percentual, BigDecimal fator) {

    public static NivelDesperdicioResponseDto from(NivelDesperdicio nivelDesperdicio) {
        return new NivelDesperdicioResponseDto(
                nivelDesperdicio.getUuid(),
                nivelDesperdicio.getNome(),
                nivelDesperdicio.getPercentual(),
                nivelDesperdicio.calcularFatorDesperdicio()
        );
    }
}
