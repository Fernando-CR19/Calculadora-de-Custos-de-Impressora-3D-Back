package com.Geek3DShop.Calculadora_3D.auth.controller;

import com.Geek3DShop.Calculadora_3D.auth.dto.LoginRequestDto;
import com.Geek3DShop.Calculadora_3D.auth.dto.LoginResponseDto;
import com.Geek3DShop.Calculadora_3D.auth.service.AuthService;
import com.Geek3DShop.Calculadora_3D.usuario.dto.CreateUsuarioRequestDto;
import com.Geek3DShop.Calculadora_3D.usuario.dto.UsuarioResponseDto;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Papel;
import com.Geek3DShop.Calculadora_3D.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UsuarioService usuarioService;

    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDto cadastrar(@Valid @RequestBody CreateUsuarioRequestDto createUsuarioRequestDto) {
        return usuarioService.createUsuario(createUsuarioRequestDto, Papel.USUARIO);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}
