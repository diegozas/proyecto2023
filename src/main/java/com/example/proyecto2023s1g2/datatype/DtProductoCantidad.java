package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

@Getter
public class DtProductoCantidad {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen;
    private int cantidad;


    public DtProductoCantidad(Long id, String nombre, String descripcion, Double precio,String imagen, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.cantidad = cantidad;
    }
}
