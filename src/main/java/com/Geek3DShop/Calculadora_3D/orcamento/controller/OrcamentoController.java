package com.Geek3DShop.Calculadora_3D.orcamento.controller;

import com.Geek3DShop.Calculadora_3D.orcamento.dto.OrcamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.orcamento.dto.OrcamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.orcamento.service.OrcamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {
    private final OrcamentoService orcamentoService;

    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OrcamentoResponseDto calcularOrcamento(@Valid @RequestBody OrcamentoRequestDto orcamentoRequestDto) {
        return orcamentoService.calcularOrcamento(orcamentoRequestDto);
    }
}
