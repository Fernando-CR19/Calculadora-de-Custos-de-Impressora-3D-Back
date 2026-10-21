package com.Geek3DShop.Calculadora_3D.filamento.controller;

import com.Geek3DShop.Calculadora_3D.filamento.dto.CreateFilamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.filamento.dto.FilamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.filamento.dto.UpdateFilamentoDto;
import com.Geek3DShop.Calculadora_3D.filamento.service.FilamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/filamentos")
public class FilamentoController {
    private final FilamentoService filamentoService;

    public FilamentoController(FilamentoService filamentoService) {
        this.filamentoService = filamentoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilamentoResponseDto createFilamento(@Valid @RequestBody CreateFilamentoRequestDto filamentoRequestDto) {
        return filamentoService.createFilamento(filamentoRequestDto);
    }

    @GetMapping
    public List<FilamentoResponseDto> listFilamentos() {
        return filamentoService.listFilamentos();
    }

    @PutMapping("/{uuid}")
    public FilamentoResponseDto updateFilamento(@PathVariable UUID uuid, @Valid @RequestBody UpdateFilamentoDto filamentoDto) {
        return filamentoService.updateFilamento(uuid, filamentoDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilamento(@PathVariable UUID uuid) {
        filamentoService.deleteFilamento(uuid);
    }
}
