package com.Geek3DShop.Calculadora_3D.auth.security;

import com.Geek3DShop.Calculadora_3D.usuario.entity.Papel;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

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

        assertThat(uuidToken).hasValue(uuidUsuario.toString());
    }

    @Test
    void deveIncluirOPapelOEmissorEAValidadeNoToken() {
        UUID uuidUsuario = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .papel(Papel.GUEST)
                .build();
        usuario.setUuid(uuidUsuario);

        String tokenGerado = tokenService.generateToken(usuario);

        DecodedJWT jwtDecodificado = JWT.decode(tokenGerado);

        Instant expiracaoEsperada = Instant.now().plus(2, ChronoUnit.HOURS);

        assertThat(jwtDecodificado.getClaim("papel").asString()).isEqualTo("GUEST");
        assertThat(jwtDecodificado.getIssuer()).isEqualTo("Calculadora-3D");
        assertThat(jwtDecodificado.getExpiresAtAsInstant()).isCloseTo(expiracaoEsperada, within(2, ChronoUnit.SECONDS));
    }

    @Test
    void deveRejeitarTokenGuestComPayloadAdminReaproveitado() {
        UUID uuidGuest = UUID.randomUUID();
        Usuario usuarioGuest = Usuario.builder()
                .papel(Papel.GUEST)
                .build();
        usuarioGuest.setUuid(uuidGuest);

        String tokenGuest = tokenService.generateToken(usuarioGuest);

        int primeiroPontoGuest = tokenGuest.indexOf(".");
        int segundoPontoGuest = tokenGuest.indexOf(".", primeiroPontoGuest + 1);

        String headerGuest = tokenGuest.substring(0, primeiroPontoGuest);
        String assinaturaGuest = tokenGuest.substring(segundoPontoGuest + 1);

        UUID uuidAdmin = UUID.randomUUID();
        Usuario usuarioAdmin = Usuario.builder()
                .papel(Papel.ADMIN)
                .build();
        usuarioAdmin.setUuid(uuidAdmin);

        String tokenAdmin = tokenService.generateToken(usuarioAdmin);

        int primeiroPontoAdmin = tokenAdmin.indexOf(".");
        int segundoPontoAdmin = tokenAdmin.indexOf(".", primeiroPontoAdmin + 1);

        String payloadAdmin = tokenAdmin.substring(primeiroPontoAdmin + 1, segundoPontoAdmin);

        String tokenAlterado = headerGuest + "." + payloadAdmin + "." + assinaturaGuest;

        Optional<String> resultadoValidacao = tokenService.validateToken(tokenAlterado);

        assertThat(resultadoValidacao).isNotPresent();
    }

    @Test
    void deveRejeitarTokenAssinadoComOutraChave() {
        UUID uuidUsuario = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .papel(Papel.USUARIO)
                .build();
        usuario.setUuid(uuidUsuario);

        String tokenGerado = tokenService.generateToken(usuario);

        TokenService tokenServiceComOutraChave = new TokenService("TokenTesteInvalido");

        Optional<String> resultadoValidacao = tokenServiceComOutraChave.validateToken(tokenGerado);

        assertThat(resultadoValidacao).isNotPresent();
    }

    @Test
    void deveRejeitarUmTextoQueNaoEhUmToken() {
        String textoQualquer = "TeXTo.nAoEUm.toKeNValIDO";

        Optional<String> resultadoValidacao = tokenService.validateToken(textoQualquer);

        assertThat(resultadoValidacao).isNotPresent();
    }
}