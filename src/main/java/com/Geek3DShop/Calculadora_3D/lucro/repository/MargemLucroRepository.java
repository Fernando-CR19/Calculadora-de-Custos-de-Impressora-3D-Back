package com.Geek3DShop.Calculadora_3D.lucro.repository;

import com.Geek3DShop.Calculadora_3D.lucro.entity.MargemLucro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MargemLucroRepository extends JpaRepository<MargemLucro, Integer> {
}
