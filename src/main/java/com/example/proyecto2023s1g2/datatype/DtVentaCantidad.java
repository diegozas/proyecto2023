package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.enumeradores.EstadoVenta;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DtVentaCantidad {
    private Long id;
    private DtSucursal sucursal;
    private DtDireccion lugarRetiro;
    private DtComprador comprador;
    private List<DtProductoCantidad> productos = new ArrayList<DtProductoCantidad>();
    private double total;
    private EstadoVenta estado;
    private LocalDateTime fecha;


    public DtVentaCantidad(Long id, DtSucursal sucursal, DtDireccion lugarRetiro, LocalDateTime fecha, EstadoVenta estado, double total) {
        this.id = id;
        this.sucursal = sucursal;
        this.lugarRetiro = lugarRetiro;
        this.total = total;
        this.estado = estado;
        this.fecha = fecha;
    }

    public DtVentaCantidad(Long id, DtComprador comprador,DtDireccion lugarRetiro,LocalDateTime fecha,EstadoVenta estado,double total,List<DtProductoCantidad> productos) {
        this.id = id;
        this.comprador = comprador;
        this.lugarRetiro = lugarRetiro;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.productos = productos;

    }

}
