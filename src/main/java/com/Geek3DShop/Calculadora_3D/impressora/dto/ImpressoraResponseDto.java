package com.Geek3DShop.Calculadora_3D.impressora.dto;

import com.Geek3DShop.Calculadora_3D.impressora.entity.Impressora;

import java.math.BigDecimal;
import java.util.UUID;

public record ImpressoraResponseDto(UUID uuid, String nome, BigDecimal custoAquisicao, Integer vidaUtilHoras, BigDecimal potenciaMediaAmsW, BigDecimal depreciacaoPorHora) {

    public static ImpressoraResponseDto from(Impressora impressora) {
        return new ImpressoraResponseDto(
                impressora.getUuid(),
                impressora.getNome(),
                impressora.getCustoAquisicao(),
                impressora.getVidaUtilHoras(),
                impressora.getPotenciaMediaAmsW(),
                impressora.calcularDepreciacaoPorHora()
        );
    }
}
