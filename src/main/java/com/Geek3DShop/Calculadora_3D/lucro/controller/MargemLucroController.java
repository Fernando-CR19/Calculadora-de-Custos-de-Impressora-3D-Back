package com.Geek3DShop.Calculadora_3D.lucro.controller;

import com.Geek3DShop.Calculadora_3D.lucro.dto.CreateMargemLucroRequestDto;
import com.Geek3DShop.Calculadora_3D.lucro.dto.MargemLucroResponseDto;
import com.Geek3DShop.Calculadora_3D.lucro.service.MargemLucroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/margens-lucro")
public class MargemLucroController {
    private final MargemLucroService margemLucroService;

    public MargemLucroController (MargemLucroService margemLucroService) {
        this.margemLucroService = margemLucroService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MargemLucroResponseDto createMargemLucro(@Valid @RequestBody CreateMargemLucroRequestDto margemLucroRequestDto) {
        return margemLucroService.createMargemLucro(margemLucroRequestDto);
    }

    @GetMapping
    public List<MargemLucroResponseDto> listMargensLucro() {
        return margemLucroService.listMargensLucro();
    }
}
