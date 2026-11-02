package com.Geek3DShop.Calculadora_3D.potencia.service;

import com.Geek3DShop.Calculadora_3D.impressora.entity.Impressora;
import com.Geek3DShop.Calculadora_3D.impressora.repository.ImpressoraRepository;
import com.Geek3DShop.Calculadora_3D.material.entity.Material;
import com.Geek3DShop.Calculadora_3D.material.repository.MaterialRepository;
import com.Geek3DShop.Calculadora_3D.potencia.dto.CreatePotenciaImpressoraRequestDto;
import com.Geek3DShop.Calculadora_3D.potencia.dto.PotenciaImpressoraResponseDto;
import com.Geek3DShop.Calculadora_3D.potencia.dto.UpdatePotenciaImpressoraDto;
import com.Geek3DShop.Calculadora_3D.potencia.entity.PotenciaImpressora;
import com.Geek3DShop.Calculadora_3D.potencia.repository.PotenciaImpressoraRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PotenciaImpressoraService {
    private final PotenciaImpressoraRepository potenciaImpressoraRepository;
    private final ImpressoraRepository impressoraRepository;
    private final MaterialRepository materialRepository;

    public PotenciaImpressoraService(PotenciaImpressoraRepository potenciaImpressoraRepository, ImpressoraRepository impressoraRepository, MaterialRepository materialRepository) {
        this.potenciaImpressoraRepository = potenciaImpressoraRepository;
        this.impressoraRepository = impressoraRepository;
        this.materialRepository = materialRepository;
    }

    @Transactional
    public PotenciaImpressoraResponseDto createPotenciaImpressora(Usuario usuario, UUID uuidImpressora, CreatePotenciaImpressoraRequestDto createPotenciaImpressoraRequestDto) {
        Impressora impressora = impressoraRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuidImpressora).orElseThrow(
                () -> new ResourceNotFoundException("Impressora: " + uuidImpressora + " não foi encontrada")
        );

        Material material = materialRepository.findByUuidAndUsuarioAndActiveTrueAndDeletedFalse(createPotenciaImpressoraRequestDto.uuidMaterial(), usuario).orElseThrow(
                () -> new ResourceNotFoundException("Material: " + createPotenciaImpressoraRequestDto.uuidMaterial() + " não foi encontrado")
        );

        if (potenciaImpressoraRepository.existsByImpressoraIdAndMaterialId(impressora.getId(), material.getId())) {
            throw new ResourceAlreadyExistsException("A impressora já tem potência cadastrada para esse material");
        }

        var entity = new PotenciaImpressora(material, impressora, createPotenciaImpressoraRequestDto.potenciaMediaW());
        var entitySaved = potenciaImpressoraRepository.save(entity);
        return PotenciaImpressoraResponseDto.from(entitySaved);
    }

    @Transactional(readOnly = true)
    public List<PotenciaImpressoraResponseDto> listPotenciasImpressora(Usuario usuario, UUID uuidImpressora) {
        Impressora impressora = impressoraRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuidImpressora).orElseThrow(
                () -> new ResourceNotFoundException("Impressora: " + uuidImpressora + " não foi encontrada")
        );

        var lista = potenciaImpressoraRepository.findByImpressoraIdAndMaterialActiveTrueAndMaterialDeletedFalseOrderByMaterialNomeAsc(impressora.getId());
        return lista.stream().map(PotenciaImpressoraResponseDto::from).toList();
    }

    @Transactional
    public PotenciaImpressoraResponseDto updatePotenciaImpressora(Usuario usuario, UUID uuidImpressora, UUID uuidMaterial, UpdatePotenciaImpressoraDto updatePotenciaImpressoraDto) {
        Impressora impressora = impressoraRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuidImpressora).orElseThrow(
                () -> new ResourceNotFoundException("Impressora: " + uuidImpressora + " não foi encontrada")
        );

        PotenciaImpressora potenciaImpressora = potenciaImpressoraRepository.findByImpressoraIdAndMaterialUuidAndMaterialActiveTrueAndMaterialDeletedFalse(impressora.getId(), uuidMaterial).orElseThrow(
                () -> new ResourceNotFoundException("Potência da impressora não foi encontrada")
        );

        potenciaImpressora.setPotenciaMediaW(updatePotenciaImpressoraDto.potenciaMediaW());

        potenciaImpressoraRepository.save(potenciaImpressora);

        return PotenciaImpressoraResponseDto.from(potenciaImpressora);
    }

    @Transactional
    public void deletePotenciaImpressora(Usuario usuario, UUID uuidImpressora, UUID uuidMaterial) {
        Impressora impressora = impressoraRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuidImpressora).orElseThrow(
                () -> new ResourceNotFoundException("Impressora: " + uuidImpressora + " não foi encontrada")
        );

        PotenciaImpressora potenciaImpressora = potenciaImpressoraRepository.findByImpressoraIdAndMaterialUuidAndMaterialActiveTrueAndMaterialDeletedFalse(impressora.getId(), uuidMaterial).orElseThrow(
                () -> new ResourceNotFoundException("Potência da impressora não foi encontrada")
        );

        potenciaImpressoraRepository.delete(potenciaImpressora);
    }
}
