package com.Geek3DShop.Calculadora_3D.impressora.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateImpressoraRequestDto(
        @NotBlank(message = "O nome da impressora é obrigatório") String nome,

        @NotNull(message = "O preço da impressora é obrigatório")
        @Positive(message = "O preço da impressora precisa ser maior do que zero")
        @Digits(integer = 5, fraction = 2, message = "O preço da impressora precisa ter no máximo cinco dígitos inteiros e dois decimais") BigDecimal custoAquisicao,

        @NotNull(message = "A vida útil da impressora é obrigatória")
        @Positive(message = "A vida útil da impressora deve ser maior do que zero") Integer vidaUtilHoras,

        @NotNull(message = "A potência média do AMS é obrigatória")
        @PositiveOrZero(message = "A potência média do AMS não pode ser negativa")
        @Digits(integer = 4, fraction = 2, message = "A potência média precisa ter no máximo quatro dígitos inteiros e dois decimais") BigDecimal potenciaMediaAmsW

        ) {

    public CreateImpressoraRequestDto {
        if (nome != null) {
            nome = nome.strip();
        }
    }

}
