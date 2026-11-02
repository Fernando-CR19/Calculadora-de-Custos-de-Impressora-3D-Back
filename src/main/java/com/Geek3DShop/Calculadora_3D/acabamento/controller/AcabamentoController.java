package com.Geek3DShop.Calculadora_3D.acabamento.controller;

import com.Geek3DShop.Calculadora_3D.acabamento.dto.AcabamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.acabamento.dto.AcabamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.acabamento.service.AcabamentoService;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acabamento")
public class AcabamentoController {
    private final AcabamentoService acabamentoService;

    public AcabamentoController(AcabamentoService acabamentoService) {
        this.acabamentoService = acabamentoService;
    }

    @PutMapping
    public AcabamentoResponseDto upsertAcabamento(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody AcabamentoRequestDto acabamentoRequestDto) {
        return acabamentoService.upsertAcabamento(usuario, acabamentoRequestDto);
    }

    @GetMapping
    public AcabamentoResponseDto getAcabamento(@AuthenticationPrincipal Usuario usuario) {
        return acabamentoService.getAcabamento(usuario);
    }
}
