package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DtCrearUsuarioSucursal {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private LocalDate fecha_nac;
    private Long sucursal_id;

    public DtCrearUsuarioSucursal() {
    }

    public DtCrearUsuarioSucursal(String email, String password, String nombre, String apellido, LocalDate fecha_nac, Long sucursal_id) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nac = fecha_nac;
        this.sucursal_id = sucursal_id;
    }
}

