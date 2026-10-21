package com.Geek3DShop.Calculadora_3D.material.controller;

import com.Geek3DShop.Calculadora_3D.material.dto.CreateMaterialRequestDto;
import com.Geek3DShop.Calculadora_3D.material.dto.MaterialResponseDto;
import com.Geek3DShop.Calculadora_3D.material.dto.UpdateMaterialDto;
import com.Geek3DShop.Calculadora_3D.material.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/materiais")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialResponseDto createMaterial(@Valid @RequestBody CreateMaterialRequestDto materialRequestDto) {
        return materialService.createMaterial(materialRequestDto);
    }

    @GetMapping
    public List<MaterialResponseDto> listMaterials() {
        return materialService.listMaterials();
    }

    @PutMapping("/{uuid}")
    public MaterialResponseDto updateMaterial(@PathVariable UUID uuid, @Valid @RequestBody UpdateMaterialDto materialDto) {
        return materialService.updateMaterial(uuid, materialDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterial(@PathVariable UUID uuid) {
        materialService.deleteMaterial(uuid);
    }
}
