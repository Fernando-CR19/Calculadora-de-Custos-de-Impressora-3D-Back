package com.Geek3DShop.Calculadora_3D.energia.service;

import com.Geek3DShop.Calculadora_3D.energia.dto.EnergiaRequestDto;
import com.Geek3DShop.Calculadora_3D.energia.dto.EnergiaResponseDto;
import com.Geek3DShop.Calculadora_3D.energia.entity.Energia;
import com.Geek3DShop.Calculadora_3D.energia.repository.EnergiaRepository;
import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceNotFoundException;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnergiaService {
    private final EnergiaRepository energiaRepository;

    public EnergiaService(EnergiaRepository energiaRepository) {
        this.energiaRepository = energiaRepository;
    }

    @Transactional
    public EnergiaResponseDto upsertEnergia(Usuario usuario, EnergiaRequestDto energiaRequestDto) {
        var energia = energiaRepository.findByUsuario(usuario);

        if (energia.isPresent()) {
            Energia energiaFound = energia.get();

            energiaFound.setValorFatura(energiaRequestDto.valorFatura());
            energiaFound.setConsumoKwh(energiaRequestDto.consumoKwh());

            energiaRepository.save(energiaFound);

            return EnergiaResponseDto.from(energiaFound);
        }


        var entity = new Energia(usuario, energiaRequestDto.valorFatura(), energiaRequestDto.consumoKwh());
        var entitySaved = energiaRepository.save(entity);

        return EnergiaResponseDto.from(entitySaved);
    }

    @Transactional(readOnly = true)
    public EnergiaResponseDto getEnergia(Usuario usuario) {
        var energia = energiaRepository.findByUsuario(usuario).orElseThrow(
                () -> new ResourceNotFoundException("A fatura de energia ainda não foi cadastrada")
        );

        return EnergiaResponseDto.from(energia);
    }
}
