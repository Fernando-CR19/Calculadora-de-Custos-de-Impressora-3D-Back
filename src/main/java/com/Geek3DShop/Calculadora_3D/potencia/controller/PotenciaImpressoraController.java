package com.Geek3DShop.Calculadora_3D.potencia.controller;

import com.Geek3DShop.Calculadora_3D.potencia.dto.CreatePotenciaImpressoraRequestDto;
import com.Geek3DShop.Calculadora_3D.potencia.dto.PotenciaImpressoraResponseDto;
import com.Geek3DShop.Calculadora_3D.potencia.dto.UpdatePotenciaImpressoraDto;
import com.Geek3DShop.Calculadora_3D.potencia.service.PotenciaImpressoraService;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public PotenciaImpressoraResponseDto createPotenciaImpressora(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuidImpressora, @Valid @RequestBody CreatePotenciaImpressoraRequestDto potenciaImpressoraRequestDto) {
        return potenciaImpressoraService.createPotenciaImpressora(usuario, uuidImpressora, potenciaImpressoraRequestDto);
    }

    @GetMapping
    public List<PotenciaImpressoraResponseDto> listPotenciasImpressora(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuidImpressora) {
        return potenciaImpressoraService.listPotenciasImpressora(usuario, uuidImpressora);
    }

    @PutMapping("/{uuidMaterial}")
    public PotenciaImpressoraResponseDto updatePotenciaImpressora(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuidImpressora, @PathVariable UUID uuidMaterial, @Valid @RequestBody UpdatePotenciaImpressoraDto updatePotenciaImpressoraDto) {
        return potenciaImpressoraService.updatePotenciaImpressora(usuario, uuidImpressora,uuidMaterial, updatePotenciaImpressoraDto);
    }

    @DeleteMapping("/{uuidMaterial}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePotenciaImpressora(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuidImpressora, @PathVariable UUID uuidMaterial) {
        potenciaImpressoraService.deletePotenciaImpressora(usuario, uuidImpressora, uuidMaterial);
    }
}
