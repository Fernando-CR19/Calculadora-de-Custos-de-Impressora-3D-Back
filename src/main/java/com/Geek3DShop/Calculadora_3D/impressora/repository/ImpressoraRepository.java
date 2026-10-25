package com.Geek3DShop.Calculadora_3D.impressora.repository;

import com.Geek3DShop.Calculadora_3D.impressora.entity.Impressora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImpressoraRepository extends JpaRepository<Impressora, Integer> {

    Optional<Impressora> findByNomeIgnoreCase(String nome);

}
