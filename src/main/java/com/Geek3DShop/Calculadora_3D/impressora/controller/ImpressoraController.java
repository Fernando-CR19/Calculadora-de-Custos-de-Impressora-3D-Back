package com.Geek3DShop.Calculadora_3D.impressora.controller;

import com.Geek3DShop.Calculadora_3D.impressora.dto.CreateImpressoraRequestDto;
import com.Geek3DShop.Calculadora_3D.impressora.dto.ImpressoraResponseDto;
import com.Geek3DShop.Calculadora_3D.impressora.dto.UpdateImpressoraDto;
import com.Geek3DShop.Calculadora_3D.impressora.service.ImpressoraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ImpressoraResponseDto createImpressora(@Valid @RequestBody CreateImpressoraRequestDto createImpressoraRequestDto) {
        return impressoraService.createImpressora(createImpressoraRequestDto);
    }

    @GetMapping
    public List<ImpressoraResponseDto> listImpressoras() {
        return impressoraService.listImpressoras();
    }

    @PutMapping("/{uuid}")
    public ImpressoraResponseDto updateImpressora(@PathVariable UUID uuid, @Valid @RequestBody UpdateImpressoraDto updateImpressoraDto) {
        return impressoraService.updateImpressora(uuid, updateImpressoraDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImpressora(@PathVariable UUID uuid) {
        impressoraService.deleteImpressora(uuid);
    }
}
