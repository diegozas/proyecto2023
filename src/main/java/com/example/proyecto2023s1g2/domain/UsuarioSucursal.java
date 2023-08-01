package com.example.proyecto2023s1g2.domain;

import com.example.proyecto2023s1g2.datatype.DtComprador;
import com.example.proyecto2023s1g2.datatype.DtUsuarioSucursal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="usuario_sucursal")
@PrimaryKeyJoinColumn(referencedColumnName = "usuario_id")

public class UsuarioSucursal extends Usuario {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "nombre")
    @Getter@Setter
    private String nombre;

    @Column(name = "apellido")
    @Getter@Setter
    private String apellido;

    @Column(name = "fecha_nac")
    @Getter@Setter
    private LocalDate fecha_nac;

    @Column(name = "bloqueado")
    @Getter@Setter
    private boolean bloqueado;

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    @Getter@Setter
    private Sucursal sucursal;

    public int getEdad(){
        Period periodo = Period.between(this.fecha_nac,LocalDate.now());
        return periodo.getYears();
    }

    public UsuarioSucursal() {}

    public UsuarioSucursal(String email, String password, String nombre, String apellido, LocalDate fecha_nac) {
        super(email, password);
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nac = fecha_nac;
    }

    public DtUsuarioSucursal getDtUsuarioSucursal(){
        return new DtUsuarioSucursal(this.id,this.nombre,this.apellido,getEdad(),this.fecha_nac,this.bloqueado);
    }

    public DtUsuarioSucursal getDtUsuarioSucursalId(){
        return new DtUsuarioSucursal(this.id,this.nombre,this.apellido,getEmail(),getEdad(),this.fecha_nac,this.bloqueado,this.sucursal.getId());
    }




}
