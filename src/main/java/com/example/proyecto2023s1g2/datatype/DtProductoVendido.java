package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;
import lombok.Setter;

public class DtProductoVendido {

    @Getter
    private DtProducto producto;

    @Getter@Setter
    private int cantidad;

    public DtProductoVendido() {
    }

    public DtProductoVendido(DtProducto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
}
