package com.Geek3DShop.Calculadora_3D.acabamento.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AcabamentoRequestDto(
        @NotNull(message = "O valor da hora trabalhada é obrigatório")
        @Positive(message = "O valor da hora trabalhada deve ser maior do que zero")
        @Digits(integer = 3, fraction = 2, message = "O valor da hora trabalhada deve ter no máximo três dígitos inteiros e dois decimais") BigDecimal valorHora
) {
}
