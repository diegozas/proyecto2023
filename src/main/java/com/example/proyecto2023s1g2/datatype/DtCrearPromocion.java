package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DtCrearPromocion {
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;

    public DtCrearPromocion(){}

    public DtCrearPromocion(String titulo, String descripcion, LocalDateTime fecha_inicio, LocalDateTime fecha_fin) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }
}
