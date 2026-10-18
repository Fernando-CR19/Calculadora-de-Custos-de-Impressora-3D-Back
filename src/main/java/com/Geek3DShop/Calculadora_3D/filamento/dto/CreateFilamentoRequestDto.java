package com.Geek3DShop.Calculadora_3D.filamento.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateFilamentoRequestDto(
        @NotBlank(message = "A cor do filamento é obrigatória") String cor,

        @NotNull(message = "O preço do filamento é obrigatório")
        @Positive(message = "O preço do filamento deve ser maior do que zero")
        @Digits(integer = 3, fraction = 2, message = "O valor precisa ter no máximo três dígitos inteiros e dois decimais") BigDecimal precoKg,

        @NotNull(message = "O material do filamento é obrigatório") UUID uuidMaterial) {

    public CreateFilamentoRequestDto {
        if (cor != null) {
            cor = cor.strip();
        }
    }
}
