package com.Geek3DShop.Calculadora_3D.potencia.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePotenciaImpressoraRequestDto(
        @NotNull(message = "O material é obrigatório") UUID uuidMaterial,

        @NotNull(message = "A potência média é obrigatória")
        @Positive(message = "A potência média deve ser maior do que zero")
        @Digits(integer = 4, fraction = 2, message = "A potência precisa ter no máximo quatro dígitos inteiros e dois decimais") BigDecimal potenciaMediaW) {
}
