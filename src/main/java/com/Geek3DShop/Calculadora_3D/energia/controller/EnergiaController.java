package com.Geek3DShop.Calculadora_3D.energia.controller;

import com.Geek3DShop.Calculadora_3D.energia.dto.EnergiaRequestDto;
import com.Geek3DShop.Calculadora_3D.energia.dto.EnergiaResponseDto;
import com.Geek3DShop.Calculadora_3D.energia.service.EnergiaService;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/energia")
public class EnergiaController {
    private final EnergiaService energiaService;

    public EnergiaController(EnergiaService energiaService) {
        this.energiaService = energiaService;
    }

    @PutMapping
    public EnergiaResponseDto upsertEnergia(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody EnergiaRequestDto energiaRequestDto) {
        return energiaService.upsertEnergia(usuario, energiaRequestDto);
    }

    @GetMapping
    public EnergiaResponseDto getEnergia(@AuthenticationPrincipal Usuario usuario) {
        return energiaService.getEnergia(usuario);
    }
}
