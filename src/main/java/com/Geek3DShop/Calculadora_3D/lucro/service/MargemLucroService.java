package com.Geek3DShop.Calculadora_3D.lucro.service;

import com.Geek3DShop.Calculadora_3D.lucro.dto.CreateMargemLucroRequestDto;
import com.Geek3DShop.Calculadora_3D.lucro.dto.MargemLucroResponseDto;
import com.Geek3DShop.Calculadora_3D.lucro.entity.MargemLucro;
import com.Geek3DShop.Calculadora_3D.lucro.repository.MargemLucroRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MargemLucroService {
    private final MargemLucroRepository margemLucroRepository;

    public MargemLucroService(MargemLucroRepository margemLucroRepository) {
        this.margemLucroRepository = margemLucroRepository;
    }

    @Transactional
    public MargemLucroResponseDto createMargemLucro(CreateMargemLucroRequestDto margemLucroRequestDto) {
        if (margemLucroRepository.existsByNomeIgnoreCaseAndDeletedFalse(margemLucroRequestDto.nome())) {
            throw new ResourceAlreadyExistsException("Margem de lucro com nome: " + margemLucroRequestDto.nome() + " já está cadastrada");
        }

        if (margemLucroRepository.existsByPercentualAndDeletedFalse(margemLucroRequestDto.percentual())) {
            throw new ResourceAlreadyExistsException("Margem de lucro com percentual: " + margemLucroRequestDto.percentual() + " já está cadastrada");
        }

        var entity = new MargemLucro(margemLucroRequestDto.nome(), margemLucroRequestDto.percentual());
        var entitySaved = margemLucroRepository.save(entity);

        return MargemLucroResponseDto.from(entitySaved);
    }

    @Transactional(readOnly = true)
    public List<MargemLucroResponseDto> listMargensLucro() {
        var list = margemLucroRepository.findByDeletedFalseAndActiveTrueOrderByPercentualAsc();
        return list.stream().map(MargemLucroResponseDto::from).toList();
    }
}
