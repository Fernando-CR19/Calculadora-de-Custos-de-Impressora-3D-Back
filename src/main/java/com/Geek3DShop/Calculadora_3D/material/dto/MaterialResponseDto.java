package com.Geek3DShop.Calculadora_3D.material.dto;

import com.Geek3DShop.Calculadora_3D.material.entity.Material;

import java.util.UUID;

public record MaterialResponseDto(UUID uuid, String nome) {
    public static MaterialResponseDto from(Material material) {
        return new MaterialResponseDto(
                material.getUuid(),
                material.getNome()
        );
    }
}
