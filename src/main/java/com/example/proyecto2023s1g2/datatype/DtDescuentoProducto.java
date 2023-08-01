package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.enumeradores.TipoPromocion;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DtDescuentoProducto extends DtPromocion{
    private int porcentaje;
    private List<DtProducto> productos=new ArrayList<>();
    private TipoPromocion tipoPromocion;

    DtDescuentoProducto(){}

    public DtDescuentoProducto(Long id,String titulo, String descripcion, LocalDateTime fecha_I, LocalDateTime fecha_F,int porcentaje, List<DtProducto> productos) {
        super(id,titulo, descripcion, fecha_I, fecha_F);
        this.porcentaje = porcentaje;
        this.productos = productos;
        this.tipoPromocion=TipoPromocion.DESCUENTOPRODUCTO;
    }
}
