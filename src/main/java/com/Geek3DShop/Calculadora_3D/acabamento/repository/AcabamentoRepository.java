package com.Geek3DShop.Calculadora_3D.acabamento.repository;

import com.Geek3DShop.Calculadora_3D.acabamento.entity.Acabamento;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcabamentoRepository extends JpaRepository<Acabamento, Integer> {

    Optional<Acabamento> findByUsuario(Usuario usuario);

}
