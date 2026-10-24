package com.Geek3DShop.Calculadora_3D.desperdicio.service;

import com.Geek3DShop.Calculadora_3D.desperdicio.dto.CreateNivelDesperdicioRequestDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.dto.NivelDesperdicioResponseDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.dto.UpdateNivelDesperdicioDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.entity.NivelDesperdicio;
import com.Geek3DShop.Calculadora_3D.desperdicio.repository.NivelDesperdicioRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class NivelDesperdicioService {
    private final NivelDesperdicioRepository nivelDesperdicioRepository;

    public NivelDesperdicioService(NivelDesperdicioRepository nivelDesperdicioRepository) {
        this.nivelDesperdicioRepository = nivelDesperdicioRepository;
    }

    @Transactional
    public NivelDesperdicioResponseDto createNivelDesperdicio(CreateNivelDesperdicioRequestDto nivelDesperdicioDto) {
        if (nivelDesperdicioRepository.existsByNomeIgnoreCaseAndDeletedFalse(nivelDesperdicioDto.nome())) {
            throw new ResourceAlreadyExistsException("O nível: " + nivelDesperdicioDto.nome() + " já está cadastrado");
        }

        if (nivelDesperdicioRepository.existsByPercentualAndDeletedFalse(nivelDesperdicioDto.percentual())) {
            throw new ResourceAlreadyExistsException("O percentual: " + nivelDesperdicioDto.percentual() + " já está cadastrado");
        }

        var entity = new NivelDesperdicio(nivelDesperdicioDto.nome(), nivelDesperdicioDto.percentual());
        var entitySaved = nivelDesperdicioRepository.save(entity);

        return NivelDesperdicioResponseDto.from(entitySaved);
    }

    @Transactional(readOnly = true)
    public List<NivelDesperdicioResponseDto> listNiveisDesperdicio() {
        var list = nivelDesperdicioRepository.findByDeletedFalseAndActiveTrueOrderByPercentualAsc();
        return list.stream().map(NivelDesperdicioResponseDto::from).toList();
    }

    @Transactional
    public NivelDesperdicioResponseDto updateNivelDesperdicio(UUID uuid, UpdateNivelDesperdicioDto nivelDesperdicioDto) {
        NivelDesperdicio nivelDesperdicio = nivelDesperdicioRepository.findByUuidAndDeletedFalseAndActiveTrue(uuid).orElseThrow(
                () -> new ResourceNotFoundException("Nível de Desperdício: " + uuid + " não foi encontrado")
        );

        if (nivelDesperdicioRepository.existsByNomeIgnoreCaseAndDeletedFalseAndUuidNot(nivelDesperdicioDto.nome(), uuid)) {
            throw new ResourceAlreadyExistsException("O nível: " + nivelDesperdicioDto.nome() + " já está cadastrado");
        }

        if (nivelDesperdicioRepository.existsByPercentualAndDeletedFalseAndUuidNot(nivelDesperdicioDto.percentual(), uuid)) {
            throw new ResourceAlreadyExistsException("O percentual: " + nivelDesperdicioDto.percentual() + " já está cadastrado");
        }

        nivelDesperdicio.setNome(nivelDesperdicioDto.nome());
        nivelDesperdicio.setPercentual(nivelDesperdicioDto.percentual());

        nivelDesperdicioRepository.save(nivelDesperdicio);

        return NivelDesperdicioResponseDto.from(nivelDesperdicio);
    }

    @Transactional
    public void deleteNivelDesperdicio(UUID uuid) {
        NivelDesperdicio nivelDesperdicio = nivelDesperdicioRepository.findByUuidAndDeletedFalseAndActiveTrue(uuid).orElseThrow(
                () -> new ResourceNotFoundException("Nível de Desperdício: " + uuid + " não foi encontrado")
        );

        nivelDesperdicio.setActive(false);
        nivelDesperdicio.setDeleted(true);

        nivelDesperdicioRepository.save(nivelDesperdicio);
    }
}
