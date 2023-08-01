package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

public class DtComprasPorSucursal {

    @Getter
    private String nombre;

    @Getter
    private int cantidad_compras;

    public DtComprasPorSucursal() {
    }

    public DtComprasPorSucursal(String nombre, int cantidad_compras) {
        this.nombre = nombre;
        this.cantidad_compras = cantidad_compras;
    }
}
