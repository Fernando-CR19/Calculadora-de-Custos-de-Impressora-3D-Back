package com.Geek3DShop.Calculadora_3D.orcamento.dto;

import java.math.BigDecimal;

public record CustoFilamentoDto(
        String cor,
        String material,
        BigDecimal gramas,
        BigDecimal custo
) {
}
