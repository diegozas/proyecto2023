package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class DtPromocion {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha_I;
    private LocalDateTime fecha_F;


    public DtPromocion() {}


    public DtPromocion(Long id,String titulo, String descripcion, LocalDateTime fecha_I, LocalDateTime fecha_F) {
        this.id=id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_I = fecha_I;
        this.fecha_F = fecha_F;
    }
}
