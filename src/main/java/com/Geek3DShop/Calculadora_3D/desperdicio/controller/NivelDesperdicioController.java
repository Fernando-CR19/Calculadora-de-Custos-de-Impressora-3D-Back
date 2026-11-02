package com.Geek3DShop.Calculadora_3D.desperdicio.controller;

import com.Geek3DShop.Calculadora_3D.desperdicio.dto.CreateNivelDesperdicioRequestDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.dto.NivelDesperdicioResponseDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.dto.UpdateNivelDesperdicioDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.service.NivelDesperdicioService;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/niveis-desperdicio")
public class NivelDesperdicioController {
    private final NivelDesperdicioService nivelDesperdicioService;

    public NivelDesperdicioController(NivelDesperdicioService nivelDesperdicioService) {
        this.nivelDesperdicioService = nivelDesperdicioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NivelDesperdicioResponseDto createNivelDesperdicio(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody CreateNivelDesperdicioRequestDto nivelDesperdicioDto) {
        return nivelDesperdicioService.createNivelDesperdicio(usuario, nivelDesperdicioDto);
    }

    @GetMapping
    public List<NivelDesperdicioResponseDto> listNiveisDesperdicio(@AuthenticationPrincipal Usuario usuario) {
        return nivelDesperdicioService.listNiveisDesperdicio(usuario);
    }

    @PutMapping("/{uuid}")
    public NivelDesperdicioResponseDto updateNivelDesperdicio(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuid, @Valid @RequestBody UpdateNivelDesperdicioDto nivelDesperdicioDto) {
        return nivelDesperdicioService.updateNivelDesperdicio(usuario, uuid, nivelDesperdicioDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNivelDesperdicio(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuid) {
        nivelDesperdicioService.deleteNivelDesperdicio(usuario, uuid);
    }
}
