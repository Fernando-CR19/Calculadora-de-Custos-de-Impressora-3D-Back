package com.Geek3DShop.Calculadora_3D.impressora.repository;

import com.Geek3DShop.Calculadora_3D.impressora.entity.Impressora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImpressoraRepository extends JpaRepository<Impressora, Integer> {

    Optional<Impressora> findByNomeIgnoreCase(String nome);

    List<Impressora> findByActiveTrueAndDeletedFalseOrderByNomeAsc();

    Optional<Impressora> findByUuidAndActiveTrueAndDeletedFalse(UUID uuid);

    Optional<Impressora> findByNomeIgnoreCaseAndUuidNot(String nome, UUID uuid);

}
