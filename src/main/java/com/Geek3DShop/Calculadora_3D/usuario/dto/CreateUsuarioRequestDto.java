package com.Geek3DShop.Calculadora_3D.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUsuarioRequestDto(
        @NotBlank(message = "O seu nome é obrigatório") String nome,

        @NotBlank(message = "O nome de usuário é obrigatório") String nomeUsuario,

        @Size(min = 8, max = 72, message = "A senha deve conter entre 8 e 72 caracteres")
        @NotBlank(message = "Por favor digite uma senha") String senha

) {
    public CreateUsuarioRequestDto {
        if (nome != null) {
            nome = nome.strip();
        }

        if (nomeUsuario != null) {
            nomeUsuario = nomeUsuario.strip();
        }
    }
}
