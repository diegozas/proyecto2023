package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DtProducto {
    @Getter
    private Long id;
    @Getter
    private String nombre;
    @Getter
    private String descripcion;
    @Getter
    private Double precio;
    @Getter
    private int stock;
    @Getter
    private String imagen;
    @Getter
    private Long categoria;

    @Getter
    private List<DtCategoria> categorias=new ArrayList<>();

    public DtProducto() {}

    public DtProducto(Long id, String nombre, String descripcion, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public DtProducto(String nombre, String descripcion, Double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public DtProducto(Long id, String nombre, String descripcion, Double precio, int stock, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
    }

    public DtProducto(Long id, String nombre, String descripcion, Double precio, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
    }
}