package com.example.proyecto2023s1g2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="descuentocomprafinal")
@PrimaryKeyJoinColumn(referencedColumnName = "promocion_id")
public class DescuentoCompraFinal extends Promocion {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    @Getter@Setter
    private Long id;

    @Column(name = "descuento")
    @Getter@Setter
    private int descuento;

    public DescuentoCompraFinal() {
    }

    public DescuentoCompraFinal(String titulo, String descripcion, LocalDateTime fecha_I, LocalDateTime fecha_F, int descuento) {
        super(titulo, descripcion, fecha_I, fecha_F);
        this.descuento = descuento;
    }




}
