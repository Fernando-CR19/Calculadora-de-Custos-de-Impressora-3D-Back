package com.Geek3DShop.Calculadora_3D.orcamento.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrcamentoResponseDto(
        List<CustoFilamentoDto> filamentos,

        BigDecimal tempoImpressaoHoras,

        BigDecimal consumoEnergiaKwh,

        BigDecimal custoFilamentos,

        BigDecimal custoDesperdicio,

        BigDecimal custoEnergia,

        BigDecimal custoDepreciacao,

        BigDecimal custoBase,

        BigDecimal custoAcabamento,

        BigDecimal custoConsumiveis,

        BigDecimal custoTotal,

        BigDecimal fatorucro,

        BigDecimal valorSugerido
) {
}
