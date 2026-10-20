package com.Geek3DShop.Calculadora_3D.filamento.service;

import com.Geek3DShop.Calculadora_3D.filamento.dto.CreateFilamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.filamento.dto.FilamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.filamento.dto.UpdateFilamentoDto;
import com.Geek3DShop.Calculadora_3D.filamento.entity.Filamento;
import com.Geek3DShop.Calculadora_3D.filamento.repository.FilamentoRepository;
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
public class FilamentoService {
    private final FilamentoRepository filamentoRepository;
    private final MaterialRepository materialRepository;

    public FilamentoService(FilamentoRepository filamentoRepository, MaterialRepository materialRepository) {
        this.filamentoRepository = filamentoRepository;
        this.materialRepository = materialRepository;
    }

    @Transactional
    public FilamentoResponseDto createFilamento(CreateFilamentoRequestDto filamentoDto) {
        Material material = materialRepository.findByUuidAndActiveTrueAndDeletedFalse(filamentoDto.uuidMaterial()).orElseThrow(
                () -> new ResourceNotFoundException("Material: " + filamentoDto.uuidMaterial() + " não encontrado")
        );

        Optional<Filamento> filamento = filamentoRepository.findByMaterialIdAndCorIgnoreCase(material.getId(), filamentoDto.cor());

        if (filamento.isPresent()) {
            Filamento filamentoFound = filamento.get();

            if (filamentoFound.isDeleted()) {
                filamentoFound.setCor(filamentoDto.cor());
                filamentoFound.setPrecoKg(filamentoDto.precoKg());
                filamentoFound.setActive(true);
                filamentoFound.setDeleted(false);

                filamentoRepository.save(filamentoFound);

                return FilamentoResponseDto.from(filamentoFound);
            }

            throw new ResourceAlreadyExistsException("Filamento: " + material.getNome() + " cor: " + filamentoDto.cor() + " já existe");
        }

        var entity = new Filamento(filamentoDto.cor(), filamentoDto.precoKg(), material);
        filamentoRepository.save(entity);
        return FilamentoResponseDto.from(entity);
    }

    @Transactional(readOnly = true)
    public List<FilamentoResponseDto> listFilamentos() {
        var list = filamentoRepository.findByActiveTrueAndDeletedFalseOrderByMaterialNomeAscCorAsc();
        return list.stream().map(FilamentoResponseDto::from).toList();
    }

    @Transactional()
    public FilamentoResponseDto updateFilamento(UUID uuid, UpdateFilamentoDto filamentoDto) {
        Filamento filamento = filamentoRepository.findByUuidAndActiveTrueAndDeletedFalse(uuid).orElseThrow(
                () -> new ResourceNotFoundException("Filamento: " + uuid + " não encontrado")
        );

        Material material = materialRepository.findByUuidAndActiveTrueAndDeletedFalse(filamentoDto.uuidMaterial()).orElseThrow(
                () -> new ResourceNotFoundException("Material: " + filamentoDto.uuidMaterial() + " não encontrado")
        );

        filamentoRepository.findByCorIgnoreCaseAndMaterialUuidAndUuidNot(filamentoDto.cor(), material.getUuid(), uuid).ifPresent(_ -> {
            throw new ResourceAlreadyExistsException("Filamento: " + filamentoDto.cor() + " material: " + material.getNome() + " já existe");
        });

        filamento.setCor(filamentoDto.cor());
        filamento.setPrecoKg(filamentoDto.precoKg());
        filamento.setMaterial(material);

        filamentoRepository.save(filamento);

        return FilamentoResponseDto.from(filamento);
    }

    @Transactional()
    public void deleteFilamento(UUID uuid) {
        Filamento filamento = filamentoRepository.findByUuidAndActiveTrueAndDeletedFalse(uuid).orElseThrow(
                () -> new ResourceNotFoundException("Filamento: " + uuid + " não encontrado")
        );

        filamento.setActive(false);
        filamento.setDeleted(true);

        filamentoRepository.save(filamento);
    }
}