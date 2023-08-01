package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.enumeradores.TipoUsuario;
import lombok.Getter;


import java.time.LocalDate;

public class DtUsuarioSucursal extends DtUsuario{
    @Getter
    private Long id;
    @Getter
    private String nombre;
    @Getter
    private String apellido;
    @Getter
    private String email;
    @Getter
    private int edad;
    @Getter
    private LocalDate fecha_nac;
    @Getter
    private boolean bloqueado;

    @Getter
    private DtSucursal sucursalTrabajo;

    @Getter
    private TipoUsuario tipoUsuario;

    @Getter
    private Long id_sucursal;
   public DtUsuarioSucursal(){}

    public DtUsuarioSucursal(Long id, String nombre, String apellido,int edad, LocalDate fecha_nac, boolean bloqueado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fecha_nac = fecha_nac;
        this.bloqueado = bloqueado;
        this.tipoUsuario = TipoUsuario.USUARIO_SUCURSAL;
    }

    public DtUsuarioSucursal(Long id, String nombre, String apellido,int edad, LocalDate fecha_nac, boolean bloqueado,DtSucursal dtSucursal) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fecha_nac = fecha_nac;
        this.bloqueado = bloqueado;
        this.tipoUsuario = TipoUsuario.USUARIO_SUCURSAL;
        this.sucursalTrabajo = dtSucursal;
    }


    public DtUsuarioSucursal(Long id, String nombre, String apellido, String email,int edad, LocalDate fecha_nac, boolean bloqueado, Long id_sucursal) {
       this.id = id;
       this.nombre = nombre;
       this.apellido = apellido;
       this.email=email;
       this.edad = edad;
       this.fecha_nac = fecha_nac;
       this.bloqueado = bloqueado;
       this.id_sucursal = id_sucursal;
    }

}
