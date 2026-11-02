package com.Geek3DShop.Calculadora_3D.impressora.service;

import com.Geek3DShop.Calculadora_3D.impressora.dto.CreateImpressoraRequestDto;
import com.Geek3DShop.Calculadora_3D.impressora.dto.ImpressoraResponseDto;
import com.Geek3DShop.Calculadora_3D.impressora.dto.UpdateImpressoraDto;
import com.Geek3DShop.Calculadora_3D.impressora.entity.Impressora;
import com.Geek3DShop.Calculadora_3D.impressora.repository.ImpressoraRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImpressoraService {
    private final ImpressoraRepository impressoraRepository;

    public ImpressoraService(ImpressoraRepository impressoraRepository) {
        this.impressoraRepository = impressoraRepository;
    }

    @Transactional
    public ImpressoraResponseDto createImpressora(Usuario usuario, CreateImpressoraRequestDto impressoraRequestDto) {
        Optional<Impressora> impressora = impressoraRepository.findByUsuarioAndNomeIgnoreCase(usuario, impressoraRequestDto.nome());

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
                .usuario(usuario)
                .nome(impressoraRequestDto.nome())
                .custoAquisicao(impressoraRequestDto.custoAquisicao())
                .vidaUtilHoras(impressoraRequestDto.vidaUtilHoras())
                .potenciaMediaAmsW(impressoraRequestDto.potenciaMediaAmsW())
                .build();

        var entitySaved = impressoraRepository.save(entity);

        return ImpressoraResponseDto.from(entitySaved);
    }

    @Transactional(readOnly = true)
    public List<ImpressoraResponseDto> listImpressoras(Usuario usuario) {
        var list = impressoraRepository.findByUsuarioAndActiveTrueAndDeletedFalseOrderByNomeAsc(usuario);
        return list.stream().map(ImpressoraResponseDto::from).toList();
    }

    @Transactional
    public ImpressoraResponseDto updateImpressora(Usuario usuario, UUID uuid, UpdateImpressoraDto updateImpressoraDto) {
        Impressora impressora = impressoraRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuid).orElseThrow(
                () -> new ResourceNotFoundException("Impressora: " + uuid + " não foi encontrada")
        );

        impressoraRepository.findByUsuarioAndNomeIgnoreCaseAndUuidNot(usuario, updateImpressoraDto.nome(), uuid).ifPresent(_ -> {
            throw new ResourceAlreadyExistsException("Impressora: " + updateImpressoraDto.nome() + " já está cadastrada");
        });

        impressora.setNome(updateImpressoraDto.nome());
        impressora.setCustoAquisicao(updateImpressoraDto.custoAquisicao());
        impressora.setVidaUtilHoras(updateImpressoraDto.vidaUtilHoras());
        impressora.setPotenciaMediaAmsW(updateImpressoraDto.potenciaMediaAmsW());

        impressoraRepository.save(impressora);

        return ImpressoraResponseDto.from(impressora);
    }

    @Transactional
    public void deleteImpressora(Usuario usuario, UUID uuid) {
        Impressora impressora = impressoraRepository.findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(usuario, uuid).orElseThrow(
            () -> new ResourceNotFoundException("Impressora: " + uuid + " não foi encontrada")
        );

        impressora.setActive(false);
        impressora.setDeleted(true);

        impressoraRepository.save(impressora);

        // As potências não são apagadas, para o caso de uma impressora ser reativada, elas voltarem juntas
    }
}
