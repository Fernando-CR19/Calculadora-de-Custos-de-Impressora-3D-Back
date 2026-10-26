package com.Geek3DShop.Calculadora_3D.energia.service;

import com.Geek3DShop.Calculadora_3D.energia.dto.EnergiaResponseDto;
import com.Geek3DShop.Calculadora_3D.energia.repository.EnergiaRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnergiaService {
    private final EnergiaRepository energiaRepository;

    public EnergiaService(EnergiaRepository energiaRepository) {
        this.energiaRepository = energiaRepository;
    }

    @Transactional(readOnly = true)
    public EnergiaResponseDto getEnergia() {
        var energia = energiaRepository.findFirstByOrderByIdAsc().orElseThrow(
                () -> new ResourceNotFoundException("A fatura de energia ainda não foi cadastrada")
        );

        return EnergiaResponseDto.from(energia);
    }
}
