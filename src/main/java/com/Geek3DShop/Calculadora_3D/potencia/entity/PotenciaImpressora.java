package com.Geek3DShop.Calculadora_3D.potencia.entity;

import com.Geek3DShop.Calculadora_3D.impressora.entity.Impressora;
import com.Geek3DShop.Calculadora_3D.material.entity.Material;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "impressora_material")
@Getter
@Setter
@NoArgsConstructor
public class PotenciaImpressora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "impressora_id", nullable = false)
    private Impressora impressora;

    @Column(name = "potencia_media_w")
    private BigDecimal potenciaMediaW;

    public PotenciaImpressora(Material material, Impressora impressora, BigDecimal potenciaMediaW) {
        this.material = material;
        this.impressora = impressora;
        this.potenciaMediaW = potenciaMediaW;
    }
}
