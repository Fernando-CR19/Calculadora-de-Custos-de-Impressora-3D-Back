package com.Geek3DShop.Calculadora_3D.lucro.controller;

import com.Geek3DShop.Calculadora_3D.lucro.dto.CreateMargemLucroRequestDto;
import com.Geek3DShop.Calculadora_3D.lucro.dto.MargemLucroResponseDto;
import com.Geek3DShop.Calculadora_3D.lucro.dto.UpdateMargemLucroDto;
import com.Geek3DShop.Calculadora_3D.lucro.service.MargemLucroService;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/margens-lucro")
public class MargemLucroController {
    private final MargemLucroService margemLucroService;

    public MargemLucroController(MargemLucroService margemLucroService) {
        this.margemLucroService = margemLucroService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MargemLucroResponseDto createMargemLucro(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody CreateMargemLucroRequestDto margemLucroRequestDto) {
        return margemLucroService.createMargemLucro(usuario, margemLucroRequestDto);
    }

    @GetMapping
    public List<MargemLucroResponseDto> listMargensLucro(@AuthenticationPrincipal Usuario usuario) {
        return margemLucroService.listMargensLucro(usuario);
    }

    @PutMapping("/{uuid}")
    public MargemLucroResponseDto updateMargemLucro(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuid, @Valid @RequestBody UpdateMargemLucroDto updateMargemLucroDto) {
        return margemLucroService.updateMargemLucro(usuario, uuid, updateMargemLucroDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMargemLucro(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuid) {
        margemLucroService.deleteMargemLucro(usuario, uuid);
    }
}
