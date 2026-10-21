package com.Geek3DShop.Calculadora_3D.filamento.dto;

import com.Geek3DShop.Calculadora_3D.filamento.entity.Filamento;
import com.Geek3DShop.Calculadora_3D.material.dto.MaterialResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

public record FilamentoResponseDto(UUID uuid, String cor, BigDecimal precoKg, MaterialResponseDto material) {

    public static FilamentoResponseDto from(Filamento filamento) {
        var uuid = filamento.getUuid();
        var cor = filamento.getCor();
        var precoKg = filamento.getPrecoKg();
        var material = filamento.getMaterial();
        MaterialResponseDto materialDto = MaterialResponseDto.from(material);

        return new FilamentoResponseDto(uuid, cor, precoKg, materialDto);
    }
}
