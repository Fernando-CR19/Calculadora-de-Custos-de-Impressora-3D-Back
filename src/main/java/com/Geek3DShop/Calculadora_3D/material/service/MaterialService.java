package com.Geek3DShop.Calculadora_3D.material.service;

import com.Geek3DShop.Calculadora_3D.material.dto.CreateMaterialRequestDto;
import com.Geek3DShop.Calculadora_3D.material.dto.MaterialResponseDto;
import com.Geek3DShop.Calculadora_3D.material.dto.UpdateMaterialDto;
import com.Geek3DShop.Calculadora_3D.material.entity.Material;
import com.Geek3DShop.Calculadora_3D.material.repository.MaterialRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Transactional
    public MaterialResponseDto createMaterial(CreateMaterialRequestDto materialDto) {
        Optional<Material> material = materialRepository.findByNomeIgnoreCase(materialDto.nome());

        if (material.isPresent()) {
            Material materialFound = material.get();

            if (materialFound.isDeleted()) {
                materialFound.setNome(materialDto.nome());
                materialFound.setActive(true);
                materialFound.setDeleted(false);

                materialRepository.save(materialFound);

                return new MaterialResponseDto(materialFound.getUuid(), materialFound.getNome());
            }

            throw new ResourceAlreadyExistsException("Material: " + materialDto.nome() + " já está cadastrado");
        }

        var entity = new Material(materialDto.nome());
        var entitySaved = materialRepository.save(entity);
        return new MaterialResponseDto(entitySaved.getUuid(), entitySaved.getNome());
    }

    public List<MaterialResponseDto> listMaterials() {
        var list = materialRepository.findByActiveTrueAndDeletedFalseOrderByNomeAsc();
        return list.stream().map(material -> new MaterialResponseDto(material.getUuid(), material.getNome())).toList();
    }

    @Transactional
    public MaterialResponseDto updateMaterial(UUID uuid, UpdateMaterialDto materialDto) {
        Material material = materialRepository.findByUuidAndActiveTrueAndDeletedFalse(uuid).orElseThrow(
                () -> new ResourceNotFoundException("Material: " + uuid + " não encontrado")
        );

        materialRepository.findByNomeIgnoreCaseAndUuidNot(materialDto.nome(), uuid).ifPresent(_ -> {
            throw new ResourceAlreadyExistsException("Material: " + materialDto.nome() + " já está cadastrado");
        });

        material.setNome(materialDto.nome());

        materialRepository.save(material);

        return new MaterialResponseDto(material.getUuid(), material.getNome());
    }

    @Transactional
    public void deleteMaterial(UUID uuid) {
        Material material = materialRepository.findByUuidAndActiveTrueAndDeletedFalse(uuid).orElseThrow(
                () -> new ResourceNotFoundException("Material: " + uuid + " não encontrado")
        );

        material.setActive(false);
        material.setDeleted(true);

        materialRepository.save(material);

        // TODO: implementar lógica para fazer soft delete nos filamentos do material deletado
    }
}
