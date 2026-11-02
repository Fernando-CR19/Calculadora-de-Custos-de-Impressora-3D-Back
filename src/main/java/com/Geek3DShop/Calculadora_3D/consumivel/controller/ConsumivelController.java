package com.Geek3DShop.Calculadora_3D.consumivel.controller;

import com.Geek3DShop.Calculadora_3D.consumivel.dto.ConsumivelResponseDto;
import com.Geek3DShop.Calculadora_3D.consumivel.dto.CreateConsumivelRequestDto;
import com.Geek3DShop.Calculadora_3D.consumivel.dto.UpdateConsumivelDto;
import com.Geek3DShop.Calculadora_3D.consumivel.service.ConsumivelService;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consumiveis")
public class ConsumivelController {
    private final ConsumivelService consumivelService;

    public ConsumivelController(ConsumivelService consumivelService) {
        this.consumivelService = consumivelService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsumivelResponseDto createConsumivel(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody CreateConsumivelRequestDto consumivelDto) {
        return consumivelService.createConsumivel(usuario, consumivelDto);
    }

    @GetMapping
    public List<ConsumivelResponseDto> listConsumiveis(@AuthenticationPrincipal Usuario usuario) {
        return consumivelService.listConsumiveis(usuario);
    }

    @PutMapping("/{uuid}")
    public ConsumivelResponseDto updateConsumivel(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuid, @Valid @RequestBody UpdateConsumivelDto consumivelDto) {
        return consumivelService.updateConsumivel(usuario, uuid, consumivelDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsumivel(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID uuid) {
        consumivelService.deleteConsumivel(usuario, uuid);
    }
}
