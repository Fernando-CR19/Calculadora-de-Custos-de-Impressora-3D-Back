package com.Geek3DShop.Calculadora_3D.auth.security;

import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import com.Geek3DShop.Calculadora_3D.usuario.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    private static final String PREFIXO = "Bearer ";

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoveryToken(request);

        if (token != null) {
            Optional<String> subject = tokenService.validateToken(token);

            if (subject.isPresent()) {
                UUID uuid = UUID.fromString(subject.get());
                Optional<Usuario> usuario = usuarioRepository.findByUuidAndActiveTrueAndDeletedFalse(uuid);

                if (usuario.isPresent()) {
                    Usuario usuarioFound = usuario.get();

                    GrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + usuarioFound.getPapel().name());
                    List<GrantedAuthority> authorities = Collections.singletonList(role);

                    var authentication = new UsernamePasswordAuthenticationToken(usuarioFound, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(PREFIXO)) {
            return null;
        }

        return authHeader.substring(PREFIXO.length());
    }
}
