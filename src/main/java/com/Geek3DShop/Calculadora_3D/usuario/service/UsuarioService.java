package com.Geek3DShop.Calculadora_3D.usuario.service;

import com.Geek3DShop.Calculadora_3D.shared.exception.ResourceAlreadyExistsException;
import com.Geek3DShop.Calculadora_3D.usuario.dto.CreateUsuarioRequestDto;
import com.Geek3DShop.Calculadora_3D.usuario.dto.UsuarioResponseDto;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Papel;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import com.Geek3DShop.Calculadora_3D.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponseDto createUsuario(CreateUsuarioRequestDto createUsuarioRequestDto, Papel papel) {
        if (usuarioRepository.existsByNomeUsuarioIgnoreCase(createUsuarioRequestDto.nomeUsuario())) {
            throw new ResourceAlreadyExistsException("O nome de usuário: " + createUsuarioRequestDto.nomeUsuario() + " já está cadastrado, tente outro nome");
        }

        String senhaHash = passwordEncoder.encode(createUsuarioRequestDto.senha());

        var entity = Usuario.builder()
                .nome(createUsuarioRequestDto.nome())
                .senha(senhaHash)
                .nomeUsuario(createUsuarioRequestDto.nomeUsuario())
                .papel(papel)
                .build();

        var entitySaved = usuarioRepository.save(entity);

        return UsuarioResponseDto.from(entitySaved);
    }
}
