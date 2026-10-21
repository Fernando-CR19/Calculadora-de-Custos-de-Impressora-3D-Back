package com.Geek3DShop.Calculadora_3D.consumivel.service;

import com.Geek3DShop.Calculadora_3D.consumivel.dto.ConsumivelResponseDto;
import com.Geek3DShop.Calculadora_3D.consumivel.dto.CreateConsumivelRequestDto;
import com.Geek3DShop.Calculadora_3D.consumivel.dto.UpdateConsumivelDto;
import com.Geek3DShop.Calculadora_3D.consumivel.entity.Consumivel;
import com.Geek3DShop.Calculadora_3D.consumivel.repository.ConsumivelRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public List<ConsumivelResponseDto> listConsumiveis() {
        var list = consumivelRepository.findByActiveTrueAndDeletedFalseOrderByNomeAsc();
        return list.stream().map(ConsumivelResponseDto::from).toList();
    }

    @Transactional
    public ConsumivelResponseDto updateConsumivel(UUID uuid, UpdateConsumivelDto consumivelDto) {
        Consumivel consumivel = consumivelRepository.findByUuidAndActiveTrueAndDeletedFalse(uuid).orElseThrow(
                () -> new ResourceNotFoundException("Consumível: " + uuid + " não foi encontrado")
        );

        consumivelRepository.findByNomeIgnoreCaseAndUuidNot(consumivelDto.nome(), uuid).ifPresent(_ -> {
            throw new ResourceAlreadyExistsException("Consumível: " + consumivelDto.nome() + " já está cadastrado");
        });

        consumivel.setNome(consumivelDto.nome());
        consumivel.setPrecoKit(consumivelDto.precoKit());
        consumivel.setQuantidadeKit(consumivelDto.quantidadeKit());

        consumivelRepository.save(consumivel);

        return ConsumivelResponseDto.from(consumivel);
    }

    @Transactional
    public void deleteConsumivel(UUID uuid) {
        Consumivel consumivel = consumivelRepository.findByUuidAndActiveTrueAndDeletedFalse(uuid).orElseThrow(
            () -> new ResourceNotFoundException("Consumível: "+ uuid + " não foi encontrado")
        );

        consumivel.setActive(false);
        consumivel.setDeleted(true);

        consumivelRepository.save(consumivel);
    }
}
