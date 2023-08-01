package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.enumeradores.TipoPromocion;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DtRegaloProducto extends DtPromocion{
    private DtProducto producto;
    private TipoPromocion tipoPromocion;
    public DtRegaloProducto() {}

    public DtRegaloProducto(Long id,String titulo, String descripcion, LocalDateTime fecha_I, LocalDateTime fecha_F,DtProducto producto) {
        super(id,titulo, descripcion, fecha_I, fecha_F);
        this.producto = producto;
        this.tipoPromocion=TipoPromocion.REGALOPRODUCTO;
    }
}
