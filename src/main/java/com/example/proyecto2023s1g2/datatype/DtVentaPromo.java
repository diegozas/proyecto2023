package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DtVentaPromo {

    @Getter
    private Long id_sucursal;

    @Getter
    private Long id_producto;

    @Getter
    private int cantidad;

    public DtVentaPromo(){}

    public DtVentaPromo(Long id_sucursal, Long id_producto, int cantidad) {
        this.id_sucursal = id_sucursal;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }
}
