package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

public class DtRecaudacionPorSucursal {
    @Getter
    private String nombre;

    @Getter
    private double recaudado;

    public DtRecaudacionPorSucursal() {
    }

    public DtRecaudacionPorSucursal(String nombre, double recaudado) {
        this.nombre = nombre;
        this.recaudado = recaudado;
    }
}
