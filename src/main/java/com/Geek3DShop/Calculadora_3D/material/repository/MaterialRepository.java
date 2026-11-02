package com.Geek3DShop.Calculadora_3D.material.repository;

import com.Geek3DShop.Calculadora_3D.material.entity.Material;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    Optional<Material> findByUuidAndActiveTrueAndDeletedFalse(UUID uuid);

    Optional<Material> findByUuidAndUsuarioAndActiveTrueAndDeletedFalse(UUID uuid, Usuario usuario);

    Optional<Material> findByUsuarioAndNomeIgnoreCase(Usuario usuario, String nome);

    Optional<Material> findByNomeIgnoreCaseAndUsuarioAndUuidNot(String nome, Usuario usuario, UUID uuid);

    List<Material> findByUsuarioAndActiveTrueAndDeletedFalseOrderByNomeAsc(Usuario usuario);
}
