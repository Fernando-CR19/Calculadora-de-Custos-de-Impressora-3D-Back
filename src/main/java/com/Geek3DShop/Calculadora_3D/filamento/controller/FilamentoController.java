package com.Geek3DShop.Calculadora_3D.filamento.controller;

import com.Geek3DShop.Calculadora_3D.filamento.dto.CreateFilamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.filamento.dto.FilamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.filamento.dto.UpdateFilamentoDto;
import com.Geek3DShop.Calculadora_3D.filamento.service.FilamentoService;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public FilamentoResponseDto createFilamento(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody CreateFilamentoRequestDto filamentoRequestDto) {
        return filamentoService.createFilamento(usuario, filamentoRequestDto);
    }

    @GetMapping
    public List<FilamentoResponseDto> listFilamentos(@AuthenticationPrincipal Usuario usuario) {
        return filamentoService.listFilamentos(usuario);
    }

    @PutMapping("/{uuid}")
    public FilamentoResponseDto updateFilamento(@AuthenticationPrincipal Usuario usuario,@PathVariable UUID uuid, @Valid @RequestBody UpdateFilamentoDto filamentoDto) {
        return filamentoService.updateFilamento(usuario, uuid, filamentoDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilamento(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuid) {
        filamentoService.deleteFilamento(usuario, uuid);
    }
}
