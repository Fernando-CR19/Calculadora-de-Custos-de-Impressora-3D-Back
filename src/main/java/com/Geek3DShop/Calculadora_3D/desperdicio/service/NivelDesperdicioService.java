package com.Geek3DShop.Calculadora_3D.desperdicio.service;

import com.Geek3DShop.Calculadora_3D.desperdicio.dto.CreateNivelDesperdicioRequestDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.dto.NivelDesperdicioResponseDto;
import com.Geek3DShop.Calculadora_3D.desperdicio.entity.NivelDesperdicio;
import com.Geek3DShop.Calculadora_3D.desperdicio.repository.NivelDesperdicioRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
