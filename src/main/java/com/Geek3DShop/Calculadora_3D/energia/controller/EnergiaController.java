package com.Geek3DShop.Calculadora_3D.energia.controller;

import com.Geek3DShop.Calculadora_3D.energia.dto.EnergiaResponseDto;
import com.Geek3DShop.Calculadora_3D.energia.service.EnergiaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/energia")
public class EnergiaController {
    private final EnergiaService energiaService;

    public EnergiaController(EnergiaService energiaService) {
        this.energiaService = energiaService;
    }

    @GetMapping
    public EnergiaResponseDto getEnergia() {
        return energiaService.getEnergia();
    }
}
