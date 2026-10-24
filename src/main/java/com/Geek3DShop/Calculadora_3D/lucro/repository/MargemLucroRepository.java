package com.Geek3DShop.Calculadora_3D.lucro.repository;

import com.Geek3DShop.Calculadora_3D.lucro.entity.MargemLucro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MargemLucroRepository extends JpaRepository<MargemLucro, Integer> {

    boolean existsByNomeIgnoreCaseAndDeletedFalse(String nome);

    boolean existsByPercentualAndDeletedFalse(BigDecimal percentual);

    List<MargemLucro> findByDeletedFalseAndActiveTrueOrderByPercentualAsc();

    Optional<MargemLucro> findByUuidAndActiveTrueAndDeletedFalse(UUID uuid);

    boolean existsByNomeIgnoreCaseAndDeletedFalseAndUuidNot(String nome, UUID uuid);

    boolean existsByPercentualAndDeletedFalseAndUuidNot(BigDecimal percentual, UUID uuid);

}
