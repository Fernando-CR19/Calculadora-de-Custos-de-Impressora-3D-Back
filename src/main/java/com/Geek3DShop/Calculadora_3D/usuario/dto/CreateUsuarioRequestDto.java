package com.Geek3DShop.Calculadora_3D.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUsuarioRequestDto(
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        @NotBlank(message = "O seu nome é obrigatório") String nome,

        @NotBlank(message = "O nome de usuário é obrigatório")
        @Pattern(
                regexp = "^[a-zA-Z0-9.\\-_]{3,20}$",
                message = "O nome de usuário deve ter entre 3 e 20 caracteres e conter apenas letras, números, hífen, ponto ou underline"
        )
        String nomeUsuario,

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
