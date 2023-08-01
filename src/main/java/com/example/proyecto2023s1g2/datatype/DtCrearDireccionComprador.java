package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

@Getter
public class DtCrearDireccionComprador {
   private DtDireccion domicilio;
   private Long id;

   public DtCrearDireccionComprador(){}


    public DtCrearDireccionComprador(DtDireccion dtDireccion, Long comprador_id) {
        this.domicilio = dtDireccion;
        this.id = comprador_id;
    }
}
