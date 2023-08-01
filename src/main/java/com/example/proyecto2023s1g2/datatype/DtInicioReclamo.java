package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

@Getter
public class DtInicioReclamo {
    private String asunto;
    private String comentario;
    private Long comprador_id;
    private Long venta_id;



    DtInicioReclamo(){}

    public DtInicioReclamo(String asunto, String comentario, Long comprador_id, Long venta_id) {
        this.asunto = asunto;
        this.comentario = comentario;
        this.comprador_id = comprador_id;
        this.venta_id=venta_id;
    }
}
