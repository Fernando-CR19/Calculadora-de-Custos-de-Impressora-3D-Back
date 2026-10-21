package com.Geek3DShop.Calculadora_3D.consumivel.repository;

import com.Geek3DShop.Calculadora_3D.consumivel.entity.Consumivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsumivelRepository extends JpaRepository<Consumivel, Integer> {

    Optional<Consumivel> findByNomeIgnoreCase(String nome);

    List<Consumivel> findByActiveTrueAndDeletedFalseOrderByNomeAsc();

    Optional<Consumivel> findByUuidAndActiveTrueAndDeletedFalse(UUID uuid);

    Optional<Consumivel> findByNomeIgnoreCaseAndUuidNot(String nome, UUID uuid);

}
