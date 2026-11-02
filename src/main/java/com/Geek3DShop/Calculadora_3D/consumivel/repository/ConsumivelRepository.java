package com.Geek3DShop.Calculadora_3D.consumivel.repository;

import com.Geek3DShop.Calculadora_3D.consumivel.entity.Consumivel;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsumivelRepository extends JpaRepository<Consumivel, Integer> {

    Optional<Consumivel> findByUsuarioAndNomeIgnoreCase(Usuario usuario, String nome);

    List<Consumivel> findByUsuarioAndActiveTrueAndDeletedFalseOrderByNomeAsc(Usuario usuario);

    Optional<Consumivel> findByUuidAndActiveTrueAndDeletedFalse(UUID uuid);

    Optional<Consumivel> findByUsuarioAndUuidAndActiveTrueAndDeletedFalse(Usuario usuario, UUID uuid);

    Optional<Consumivel> findByUsuarioAndNomeIgnoreCaseAndUuidNot(Usuario usuario, String nome, UUID uuid);

}
