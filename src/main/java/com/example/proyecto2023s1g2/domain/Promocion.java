package com.example.proyecto2023s1g2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name = "promocion")
@Inheritance(strategy = InheritanceType.JOINED)
public class Promocion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "promocion_id")
    @Getter@Setter
    private Long id;

    @Column(name = "titulo")
    @Getter@Setter
    private String titulo;

    @Column(name = "descripcion")
    @Getter@Setter
    private String descripcion;

    @Column(name = "fecha_i")
    @Getter@Setter
    private LocalDateTime fecha_I;

    @Column(name = "fecha_f")
    @Getter@Setter
    private LocalDateTime fecha_F;

    public Promocion() {
    }

    public Promocion(String titulo, String descripcion, LocalDateTime fecha_I, LocalDateTime fecha_F) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_I = fecha_I;
        this.fecha_F = fecha_F;
    }

}
