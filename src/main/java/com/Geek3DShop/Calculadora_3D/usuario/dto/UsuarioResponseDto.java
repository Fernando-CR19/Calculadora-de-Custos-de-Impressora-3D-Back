package com.Geek3DShop.Calculadora_3D.usuario.dto;

import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;

import java.util.UUID;

public record UsuarioResponseDto(UUID uuid, String nome, String nomeUsuario) {
    public static UsuarioResponseDto from(Usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getUuid(),
                usuario.getNome(),
                usuario.getNomeUsuario()
        );
    }
}
