package com.Geek3DShop.Calculadora_3D.usuario.init;

import com.Geek3DShop.Calculadora_3D.usuario.entity.Papel;
import com.Geek3DShop.Calculadora_3D.usuario.repository.UsuarioRepository;
import com.Geek3DShop.Calculadora_3D.usuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminInitializerTest {
    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void naoDeveCriarAdminQuandoJaExiste() {
        // PREPARA
        String adminNome = "AdminNomeTeste";
        String adminUsuario = "AdminUsuarioTeste";
        String adminSenha = "AdminSenhaTeste";

        when(usuarioRepository.existsByPapel(Papel.ADMIN)).thenReturn(true);

        AdminInitializer adminInitializer = new AdminInitializer(usuarioService, usuarioRepository, adminNome, adminUsuario, adminSenha);

        // EXECUTA
        adminInitializer.run(null);

        // VERIFICA
        verify(usuarioRepository, times(1)).existsByPapel(Papel.ADMIN);
        verify(usuarioService, never()).createUsuario(any(), any());
    }

    @Test
    void naoDeveCriarAdminQuandoFaltamVariaveis() {
        // PREPARA
        String adminNome = "";
        String adminUsuario = "AdminUsuarioTeste";
        String adminSenha = "AdminSenhaTeste";

        when(usuarioRepository.existsByPapel(Papel.ADMIN)).thenReturn(false);

        AdminInitializer adminInitializer = new AdminInitializer(usuarioService, usuarioRepository, adminNome, adminUsuario, adminSenha);

        // EXECUTA
        adminInitializer.run(null);

        // VERIFICA
        verify(usuarioRepository, times(1)).existsByPapel(Papel.ADMIN);
        verify(usuarioService, never()).createUsuario(any(), any());
    }

    @Test
    void deveCriarAdminCasoNaoExistaEVariaveisEstejamPresentes() {
        // PREPARA
        String adminNome = "AdminNomeTeste";
        String adminUsuario = "AdminUsuarioTeste";
        String adminSenha = "AdminSenhaTeste";

        when(usuarioRepository.existsByPapel(Papel.ADMIN)).thenReturn(false);

        AdminInitializer adminInitializer = new AdminInitializer(usuarioService, usuarioRepository, adminNome, adminUsuario, adminSenha);

        // EXECUTA
        adminInitializer.run(null);

        // VERIFICA
        verify(usuarioRepository, times(1)).existsByPapel(Papel.ADMIN);
        verify(usuarioService, times(1)).createUsuario(any(), eq(Papel.ADMIN));
    }
}