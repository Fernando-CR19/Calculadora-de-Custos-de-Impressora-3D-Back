package com.Geek3DShop.Calculadora_3D.desperdicio.repository;

import com.Geek3DShop.Calculadora_3D.desperdicio.entity.NivelDesperdicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface NivelDesperdicioRepository extends JpaRepository<NivelDesperdicio, Integer> {

    boolean existsByNomeIgnoreCaseAndDeletedFalse(String nome);

    boolean existsByPercentualAndDeletedFalse(BigDecimal percentual);

}
