package com.Geek3DShop.Calculadora_3D.usuario.init;

import com.Geek3DShop.Calculadora_3D.usuario.dto.CreateUsuarioRequestDto;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Papel;
import com.Geek3DShop.Calculadora_3D.usuario.repository.UsuarioRepository;
import com.Geek3DShop.Calculadora_3D.usuario.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdminInitializer implements ApplicationRunner {
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final String adminNome;
    private final String adminNomeUsuario;
    private final String adminSenha;

    public AdminInitializer(
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            @Value("${ADMIN_NOME:}") String adminNome,
            @Value("${ADMIN_NOME_USUARIO:}") String adminNomeUsuario,
            @Value("${ADMIN_SENHA:}") String adminSenha
    ) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.adminNome = adminNome;
        this.adminNomeUsuario = adminNomeUsuario;
        this.adminSenha = adminSenha;
    }

    @Override
    public void run(@NonNull ApplicationArguments arguments) {
        if (usuarioRepository.existsByPapel(Papel.ADMIN)) {
            log.info("Admin já cadastrado");
        } else if (adminNome.isBlank() || adminNomeUsuario.isBlank() || adminSenha.isBlank()) {
            log.warn("Existem variáveis de ambiente em branco");
            log.warn("Preencha os campos: ADMIN_NOME, ADMIN_NOME_USUARIO e ADMIN_SENHA");
        } else {
            CreateUsuarioRequestDto createAdminDto = new CreateUsuarioRequestDto(adminNome, adminNomeUsuario, adminSenha);
            usuarioService.createUsuario(createAdminDto, Papel.ADMIN);
            log.info("Admin cadastrado com sucesso");
        }
    }
}
