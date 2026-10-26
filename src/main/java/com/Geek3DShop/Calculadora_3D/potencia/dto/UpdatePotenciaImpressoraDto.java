package com.Geek3DShop.Calculadora_3D.potencia.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdatePotenciaImpressoraDto(
        @NotNull(message = "A potência média é obrigatória")
        @Positive(message = "A potência média deve ser maior do que zero")
        @Digits(integer = 4, fraction = 2, message = "A potência precisa ter no máximo quatro dígitos inteiros e dois decimais") BigDecimal potenciaMediaW
) {
}
