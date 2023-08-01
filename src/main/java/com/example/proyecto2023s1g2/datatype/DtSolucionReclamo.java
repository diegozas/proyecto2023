package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

@Getter
public class DtSolucionReclamo {
    private Long reclamo_id;
    private String solucion;



    public DtSolucionReclamo(){}

    public DtSolucionReclamo(Long reclamo_id, String solucion) {
        this.reclamo_id = reclamo_id;
        this.solucion = solucion;
    }
}
