package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DtCrearDescuentoProducto extends DtCrearPromocion{
    private List<Long> productos;
    private int descuento;
    private Long sucursal_id;


    public DtCrearDescuentoProducto(){}

    public DtCrearDescuentoProducto(String titulo, String descripcion, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, List<Long> productos, int descuento, Long sucursal_id) {
        super(titulo, descripcion, fecha_inicio, fecha_fin);
        this.productos = productos;
        this.descuento = descuento;
        this.sucursal_id = sucursal_id;
    }
}
