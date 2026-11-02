package com.Geek3DShop.Calculadora_3D.energia.repository;

import com.Geek3DShop.Calculadora_3D.energia.entity.Energia;
import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnergiaRepository extends JpaRepository<Energia, Integer> {

    Optional<Energia> findFirstByOrderByIdAsc();

    Optional<Energia> findByUsuario(Usuario usuario);

}
