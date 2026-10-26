package com.Geek3DShop.Calculadora_3D.acabamento.dto;

import com.Geek3DShop.Calculadora_3D.acabamento.entity.Acabamento;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AcabamentoResponseDto(OffsetDateTime updatedAt, BigDecimal valorHora, BigDecimal valorPorMinuto) {

    public static AcabamentoResponseDto from(Acabamento acabamento) {
        return new AcabamentoResponseDto(
                acabamento.getUpdatedAt(),
                acabamento.getValorHora(),
                acabamento.calcularValorPorMinuto()
        );
    }
}
