package com.example.proyecto2023s1g2.domain;

import com.example.proyecto2023s1g2.datatype.DtProducto;
import com.example.proyecto2023s1g2.datatype.DtProductoDescuento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "producto_id")
    @Getter@Setter
    private Long id;

    @Column(name = "nombre")
    @Getter@Setter
    private String nombre;

    @Column(name= "descripcion")
    @Getter@Setter
    private String descripcion;

    @Column(name= "precio")
    @Getter@Setter
    private Double precio;

    @Column(name = "imagen")
    @Getter@Setter
    private String imagen;

    @Column(name="eliminado")
    @Getter@Setter
    private boolean eliminado;

    @OneToMany(mappedBy = "producto")
    @Setter@Getter
    private List<SucursalProducto> sucursales = new ArrayList<>();

    public Producto(){}

    public Producto(String nombre, String descripcion, Double precio, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.eliminado = false;
    }

    public DtProducto getDtProducto() {
        return new DtProducto(this.id,this.nombre,this.descripcion,this.precio,this.imagen);
    }

    public DtProductoDescuento getDtProductoDescuento() {
        return new DtProductoDescuento(this.id,this.nombre,this.descripcion,this.precio,this.imagen);
    }
}
