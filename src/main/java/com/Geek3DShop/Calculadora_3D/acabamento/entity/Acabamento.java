package com.Geek3DShop.Calculadora_3D.acabamento.entity;

import com.Geek3DShop.Calculadora_3D.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Acabamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private BigDecimal valorHora;

    public Acabamento(Usuario usuario, BigDecimal valorHora) {
        this.usuario = usuario;
        this.valorHora = valorHora;
    }

    public BigDecimal calcularValorPorMinuto() {
        BigDecimal divisor = BigDecimal.valueOf(60);

        return valorHora.divide(divisor, 4, RoundingMode.HALF_UP);
    }
}
