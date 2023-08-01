package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DtCrearDescuentoCompraFinal extends DtCrearPromocion {
    private int descuento;
    private Long sucursal_id;

   public DtCrearDescuentoCompraFinal(){}

    public DtCrearDescuentoCompraFinal(String titulo, String descripcion, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, int descuento, Long sucursal_id) {
        super(titulo, descripcion, fecha_inicio, fecha_fin);
        this.descuento = descuento;
        this.sucursal_id = sucursal_id;
    }
}
