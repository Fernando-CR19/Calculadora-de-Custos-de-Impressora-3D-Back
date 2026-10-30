package com.Geek3DShop.Calculadora_3D.usuario.repository;

import com.Geek3DShop.Calculadora_3D.usuario.entity.Papel;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNomeUsuarioIgnoreCaseAndActiveTrueAndDeletedFalse(String nomeUsuario);

    boolean existsByNomeUsuarioIgnoreCase(String nomeUsuario);

    boolean existsByPapel(Papel papel);

}
