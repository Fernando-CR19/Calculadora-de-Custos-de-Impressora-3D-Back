package com.Geek3DShop.Calculadora_3D.filamento.repository;

import com.Geek3DShop.Calculadora_3D.filamento.entity.Filamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilamentoRepository extends JpaRepository<Filamento, Integer> {

    Optional<Filamento> findByMaterialIdAndCorIgnoreCase(Integer id, String cor);

    List<Filamento> findByActiveTrueAndDeletedFalseOrderByMaterialNomeAscCorAsc();

    Optional<Filamento> findByUuidAndActiveTrueAndDeletedFalse(UUID uuid);

    Optional<Filamento> findByCorIgnoreCaseAndMaterialUuidAndUuidNot(String cor, UUID materialUuid, UUID uuid);
}
