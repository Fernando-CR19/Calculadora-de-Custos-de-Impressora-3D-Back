package com.Geek3DShop.Calculadora_3D.impressora.entity;

import jakarta.persistence.*;
import lombok.Builder;
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
public class Impressora {

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

    private BigDecimal custoAquisicao;

    private Integer vidaUtilHoras;

    @Column(name = "potencia_media_ams_w")
    private BigDecimal potenciaMediaAmsW;

    @Builder
    public Impressora(String nome, BigDecimal custoAquisicao, Integer vidaUtilHoras, BigDecimal potenciaMediaAmsW) {
        this.nome = nome;
        this.custoAquisicao = custoAquisicao;
        this.vidaUtilHoras = vidaUtilHoras;
        this.potenciaMediaAmsW = potenciaMediaAmsW;
    }

    public BigDecimal calcularDepreciacaoPorHora() {
        BigDecimal vidaUtilHorasDecimal = new BigDecimal(this.vidaUtilHoras);

        return custoAquisicao.divide(vidaUtilHorasDecimal, 4, RoundingMode.HALF_UP);
    }
}
