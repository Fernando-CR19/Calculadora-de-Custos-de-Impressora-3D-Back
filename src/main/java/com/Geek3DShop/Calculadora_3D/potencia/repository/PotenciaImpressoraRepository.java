package com.Geek3DShop.Calculadora_3D.potencia.repository;

import com.Geek3DShop.Calculadora_3D.potencia.entity.PotenciaImpressora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PotenciaImpressoraRepository extends JpaRepository<PotenciaImpressora, Integer> {

    boolean existsByImpressoraIdAndMaterialId(Integer impressoraId, Integer materialId);

    List<PotenciaImpressora> findByImpressoraIdAndMaterialActiveTrueAndMaterialDeletedFalseOrderByMaterialNomeAsc(Integer impressoraId);

    Optional<PotenciaImpressora> findByImpressoraUuidAndImpressoraActiveTrueAndImpressoraDeletedFalseAndMaterialUuidAndMaterialActiveTrueAndMaterialDeletedFalse (UUID uuidImpressora, UUID uuidMaterial);

}
