package com.Geek3DShop.Calculadora_3D.energia.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EnergiaRequestDto(
        @NotNull(message = "O valor da fatura é obrigatório")
        @Positive(message = "O valor da fatura deve ser maior do que zero")
        @Digits(integer = 3, fraction = 2, message = "O valor da fatura deve ter no máximo três dígitos inteiros e dois decimais") BigDecimal valorFatura,

        @NotNull(message = "O consumo de kWh é obrigatório")
        @Positive(message = "O consumo de kWh deve ser maior do que zero") Integer consumoKwh
) {
}
