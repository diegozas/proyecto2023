package com.example.proyecto2023s1g2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="reclamo")
public class Reclamo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "reclamo_id")
    @Getter @Setter
    private Long id;

    @Column(name = "titulo")
    @Getter@Setter
    private String titulo;

    @Column(name = "descripcion")
    @Getter@Setter
    private String descripcion;
    /*
    @Column(name = "id_comprador")
    @Getter@Setter
    private Long id_comprador;
    */
    @Column(name = "id_venta")
    @Getter@Setter
    private Long id_venta;

    @Column(name = "resolucion")
    @Getter@Setter
    private String resolucion;

    @Column(name = "solucionado")
    @Getter@Setter
    private boolean solucionado;

    public Reclamo() {
    }

    public Reclamo(String titulo, String descripcion, Long id_venta, String resolucion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.id_venta = id_venta;
        this.resolucion = resolucion;
        this.solucionado = false;
    }
}
