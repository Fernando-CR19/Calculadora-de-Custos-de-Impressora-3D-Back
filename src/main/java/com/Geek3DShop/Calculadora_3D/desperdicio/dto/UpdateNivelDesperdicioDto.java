package com.Geek3DShop.Calculadora_3D.desperdicio.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateNivelDesperdicioDto(
        @NotBlank(message = "O nome do nível de desperdício é obrigatório") String nome,

        @NotNull(message = "O percentual é obrigatório")
        @Positive(message = "O percentual deve ser maior que zero")
        @Digits(integer = 2, fraction = 2, message = "O percentual precisa ter no máximo dois dígitos inteiros e dois decimais") BigDecimal percentual
) {
    public UpdateNivelDesperdicioDto {
        if (nome != null) {
            nome = nome.strip();
        }
    }
}