package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.domain.*;
import com.example.proyecto2023s1g2.enumeradores.Departamentos;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class DtSucursal {

    @Getter
    private Long id;
    @Getter
    private DtDireccion direccion;
    @Getter
    private String telefono;
    @Getter
    private String nombre;
    @Getter
    private Departamentos departamento;
    /*
    @Getter
    private List<DtCategoria> categorias =new ArrayList<DtCategoria>();

    @Getter
    private List<DtSucursalProducto> productos = new ArrayList<>();

    @Getter
    private List<DtUsuarioSucursal> usuariosSucursal = new ArrayList<DtUsuarioSucursal>();

    @Getter
    private List<DtVenta> ventas = new ArrayList<DtVenta>();
*/
    public DtSucursal(){}

    public DtSucursal(Long id, String nombre,Departamentos departamento,String telefono, DtDireccion dtDireccion) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.telefono = telefono;
        this.direccion = dtDireccion;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", direccion=" + direccion +
                ", telefono='" + telefono + '\'' +
                ", nombre='" + nombre + '\'' +
                ", departamento=" + departamento +
                '}';
    }
}
