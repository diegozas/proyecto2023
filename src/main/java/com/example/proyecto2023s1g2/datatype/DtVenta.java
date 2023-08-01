package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.domain.Producto;
import com.example.proyecto2023s1g2.enumeradores.EstadoVenta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class DtVenta {
    private static final long serialVersionUID = 1L;

    @Getter
    private Long id;

    @Getter
    private DtSucursal sucursal;

    @Getter
    private DtDireccion lugarRetiro;

    @Getter
    private DtComprador comprador;

    @Getter
    private List<DtProducto> productos = new ArrayList<DtProducto>();

    @Getter
    private double total;

    @Getter
    private EstadoVenta estado;

    @Getter
    private LocalDateTime fecha;

    public DtVenta(){}

    public DtVenta(Long id, DtSucursal sucursal,DtDireccion lugarRetiro, LocalDateTime fecha, EstadoVenta estado, double total, List<DtProducto> productos) {
        this.id = id;
        this.sucursal = sucursal;
        this.lugarRetiro=lugarRetiro;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.productos = productos;
    }

    public DtVenta(Long id, DtComprador comprador, DtDireccion lugarRetiro,LocalDateTime fecha, EstadoVenta estado, double total, List<DtProducto> productos) {
        this.id = id;
        this.comprador = comprador;
        this.lugarRetiro=lugarRetiro;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.productos = productos;
    }

    public DtVenta(Long id, DtComprador comprador, DtSucursal sucursal,DtDireccion lugarRetiro, LocalDateTime fecha, EstadoVenta estado, double total, List<DtProducto> productos) {
        this.id = id;
        this.comprador = comprador;
        this.sucursal = sucursal;
        this.lugarRetiro=lugarRetiro;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.productos = productos;
    }



}
