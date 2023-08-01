package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.domain.Direccion;
import lombok.Getter;

import java.time.LocalDate;
@Getter
public class DtCompradorMod {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private LocalDate fecha_nac;
    private String foto;


    public DtCompradorMod() {}

    public DtCompradorMod(Long id, String nombre, String apellido, String telefono, LocalDate fecha_nac,String foto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fecha_nac = fecha_nac;
        this.foto=foto;
    }
}
