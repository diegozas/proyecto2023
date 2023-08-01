package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.enumeradores.Departamentos;
import lombok.Getter;

@Getter
public class DtModSucursal {
     private Long sucursal_id;
     private String telefono;
     private String nombre;
     private Departamentos departamento;


     public DtModSucursal(){}

    public DtModSucursal(Long sucursal_id,String nombre, String telefono, Departamentos departamento) {
         this.sucursal_id=sucursal_id;
         this.nombre = nombre;
         this.telefono = telefono;
         this.departamento = departamento;
    }
}
