package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;
import lombok.Setter;

import java.time.Month;

public class DtVentasCanceladas {
    @Getter
    private String nombre;

    @Getter
    private Month mes;

    @Getter@Setter
    private int cantidad_canceladas;

    public DtVentasCanceladas() {
    }

    public DtVentasCanceladas(String nombre, Month mes, int cantidad_canceladas) {
        this.nombre = nombre;
        this.mes = mes;
        this.cantidad_canceladas = cantidad_canceladas;
    }
}
