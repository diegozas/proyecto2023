package com.example.proyecto2023s1g2.domain;

import com.example.proyecto2023s1g2.datatype.DtComprador;
import com.example.proyecto2023s1g2.datatype.DtDireccion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="comprador")
@PrimaryKeyJoinColumn(referencedColumnName = "usuario_id")
public class Comprador extends Usuario{
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


    @Column(name = "telefono")
    @Getter@Setter
    private String telefono;

    @Column(name = "bloqueado")
    @Getter@Setter
    private boolean bloqueado;

    @Column(name = "eliminado")
    @Getter@Setter
    private boolean eliminado;

    @Column(name = "foto")
    @Getter@Setter
    private String foto;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "comprador_venta")
    @JoinColumn(name = "comprador_id")
    @JoinColumn(name = "venta_id")
    @Getter@Setter
    private List<Venta> historial_venta = new ArrayList<Venta>();



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "comprador_direccion")
    @JoinColumn(name = "comprador_id")
    @JoinColumn(name = "direccion_id")
    @Getter@Setter
    private List<Direccion> direcciones = new ArrayList<Direccion>();



    @Column(name = "billetera")
    @Getter@Setter
    private double billetera;
/*
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name="comprador_reclamo")
    @JoinColumn(name="comprador_id")
    @JoinColumn(name="reclamo_id")
    @Getter@Setter
    private List<Reclamo> reclamos = new ArrayList<>();
*/
    public int getEdad(){
        Period periodo = Period.between(this.fecha_nac,LocalDate.now());
        return periodo.getYears();
    }



    public Comprador(){}

    public Comprador(String nombre, String apellido, String email, String password, LocalDate fecha_nac,String telefono,String foto) {
        super(email, password);
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.foto = foto;
        this.bloqueado = false;
        this.eliminado = false;
        this.billetera = 0;
        this.fecha_nac = fecha_nac;
    }

    public DtComprador getDtComprador(){
        if(direcciones.isEmpty()){
            return new DtComprador(this.id, this.nombre, this.apellido, getEmail(),getEdad(),this.telefono,this.foto,this.fecha_nac, this.bloqueado,this.eliminado,null,this.billetera);
        }else{
            List<DtDireccion> direcciones_comprador=new ArrayList<>();
            for (Direccion d:direcciones){
                if(!d.isEliminada()){
                    direcciones_comprador.add(d.getDtDireccion());
                }
            }
            return new DtComprador(this.id, this.nombre, this.apellido, getEmail(),getEdad(),this.telefono,this.foto,this.fecha_nac, this.bloqueado,this.eliminado,direcciones_comprador,this.billetera);
        }
    }

}
