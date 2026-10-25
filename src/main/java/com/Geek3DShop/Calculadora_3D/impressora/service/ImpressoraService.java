package com.Geek3DShop.Calculadora_3D.impressora.service;

import com.Geek3DShop.Calculadora_3D.impressora.dto.CreateImpressoraRequestDto;
import com.Geek3DShop.Calculadora_3D.impressora.dto.ImpressoraResponseDto;
import com.Geek3DShop.Calculadora_3D.impressora.entity.Impressora;
import com.Geek3DShop.Calculadora_3D.impressora.repository.ImpressoraRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImpressoraService {
    private final ImpressoraRepository impressoraRepository;

    public ImpressoraService(ImpressoraRepository impressoraRepository) {
        this.impressoraRepository = impressoraRepository;
    }

    @Transactional
    public ImpressoraResponseDto createImpressora(CreateImpressoraRequestDto impressoraRequestDto) {
        Optional<Impressora> impressora = impressoraRepository.findByNomeIgnoreCase(impressoraRequestDto.nome());

        if (impressora.isPresent()) {
            Impressora impressoraFound = impressora.get();

            if (impressoraFound.isDeleted()) {
                impressoraFound.setActive(true);
                impressoraFound.setDeleted(false);
                impressoraFound.setNome(impressoraRequestDto.nome());
                impressoraFound.setCustoAquisicao(impressoraRequestDto.custoAquisicao());
                impressoraFound.setVidaUtilHoras(impressoraRequestDto.vidaUtilHoras());
                impressoraFound.setPotenciaMediaAmsW(impressoraRequestDto.potenciaMediaAmsW());

                impressoraRepository.save(impressoraFound);

                return ImpressoraResponseDto.from(impressoraFound);
            }

            throw new ResourceAlreadyExistsException("A impressora: " + impressoraRequestDto.nome() + " já está cadastrada");
        }

        var entity = Impressora.builder()
                .nome(impressoraRequestDto.nome())
                .custoAquisicao(impressoraRequestDto.custoAquisicao())
                .vidaUtilHoras(impressoraRequestDto.vidaUtilHoras())
                .potenciaMediaAmsW(impressoraRequestDto.potenciaMediaAmsW())
                .build();

        var entitySaved = impressoraRepository.save(entity);

        return ImpressoraResponseDto.from(entitySaved);
    }
}
