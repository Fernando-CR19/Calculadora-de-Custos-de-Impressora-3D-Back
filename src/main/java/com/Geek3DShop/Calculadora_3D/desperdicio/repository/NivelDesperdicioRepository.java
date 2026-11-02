package com.Geek3DShop.Calculadora_3D.desperdicio.repository;

import com.Geek3DShop.Calculadora_3D.desperdicio.entity.NivelDesperdicio;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NivelDesperdicioRepository extends JpaRepository<NivelDesperdicio, Integer> {

    boolean existsByUsuarioAndNomeIgnoreCaseAndDeletedFalse(Usuario usuario, String nome);

    boolean existsByUsuarioAndPercentualAndDeletedFalse(Usuario usuario, BigDecimal percentual);

    List<NivelDesperdicio> findByUsuarioAndDeletedFalseAndActiveTrueOrderByPercentualAsc(Usuario usuario);

    Optional<NivelDesperdicio> findByUuidAndDeletedFalseAndActiveTrue(UUID uuid);

    Optional<NivelDesperdicio> findByUsuarioAndUuidAndDeletedFalseAndActiveTrue(Usuario usuario, UUID uuid);

    boolean existsByUsuarioAndNomeIgnoreCaseAndDeletedFalseAndUuidNot(Usuario usuario, String nome, UUID uuid);

    boolean existsByUsuarioAndPercentualAndDeletedFalseAndUuidNot(Usuario usuario, BigDecimal percentual, UUID uuid);

}
