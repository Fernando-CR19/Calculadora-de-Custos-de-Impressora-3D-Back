package com.Geek3DShop.Calculadora_3D.consumivel.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateConsumivelDto(
        @NotBlank(message = "O nome do consumível é obrigatório") String nome,

        @NotNull(message = "O preço do kit é obrigatório")
        @Positive(message = "O preço do kit deve ser maior do que zero")
        @Digits(integer = 3, fraction = 2, message = "O preço precisa ter no máximo três dígitos inteiros e dois decimais") BigDecimal precoKit,

        @NotNull(message = "A quantidade de itens do kit é obrigatória")
        @Positive(message = "A quantidade de itens do kit precisa ser maior do que zero") Integer quantidadeKit) {

    public UpdateConsumivelDto {
        if (nome != null) {
            nome = nome.strip();
        }
    }
}
