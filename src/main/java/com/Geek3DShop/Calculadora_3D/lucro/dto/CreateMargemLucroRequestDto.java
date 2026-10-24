package com.Geek3DShop.Calculadora_3D.lucro.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateMargemLucroRequestDto(
        @NotBlank(message = "O nome da margem de lucro é obrigatório") String nome,

        @NotNull(message = "O percentual é obrigatório")
        @Positive(message = "O percentual deve ser maior que zero")
        @Digits(integer = 3, fraction = 2, message = "O percentual precisa ter no máximo três dígitos inteiros e dois decimais") BigDecimal percentual
) {
    public CreateMargemLucroRequestDto {
        if (nome != null) {
            nome = nome.strip();
        }
    }
}
