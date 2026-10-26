package com.Geek3DShop.Calculadora_3D.energia.repository;

import com.Geek3DShop.Calculadora_3D.energia.entity.Energia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnergiaRepository extends JpaRepository<Energia, Integer> {
    Optional<Energia> findFirstByOrderByIdAsc();
}
