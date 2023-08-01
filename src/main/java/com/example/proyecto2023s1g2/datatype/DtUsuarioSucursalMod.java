package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DtUsuarioSucursalMod {
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fecha_nac;


    public DtUsuarioSucursalMod() {}

    public DtUsuarioSucursalMod(Long id, String nombre, String apellido, LocalDate fecha_nac) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nac = fecha_nac;
    }
}

