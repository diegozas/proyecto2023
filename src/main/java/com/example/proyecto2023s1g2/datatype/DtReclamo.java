package com.example.proyecto2023s1g2.datatype;


import lombok.Getter;


@Getter
public class DtReclamo {
    private Long id;
    private String titulo;
    private String descripcion;
    private DtVenta dtventa;
    private String resolucion;
    private boolean solucionado;

    public DtReclamo(){}

    public DtReclamo(Long id, String titulo, String descripcion, DtVenta dtventa, String resolucion, boolean solucionado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.dtventa = dtventa;
        this.resolucion = resolucion;
        this.solucionado = solucionado;
    }
}
