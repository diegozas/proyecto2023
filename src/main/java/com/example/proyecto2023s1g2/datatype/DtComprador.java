package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.domain.Direccion;
import com.example.proyecto2023s1g2.domain.Usuario;
import com.example.proyecto2023s1g2.domain.Venta;
import com.example.proyecto2023s1g2.enumeradores.TipoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


public class DtComprador extends DtUsuario{

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
    private String telefono;

    @Getter
    private String foto;

    @Getter
    private boolean bloqueado;
    @Getter
    private boolean eliminado;
    @Getter
    private TipoUsuario tipoUsuario;

    @Getter
    private List<DtVenta> historial_venta = new ArrayList<DtVenta>();
    @Getter
    private List<DtDireccion> direcciones=new ArrayList<DtDireccion>();
    @Getter
    private double billetera;

    public DtComprador(){}

    public DtComprador(Long id, String nombre, String apellido, String email, int edad, String telefono,String foto,LocalDate fecha_nac, boolean bloqueado,boolean eliminado,List<DtDireccion> direcciones,double billetera) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.edad = edad;
        this.telefono = telefono;
        this.foto = foto;
        this.fecha_nac = fecha_nac;
        this.bloqueado = bloqueado;
        this.eliminado = eliminado;
        this.direcciones = direcciones;
        this.billetera = billetera;
        this.tipoUsuario = TipoUsuario.COMPRADOR;
    }








    @Override
    public String toString() {
        return "DtComprador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", edad=" + edad +
                ", fecha_nac=" + fecha_nac +
                ", telefono='" + telefono + '\'' +
                ", bloqueado=" + bloqueado +
                ", billetera=" + billetera +
                '}';
    }
}
