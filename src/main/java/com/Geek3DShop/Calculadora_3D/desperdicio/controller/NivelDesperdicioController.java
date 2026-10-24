package com.Geek3DShop.Calculadora_3D.desperdicio.controller;

import com.Geek3DShop.Calculadora_3D.desperdicio.dto.CreateNivelDesperdicioRequestDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.dto.NivelDesperdicioResponseDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.dto.UpdateNivelDesperdicioDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.service.NivelDesperdicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public NivelDesperdicioResponseDto createNivelDesperdicio(@Valid @RequestBody CreateNivelDesperdicioRequestDto nivelDesperdicioDto) {
        return nivelDesperdicioService.createNivelDesperdicio(nivelDesperdicioDto);
    }

    @GetMapping
    public List<NivelDesperdicioResponseDto> listNiveisDesperdicio() {
        return nivelDesperdicioService.listNiveisDesperdicio();
    }

    @PutMapping("/{uuid}")
    public NivelDesperdicioResponseDto updateNivelDesperdicio(@PathVariable UUID uuid, @Valid @RequestBody UpdateNivelDesperdicioDto nivelDesperdicioDto) {
        return nivelDesperdicioService.updateNivelDesperdicio(uuid, nivelDesperdicioDto);
    }
}
