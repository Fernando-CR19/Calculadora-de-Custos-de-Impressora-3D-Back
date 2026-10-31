package com.Geek3DShop.Calculadora_3D.auth.controller;

import com.Geek3DShop.Calculadora_3D.auth.dto.LoginRequestDto;
import com.Geek3DShop.Calculadora_3D.auth.dto.LoginResponseDto;
import com.Geek3DShop.Calculadora_3D.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}
