package com.example.proyecto2023s1g2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "sucursal_producto")
public class SucursalProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "sucursalProducto_id")
    @Getter@Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    @Getter@Setter
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @Getter@Setter
    private Producto producto;

    @Column(name = "stock")
    @Getter@Setter
    private int stock;

    public SucursalProducto() {
    }
    public SucursalProducto(Sucursal sucursal, Producto producto) {
        this.sucursal = sucursal;
        this.producto = producto;
        this.stock = 0;
    }
}
