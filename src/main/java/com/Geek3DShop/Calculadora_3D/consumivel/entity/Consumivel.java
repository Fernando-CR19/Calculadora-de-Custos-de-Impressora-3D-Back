package com.Geek3DShop.Calculadora_3D.consumivel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Consumivel {

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

    private BigDecimal precoKit;

    private Integer quantidadeKit;

    public Consumivel(String nome, BigDecimal precoKit, Integer quantidadeKit) {
        this.nome = nome;
        this.precoKit = precoKit;
        this.quantidadeKit = quantidadeKit;
    };
}
