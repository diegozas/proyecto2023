package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DtVentaMod {

    @Getter
    private Long id_sucursal;

    @Getter
    private Long lugarRetiro;

    @Getter
    private Long id_comprador;

    @Getter
    private List<Long> productos = new ArrayList<>();

    public DtVentaMod(){}





    public DtVentaMod(Long id_sucursal, Long lugarRetiro, Long id_comprador, List<Long> productos) {
        this.id_sucursal = id_sucursal;
        this.lugarRetiro = lugarRetiro;
        this.id_comprador = id_comprador;
        this.productos = productos;
    }
}
