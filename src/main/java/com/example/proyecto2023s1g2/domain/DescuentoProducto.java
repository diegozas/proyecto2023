package com.example.proyecto2023s1g2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="descuentoproducto")
@PrimaryKeyJoinColumn(referencedColumnName = "promocion_id")
public class DescuentoProducto extends Promocion{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "porcentaje")
    @Getter@Setter
    private int porcentaje;
/*
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name="descuento_productos")
    @JoinColumn(name="descuentoproducto_id")
    @JoinColumn(name="producto_id")
    @Column(name = "productos")
    @Getter@Setter
    private List<Producto> productos = new ArrayList<>();
    */

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "descuento_productos",
            joinColumns = @JoinColumn(name = "descuentoproducto_id", referencedColumnName = "promocion_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "producto_id"))
    @Setter@Getter
    private List<Producto> productos = new ArrayList<>();

    public DescuentoProducto() {}

    public DescuentoProducto(String titulo, String descripcion, LocalDateTime fecha_I, LocalDateTime fecha_F, int porcentaje, List<Producto> productos) {
        super(titulo, descripcion, fecha_I, fecha_F);
        this.porcentaje = porcentaje;
        this.productos = productos;
    }
}
