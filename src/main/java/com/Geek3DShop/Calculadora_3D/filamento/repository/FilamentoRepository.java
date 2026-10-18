package com.Geek3DShop.Calculadora_3D.filamento.repository;

import com.Geek3DShop.Calculadora_3D.filamento.entity.Filamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilamentoRepository extends JpaRepository<Filamento, Integer> {
}
