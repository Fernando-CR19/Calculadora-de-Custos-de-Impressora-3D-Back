package com.Geek3DShop.Calculadora_3D.desperdicio.repository;

import com.Geek3DShop.Calculadora_3D.desperdicio.entity.NivelDesperdicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NivelDesperdicioRepository extends JpaRepository<NivelDesperdicio, Integer> {

    boolean existsByNomeIgnoreCaseAndDeletedFalse(String nome);

    boolean existsByPercentualAndDeletedFalse(BigDecimal percentual);

    List<NivelDesperdicio> findByDeletedFalseAndActiveTrueOrderByPercentualAsc();

    Optional<NivelDesperdicio> findByUuidAndDeletedFalseAndActiveTrue(UUID uuid);

    boolean existsByNomeIgnoreCaseAndDeletedFalseAndUuidNot(String nomem, UUID uuid);

    boolean existsByPercentualAndDeletedFalseAndUuidNot(BigDecimal percentual, UUID uuid);

}
