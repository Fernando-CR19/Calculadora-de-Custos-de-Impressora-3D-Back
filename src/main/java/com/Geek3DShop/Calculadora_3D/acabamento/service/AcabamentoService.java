package com.Geek3DShop.Calculadora_3D.acabamento.service;

import com.Geek3DShop.Calculadora_3D.acabamento.dto.AcabamentoRequestDto;
import com.Geek3DShop.Calculadora_3D.acabamento.dto.AcabamentoResponseDto;
import com.Geek3DShop.Calculadora_3D.acabamento.entity.Acabamento;
import com.Geek3DShop.Calculadora_3D.acabamento.repository.AcabamentoRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcabamentoService {
    private final AcabamentoRepository acabamentoRepository;

    public AcabamentoService(AcabamentoRepository acabamentoRepository) {
        this.acabamentoRepository = acabamentoRepository;
    }

    @Transactional
    public AcabamentoResponseDto upsertAcabamento(AcabamentoRequestDto acabamentoRequestDto) {
        var acabamento = acabamentoRepository.findFirstByOrderByIdAsc();

        if (acabamento.isPresent()) {
            Acabamento acabamentoFound = acabamento.get();

            acabamentoFound.setValorHora(acabamentoRequestDto.valorHora());

            acabamentoRepository.save(acabamentoFound);

            return AcabamentoResponseDto.from(acabamentoFound);
        }

        var entity = new Acabamento(acabamentoRequestDto.valorHora());
        var entitySaved = acabamentoRepository.save(entity);

        return AcabamentoResponseDto.from(entitySaved);
    }

    @Transactional(readOnly = true)
    public AcabamentoResponseDto getAcabamento() {
        var acabamento = acabamentoRepository.findFirstByOrderByIdAsc().orElseThrow(
                () -> new ResourceNotFoundException("O valor da hora trabalhada no acabamento não foi cadastrado")
        );

        return AcabamentoResponseDto.from(acabamento);
    }
}
