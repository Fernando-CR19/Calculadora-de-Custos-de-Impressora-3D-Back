package com.Geek3DShop.Calculadora_3D.acabamento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private BigDecimal valorHora;

    public Acabamento(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public BigDecimal calcularValorPorMinuto() {
        BigDecimal divisor = BigDecimal.valueOf(60);

        return valorHora.divide(divisor, 4, RoundingMode.HALF_UP);
    }
}
