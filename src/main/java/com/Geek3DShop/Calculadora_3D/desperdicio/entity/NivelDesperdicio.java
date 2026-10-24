package com.Geek3DShop.Calculadora_3D.desperdicio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NivelDesperdicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @UuidGenerator
    @Column(updatable = false)
    private UUID uuid;

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    private boolean active = true;

    private boolean deleted = false;

    private String nome;

    private BigDecimal percentual;

    public NivelDesperdicio(String nome, BigDecimal percentual) {
        this.nome = nome;
        this.percentual = percentual;
    }

    public BigDecimal calcularFatorDesperdicio() {
        BigDecimal divisor = new BigDecimal("100");

        return BigDecimal.ONE.add(percentual.divide(divisor, 4, RoundingMode.HALF_UP));
    }
}
