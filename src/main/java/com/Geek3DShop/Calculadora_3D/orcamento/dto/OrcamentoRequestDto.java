package com.Geek3DShop.Calculadora_3D.orcamento.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record OrcamentoRequestDto(
        @NotNull(message = "A impressora é obrigatória")
        UUID uuidImpressora,

        @NotEmpty(message = "Informe ao menos um filamento")
        @Valid
        List<FilamentoUsadoDto> filamentos,

        @Min(value = 0, message = "As horas de impressão não podem ser negativas")
        Integer horasDeImpressao,

        @Min(value = 0, message = "Os minutos de impressão devem estar entre 0 e 59")
        @Max(value = 59, message = "Os minutos de impressão devem estar entre 0 e 59")
        Integer minutosDeImpressao,

        @NotNull(message = "O nível de desperdício é obrigatório")
        UUID uuidNivelDesperdicio,

        @NotNull(message = "A margem de lucro é obrigatória")
        UUID uuidMargemLucro,

        @Min(value = 0, message = "As horas do acabamento não podem ser negativas")
        Integer horasDeAcabamento,

        @Min(value = 0, message = "Os minutos do acabamento devem estar entre 0 e 59")
        @Max(value = 59, message = "Os minutos do acabamento devem estar entre 0 e 59")
        Integer minutosDeAcabamento,

        Set<@NotNull(message = "Nenhum consumível da lista pode ser vazio") UUID> uuidsConsumiveis
) {

    public OrcamentoRequestDto {
        if (horasDeImpressao == null) {
            horasDeImpressao = 0;
        }

        if (minutosDeImpressao == null) {
            minutosDeImpressao = 0;
        }

        if (horasDeAcabamento == null) {
            horasDeAcabamento = 0;
        }

        if (minutosDeAcabamento == null) {
            minutosDeAcabamento = 0;
        }

        if (uuidsConsumiveis == null) {
            uuidsConsumiveis = Set.of();
        }
    }

    @AssertTrue(message = "Informe a duração em horas ou minutos da impressão (pelo menos um deve ser maior do que zero)")
    public boolean isTempoDeImpressaoInformado() {
        boolean horasValidas = horasDeImpressao != null && horasDeImpressao > 0;
        boolean minutosValidos = minutosDeImpressao != null && minutosDeImpressao > 0;

        return horasValidas || minutosValidos;
    }
}
