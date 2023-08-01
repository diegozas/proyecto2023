package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.enumeradores.TipoPromocion;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DtDescuentoCompraFinal extends DtPromocion{
    private Long id;
    private int descuento;
    private TipoPromocion tipoPromocion;

    DtDescuentoCompraFinal(){}

    public DtDescuentoCompraFinal(Long id,String titulo, String descripcion, LocalDateTime fecha_I, LocalDateTime fecha_F,int descuento) {
        super(id,titulo, descripcion, fecha_I, fecha_F);
        this.id = id;
        this.descuento = descuento;
        this.tipoPromocion=TipoPromocion.DESCUENTOCOMPRAFINAL;
    }
}
