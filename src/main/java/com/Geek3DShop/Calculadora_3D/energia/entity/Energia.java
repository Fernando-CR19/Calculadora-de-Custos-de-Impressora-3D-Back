package com.Geek3DShop.Calculadora_3D.energia.entity;

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
public class Energia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private BigDecimal valorFatura;

    private Integer consumoKwh;

    public Energia(Usuario usuario, BigDecimal valorFatura, Integer consumoKwh) {
        this.usuario = usuario;
        this.valorFatura = valorFatura;
        this.consumoKwh = consumoKwh;
    }

    public BigDecimal calcularPrecoKwh() {
        BigDecimal consumoKwhDecimal = BigDecimal.valueOf(consumoKwh);

        return valorFatura.divide(consumoKwhDecimal, 4, RoundingMode.HALF_UP);
    }
}
