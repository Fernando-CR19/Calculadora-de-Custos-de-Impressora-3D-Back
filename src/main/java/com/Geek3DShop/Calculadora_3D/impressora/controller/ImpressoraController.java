package com.Geek3DShop.Calculadora_3D.impressora.controller;

import com.Geek3DShop.Calculadora_3D.impressora.dto.CreateImpressoraRequestDto;
import com.Geek3DShop.Calculadora_3D.impressora.dto.ImpressoraResponseDto;
import com.Geek3DShop.Calculadora_3D.impressora.dto.UpdateImpressoraDto;
import com.Geek3DShop.Calculadora_3D.impressora.service.ImpressoraService;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/impressoras")
public class ImpressoraController {
    private final ImpressoraService impressoraService;

    public ImpressoraController(ImpressoraService impressoraService) {
        this.impressoraService = impressoraService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImpressoraResponseDto createImpressora(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody CreateImpressoraRequestDto createImpressoraRequestDto) {
        return impressoraService.createImpressora(usuario, createImpressoraRequestDto);
    }

    @GetMapping
    public List<ImpressoraResponseDto> listImpressoras(@AuthenticationPrincipal Usuario usuario) {
        return impressoraService.listImpressoras(usuario);
    }

    @PutMapping("/{uuid}")
    public ImpressoraResponseDto updateImpressora(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuid, @Valid @RequestBody UpdateImpressoraDto updateImpressoraDto) {
        return impressoraService.updateImpressora(usuario, uuid, updateImpressoraDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImpressora(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuid) {
        impressoraService.deleteImpressora(usuario, uuid);
    }
}
