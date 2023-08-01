package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

@Getter
public class DtProductoMod {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;

    public DtProductoMod() {
    }

    public DtProductoMod(Long id, String nombre, String descripcion,String imagen, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.imagen=imagen;
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
