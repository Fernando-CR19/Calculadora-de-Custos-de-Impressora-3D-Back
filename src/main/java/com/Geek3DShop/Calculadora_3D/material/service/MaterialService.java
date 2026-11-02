package com.Geek3DShop.Calculadora_3D.material.service;

import com.Geek3DShop.Calculadora_3D.filamento.repository.FilamentoRepository;
import com.Geek3DShop.Calculadora_3D.material.dto.CreateMaterialRequestDto;
import com.Geek3DShop.Calculadora_3D.material.dto.MaterialResponseDto;
import com.Geek3DShop.Calculadora_3D.material.dto.UpdateMaterialDto;
import com.Geek3DShop.Calculadora_3D.material.entity.Material;
import com.Geek3DShop.Calculadora_3D.material.repository.MaterialRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;

import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final FilamentoRepository filamentoRepository;

    public MaterialService(MaterialRepository materialRepository, FilamentoRepository filamentoRepository) {
        this.materialRepository = materialRepository;
        this.filamentoRepository = filamentoRepository;
    }

    @Transactional
    public MaterialResponseDto createMaterial(Usuario usuario, CreateMaterialRequestDto materialDto) {
        Optional<Material> material = materialRepository.findByUsuarioAndNomeIgnoreCase(usuario, materialDto.nome());

        if (material.isPresent()) {
            Material materialFound = material.get();

            if (materialFound.isDeleted()) {
                materialFound.setNome(materialDto.nome());
                materialFound.setActive(true);
                materialFound.setDeleted(false);

                materialRepository.save(materialFound);

                return MaterialResponseDto.from(materialFound);
            }

            throw new ResourceAlreadyExistsException("Material: " + materialDto.nome() + " já está cadastrado");
        }

        var entity = new Material(usuario, materialDto.nome());
        var entitySaved = materialRepository.save(entity);
        return MaterialResponseDto.from(entitySaved);
    }

    @Transactional(readOnly = true)
    public List<MaterialResponseDto> listMaterials(Usuario usuario) {
        var list = materialRepository.findByUsuarioAndActiveTrueAndDeletedFalseOrderByNomeAsc(usuario);
        return list.stream().map(MaterialResponseDto::from).toList();
    }

    @Transactional
    public MaterialResponseDto updateMaterial(Usuario usuario, UUID uuid, UpdateMaterialDto materialDto) {
        Material material = materialRepository.findByUuidAndUsuarioAndActiveTrueAndDeletedFalse(uuid, usuario).orElseThrow(
                () -> new ResourceNotFoundException("Material: " + uuid + " não encontrado")
        );

        materialRepository.findByNomeIgnoreCaseAndUsuarioAndUuidNot(materialDto.nome(), usuario, uuid).ifPresent(_ -> {
            throw new ResourceAlreadyExistsException("Material: " + materialDto.nome() + " já está cadastrado");
        });

        material.setNome(materialDto.nome());

        materialRepository.save(material);

        return MaterialResponseDto.from(material);
    }

    @Transactional
    public void deleteMaterial(Usuario usuario, UUID uuid) {
        Material material = materialRepository.findByUuidAndUsuarioAndActiveTrueAndDeletedFalse(uuid, usuario).orElseThrow(
                () -> new ResourceNotFoundException("Material: " + uuid + " não encontrado")
        );

        material.setActive(false);
        material.setDeleted(true);

        materialRepository.save(material);

        filamentoRepository.softDeleteFilamento(material.getId());
    }
}
