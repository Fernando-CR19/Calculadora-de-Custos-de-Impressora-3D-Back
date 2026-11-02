package com.Geek3DShop.Calculadora_3D.impressora.repository;

import com.Geek3DShop.Calculadora_3D.impressora.entity.Impressora;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImpressoraRepository extends JpaRepository<Impressora, Integer> {

    Optional<Impressora> findByUsuarioAndNomeIgnoreCase(Usuario usuario, String nome);

    List<Impressora> findByUsuarioAndActiveTrueAndDeletedFalseOrderByNomeAsc(Usuario usuario);

    Optional<Impressora> findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(Usuario usuario, UUID uuid);

    Optional<Impressora> findByUsuarioAndNomeIgnoreCaseAndUuidNot(Usuario usuario, String nome, UUID uuid);

}
