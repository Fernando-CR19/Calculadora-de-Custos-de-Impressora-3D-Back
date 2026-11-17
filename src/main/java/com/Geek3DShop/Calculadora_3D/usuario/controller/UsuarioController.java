package com.Geek3DShop.Calculadora_3D.usuario.controller;

import com.Geek3DShop.Calculadora_3D.usuario.dto.UsuarioResponseDto;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping("/perfil")
    public UsuarioResponseDto getAuthenticatedUser(@AuthenticationPrincipal Usuario usuario) {
        return UsuarioResponseDto.from(usuario);
    }
}
