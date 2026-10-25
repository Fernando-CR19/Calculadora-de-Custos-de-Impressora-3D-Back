package com.Geek3DShop.Calculadora_3D.potencia.controller;

import com.Geek3DShop.Calculadora_3D.potencia.dto.CreatePotenciaImpressoraRequestDto;
import com.Geek3DShop.Calculadora_3D.potencia.dto.PotenciaImpressoraResponseDto;
import com.Geek3DShop.Calculadora_3D.potencia.service.PotenciaImpressoraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/impressoras/{uuidImpressora}/materiais")
public class PotenciaImpressoraController {
    private final PotenciaImpressoraService potenciaImpressoraService;

    public PotenciaImpressoraController(PotenciaImpressoraService potenciaImpressoraService) {
        this.potenciaImpressoraService = potenciaImpressoraService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PotenciaImpressoraResponseDto createPotenciaImpressora(@PathVariable UUID uuidImpressora, @Valid @RequestBody CreatePotenciaImpressoraRequestDto potenciaImpressoraRequestDto) {
        return potenciaImpressoraService.createPotenciaImpressora(uuidImpressora, potenciaImpressoraRequestDto);
    }
}
