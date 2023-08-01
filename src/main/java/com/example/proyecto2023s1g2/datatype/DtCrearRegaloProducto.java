package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DtCrearRegaloProducto extends DtCrearPromocion{
    private Long producto_id;
    private int stock;
    private Long sucursal_id;

    public DtCrearRegaloProducto(){}

    public DtCrearRegaloProducto(String titulo, String descripcion, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, Long producto_id, int stock, Long sucursal_id) {
        super(titulo, descripcion, fecha_inicio, fecha_fin);
        this.producto_id = producto_id;
        this.stock = stock;
        this.sucursal_id = sucursal_id;
    }
}
