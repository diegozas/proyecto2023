package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DtSimularVenta {

    @Getter
    private List<DtProductoDescuento> productos = new ArrayList<>();

    @Getter
    private double total;

    @Getter
    private double total_con_descuento_final;

    public DtSimularVenta(){}

    public DtSimularVenta(List<DtProductoDescuento> productos, double total, double total_con_descuento_final) {
        this.productos = productos;
        this.total = total;
        this.total_con_descuento_final = total_con_descuento_final;
    }
}
