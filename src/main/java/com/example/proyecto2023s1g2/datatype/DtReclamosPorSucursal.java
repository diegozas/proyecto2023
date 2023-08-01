package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

public class DtReclamosPorSucursal {

    @Getter
    private String nombre;

    @Getter
    private int cantidad_reclamos;

    public DtReclamosPorSucursal() {
    }

    public DtReclamosPorSucursal(String nombre, int cantidad_reclamos) {
        this.nombre = nombre;
        this.cantidad_reclamos = cantidad_reclamos;
    }
}
