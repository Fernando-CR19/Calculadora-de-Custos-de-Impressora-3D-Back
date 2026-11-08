package com.Geek3DShop.Calculadora_3D.auth.security;

import com.Geek3DShop.Calculadora_3D.usuario.entity.Papel;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        this.tokenService = new TokenService("TokenTeste");
    }

    @Test
    void deveDevolverOUuidDoUsuarioAoValidarOTokenGerado() {
        UUID uuidUsuario = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .papel(Papel.USUARIO)
                .build();
        usuario.setUuid(uuidUsuario);

        String tokenGerado = tokenService.generateToken(usuario);

        Optional<String> uuidToken = tokenService.validateToken(tokenGerado);

        assertThat(tokenGerado).isNotEmpty();
        assertThat(uuidToken).hasValue(uuidUsuario.toString());

    }
}