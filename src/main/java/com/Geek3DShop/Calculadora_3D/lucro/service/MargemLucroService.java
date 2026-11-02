package com.Geek3DShop.Calculadora_3D.lucro.service;

import com.Geek3DShop.Calculadora_3D.lucro.dto.CreateMargemLucroRequestDto;
import com.Geek3DShop.Calculadora_3D.lucro.dto.MargemLucroResponseDto;
import com.Geek3DShop.Calculadora_3D.lucro.dto.UpdateMargemLucroDto;
import com.Geek3DShop.Calculadora_3D.lucro.entity.MargemLucro;
import com.Geek3DShop.Calculadora_3D.lucro.repository.MargemLucroRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MargemLucroService {
    private final MargemLucroRepository margemLucroRepository;

    public MargemLucroService(MargemLucroRepository margemLucroRepository) {
        this.margemLucroRepository = margemLucroRepository;
    }

    @Transactional
    public MargemLucroResponseDto createMargemLucro(Usuario usuario, CreateMargemLucroRequestDto margemLucroRequestDto) {
        if (margemLucroRepository.existsByUsuarioAndNomeIgnoreCaseAndDeletedFalse(usuario, margemLucroRequestDto.nome())) {
            throw new ResourceAlreadyExistsException("Margem de lucro com nome: " + margemLucroRequestDto.nome() + " já está cadastrada");
        }

        if (margemLucroRepository.existsByUsuarioAndPercentualAndDeletedFalse(usuario, margemLucroRequestDto.percentual())) {
            throw new ResourceAlreadyExistsException("Margem de lucro com percentual: " + margemLucroRequestDto.percentual() + " já está cadastrada");
        }

        var entity = new MargemLucro(usuario, margemLucroRequestDto.nome(), margemLucroRequestDto.percentual());
        var entitySaved = margemLucroRepository.save(entity);

        return MargemLucroResponseDto.from(entitySaved);
    }

    @Transactional(readOnly = true)
    public List<MargemLucroResponseDto> listMargensLucro(Usuario usuario) {
        var list = margemLucroRepository.findByUsuarioAndDeletedFalseAndActiveTrueOrderByPercentualAsc(usuario);
        return list.stream().map(MargemLucroResponseDto::from).toList();
    }

    @Transactional
    public MargemLucroResponseDto updateMargemLucro(Usuario usuario, UUID uuid, UpdateMargemLucroDto updateMargemLucroDto) {
        MargemLucro margemLucro = margemLucroRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuid).orElseThrow(
                () -> new ResourceNotFoundException("Margem de lucro: " + uuid + " não foi encontrada")
        );

        if (margemLucroRepository.existsByUsuarioAndNomeIgnoreCaseAndDeletedFalseAndUuidNot(usuario, updateMargemLucroDto.nome(), uuid)) {
            throw new ResourceAlreadyExistsException("Margem de lucro com nome: " + updateMargemLucroDto.nome() + " já está cadastrada");
        }

        if (margemLucroRepository.existsByUsuarioAndPercentualAndDeletedFalseAndUuidNot(usuario, updateMargemLucroDto.percentual(), uuid)) {
            throw new ResourceAlreadyExistsException("Margem de lucro com percentual: " + updateMargemLucroDto.percentual() + " já está cadastrada");
        }

        margemLucro.setNome(updateMargemLucroDto.nome());
        margemLucro.setPercentual(updateMargemLucroDto.percentual());

        margemLucroRepository.save(margemLucro);

        return MargemLucroResponseDto.from(margemLucro);
    }

    @Transactional
    public void deleteMargemLucro(Usuario usuario, UUID uuid) {
        MargemLucro margemLucro = margemLucroRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuid).orElseThrow(
                () -> new ResourceNotFoundException("A margem de lucro: " + uuid + " não foi encontrada")
        );

        margemLucro.setActive(false);
        margemLucro.setDeleted(true);

        margemLucroRepository.save(margemLucro);
    }
}
