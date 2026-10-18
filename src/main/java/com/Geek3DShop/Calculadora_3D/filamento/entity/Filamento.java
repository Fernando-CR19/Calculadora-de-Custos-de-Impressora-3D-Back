package com.Geek3DShop.Calculadora_3D.filamento.entity;

import com.Geek3DShop.Calculadora_3D.material.entity.Material;
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
public class Filamento {

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

    private String cor;

    private BigDecimal precoKg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    public Filamento(String cor, BigDecimal precoKg, Material material) {
        this.cor = cor;
        this.precoKg = precoKg;
        this.material = material;
    }
}
