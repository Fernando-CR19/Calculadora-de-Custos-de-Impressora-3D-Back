package com.Geek3DShop.Calculadora_3D.potencia.dto;

import com.Geek3DShop.Calculadora_3D.material.dto.MaterialResponseDto;
import com.Geek3DShop.Calculadora_3D.potencia.entity.PotenciaImpressora;

import java.math.BigDecimal;

public record PotenciaImpressoraResponseDto(MaterialResponseDto material, BigDecimal potenciaMediaW) {

    public static PotenciaImpressoraResponseDto from(PotenciaImpressora potenciaImpressora) {
        var potenciaMediaW = potenciaImpressora.getPotenciaMediaW();
        var material = potenciaImpressora.getMaterial();
        MaterialResponseDto materialDto = MaterialResponseDto.from(material);

        return new PotenciaImpressoraResponseDto(materialDto, potenciaMediaW);
    }
}
