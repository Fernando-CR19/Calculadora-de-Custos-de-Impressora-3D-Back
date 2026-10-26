package com.Geek3DShop.Calculadora_3D.acabamento.controller;

import com.Geek3DShop.Calculadora_3D.acabamento.dto.AcabamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.acabamento.dto.AcabamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.acabamento.service.AcabamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acabamento")
public class AcabamentoController {
    private final AcabamentoService acabamentoService;

    public AcabamentoController(AcabamentoService acabamentoService) {
        this.acabamentoService = acabamentoService;
    }

    @PutMapping
    public AcabamentoResponseDto upsertAcabamento(@Valid @RequestBody AcabamentoRequestDto acabamentoRequestDto) {
        return acabamentoService.upsertAcabamento(acabamentoRequestDto);
    }
}
