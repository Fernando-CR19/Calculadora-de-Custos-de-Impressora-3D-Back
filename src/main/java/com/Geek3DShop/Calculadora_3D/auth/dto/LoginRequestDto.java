package com.Geek3DShop.Calculadora_3D.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "O nome de usuário é obrigatório") String nomeUsuario,

        @NotBlank(message = "A senha é obrigatória") String senha
) {

    public LoginRequestDto {
        if (nomeUsuario != null) {
            nomeUsuario = nomeUsuario.strip();
        }
    }
}
