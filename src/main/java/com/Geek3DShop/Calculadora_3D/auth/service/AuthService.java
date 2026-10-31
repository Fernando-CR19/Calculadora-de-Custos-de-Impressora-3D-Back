package com.Geek3DShop.Calculadora_3D.auth.service;

import com.Geek3DShop.Calculadora_3D.auth.dto.LoginRequestDto;
import com.Geek3DShop.Calculadora_3D.auth.dto.LoginResponseDto;
import com.Geek3DShop.Calculadora_3D.auth.security.TokenService;
import com.Geek3DShop.Calculadora_3D.shared.exception.InvalidCredentialsException;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import com.Geek3DShop.Calculadora_3D.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Usuario usuario = usuarioRepository.findByNomeUsuarioIgnoreCaseAndActiveTrueAndDeletedFalse(loginRequestDto.nomeUsuario()).orElseThrow(
                () -> new InvalidCredentialsException("As credenciais informadas estão erradas")
        );

        String senhaHash = usuario.getSenha();

        boolean matches = passwordEncoder.matches(loginRequestDto.senha(), senhaHash);

        if (!matches) {
            throw new InvalidCredentialsException("As credenciais informadas estão erradas");
        }

        String token = tokenService.generateToken(usuario);

        return new LoginResponseDto(token);

    }
}
