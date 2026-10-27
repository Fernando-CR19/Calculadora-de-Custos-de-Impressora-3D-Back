package com.Geek3DShop.Calculadora_3D.orcamento.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record FilamentoUsadoDto(
        @NotNull(message = "O filamento é obrigatório")
        UUID uuid,

        @NotNull(message = "A quantidade de gramas é obrigatória")
        @Positive(message = "A quantidade em gramas usada precisa ser maior do que zero")
        BigDecimal gramas
) {
}
