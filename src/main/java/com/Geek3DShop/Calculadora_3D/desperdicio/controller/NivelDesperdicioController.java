package com.Geek3DShop.Calculadora_3D.desperdicio.controller;

import com.Geek3DShop.Calculadora_3D.desperdicio.dto.CreateNivelDesperdicioRequestDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.dto.NivelDesperdicioResponseDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.service.NivelDesperdicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
