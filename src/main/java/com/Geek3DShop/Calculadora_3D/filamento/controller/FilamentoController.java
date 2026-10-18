package com.Geek3DShop.Calculadora_3D.filamento.controller;

import com.Geek3DShop.Calculadora_3D.filamento.dto.CreateFilamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.filamento.dto.FilamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.filamento.service.FilamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filamentos")
public class FilamentoController {
    private final FilamentoService filamentoService;

    public FilamentoController(FilamentoService filamentoService) {
        this.filamentoService = filamentoService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public FilamentoResponseDto createFilamento(@Valid @RequestBody CreateFilamentoRequestDto filamentoRequestDto) {
        return filamentoService.createFilamento(filamentoRequestDto);
    }
}
