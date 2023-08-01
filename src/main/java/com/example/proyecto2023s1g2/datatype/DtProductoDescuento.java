package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DtProductoDescuento {
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
    private double descuento;
    @Getter
    private Long descuento_id;
    @Getter
    private double precio_con_descuento;

    @Getter
    private List<DtCategoria> categorias=new ArrayList<>();

    public DtProductoDescuento() {}

    public DtProductoDescuento(Long id, String nombre, String descripcion, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public DtProductoDescuento(String nombre, String descripcion, Double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public DtProductoDescuento(Long id, String nombre, String descripcion, Double precio, int stock, String imagen, double descuento, Long descuento_id, double precio_con_descuento) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.descuento = descuento;
        this.descuento_id = descuento_id;
        this.precio_con_descuento = precio_con_descuento;
    }

    public DtProductoDescuento(Long id, String nombre, String descripcion, Double precio, int stock, String imagen, Long descuento_id) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.descuento_id = descuento_id;
    }

    public DtProductoDescuento(Long id, String nombre, String descripcion, Double precio, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    }
}