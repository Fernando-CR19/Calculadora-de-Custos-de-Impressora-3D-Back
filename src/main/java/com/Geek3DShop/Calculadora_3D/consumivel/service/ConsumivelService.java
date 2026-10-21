package com.Geek3DShop.Calculadora_3D.consumivel.service;

import com.Geek3DShop.Calculadora_3D.consumivel.dto.ConsumivelResponseDto;
import com.Geek3DShop.Calculadora_3D.consumivel.dto.CreateConsumivelRequestDto;
import com.Geek3DShop.Calculadora_3D.consumivel.entity.Consumivel;
import com.Geek3DShop.Calculadora_3D.consumivel.repository.ConsumivelRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumivelService {
    private final ConsumivelRepository consumivelRepository;

    public ConsumivelService(ConsumivelRepository consumivelRepository) {
        this.consumivelRepository = consumivelRepository;
    }

    @Transactional
    public ConsumivelResponseDto createConsumivel(CreateConsumivelRequestDto consumivelDto) {
        Optional<Consumivel> consumivel = consumivelRepository.findByNomeIgnoreCase(consumivelDto.nome());

        if (consumivel.isPresent()) {
            Consumivel consumivelFound = consumivel.get();

            if (consumivelFound.isDeleted()) {
                consumivelFound.setActive(true);
                consumivelFound.setDeleted(false);
                consumivelFound.setNome(consumivelDto.nome());
                consumivelFound.setPrecoKit(consumivelDto.precoKit());
                consumivelFound.setQuantidadeKit(consumivelDto.quantidadeKit());

                consumivelRepository.save(consumivelFound);

                return ConsumivelResponseDto.from(consumivelFound);
            }

            throw new ResourceAlreadyExistsException("Consumível: " + consumivelDto.nome() + " já está cadastrado");
        }

        var entity = new Consumivel(consumivelDto.nome(), consumivelDto.precoKit(), consumivelDto.quantidadeKit());
        var entitySaved = consumivelRepository.save(entity);

        return ConsumivelResponseDto.from(entitySaved);
    }

    @Transactional(readOnly = true)
    public List<ConsumivelResponseDto> listConsumiveis () {
        var list = consumivelRepository.findByActiveTrueAndDeletedFalseOrderByNomeAsc();
        return list.stream().map(ConsumivelResponseDto::from).toList();
    }
}
