package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;
import lombok.Setter;

import java.time.Month;

public class DtRecaudacionMensual {
    @Getter
    private String nombre;

    @Getter
    private Month mes;

    @Getter@Setter
    private double recaudado;

    public DtRecaudacionMensual() {
    }

    public DtRecaudacionMensual(String nombre, Month mes, double recaudado) {
        this.nombre = nombre;
        this.mes = mes;
        this.recaudado = recaudado;
    }
}
