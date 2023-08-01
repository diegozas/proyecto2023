package com.example.proyecto2023s1g2.domain;

import com.example.proyecto2023s1g2.datatype.DtSucursal;
import com.example.proyecto2023s1g2.enumeradores.Departamentos;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="sucursal")
public class Sucursal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter@Setter
    @Column(name = "sucursal_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    @Getter@Setter
    private Direccion domicilio;

    @Column(name = "nombre")
    @Setter@Getter
    private String nombre;

    @Column(name = "telefono")
    @Getter@Setter
    private String telefono;

    @Column(name = "departamento")
    @Setter@Getter
    private Departamentos departamento;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sucursal_categoria",
            joinColumns = @JoinColumn(name = "sucursal_id", referencedColumnName = "sucursal_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "categoria_id"))
    @Setter@Getter
    private List<Categoria> categorias =new ArrayList<Categoria>();

    @OneToMany(mappedBy = "sucursal")
    @Setter@Getter
    private List<SucursalProducto> productos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name="sucursal_usuario_sucursal")
    @JoinColumn(name="sucursal_id")
    @JoinColumn(name="usuario_sucursal_id")
    @Getter@Setter
    private List<UsuarioSucursal> usuariosSucursal = new ArrayList<UsuarioSucursal>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name="sucursal_venta")
    @JoinColumn(name="sucursal_id")
    @JoinColumn(name="venta_id")
    @Getter@Setter
    private List<Venta> ventas = new ArrayList<Venta>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name="sucursal_promocion")
    @JoinColumn(name="sucursal_id")
    @JoinColumn(name="promocion_id")
    @Getter@Setter
    private List<Promocion> promociones = new ArrayList<Promocion>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name="sucursal_reclamo")
    @JoinColumn(name="sucursal_id")
    @JoinColumn(name="reclamo_id")
    @Getter@Setter
    private List<Reclamo> reclamos = new ArrayList<>();

    public Sucursal(){}

    public Sucursal(String nombre,Direccion domicilio, String telefono,Departamentos departamento) {
        this.nombre=nombre;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.departamento=departamento;

    }

    public DtSucursal getDtSucursal(){
        return new DtSucursal(this.id, this.nombre,this.departamento,this.telefono, this.domicilio.getDtDireccion());
    }



}
