package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.enumeradores.Departamentos;
import lombok.Getter;

@Getter
public class DtAltaSucursal {
     private String nombre;
     private DtDireccion domicilio;
     private String telefono;
     private Departamentos departamento;
    public DtAltaSucursal(){}
    public DtAltaSucursal(String nombre, DtDireccion domicilio, String telefono, Departamentos departamento) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.departamento = departamento;
    }
}
