package com.example.proyecto2023s1g2.domain;

import com.example.proyecto2023s1g2.datatype.DtProducto;
import com.example.proyecto2023s1g2.enumeradores.EstadoVenta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="venta")
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "venta_id")
    @Getter @Setter
    private Long id;

    @Column(name = "sucursal_id")
    @Getter@Setter
    private Long sucursal;

    @Column(name = "comprador_id")
    @Getter@Setter
    private Long comprador;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "venta_producto",
            joinColumns = @JoinColumn(name = "venta_id", referencedColumnName = "venta_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "producto_id"))
    @Getter@Setter
    private List<Producto> productos=new ArrayList<Producto>();


    @Column(name = "total")
    @Getter@Setter
    private double total;

    @Column(name="estado")
    @Getter@Setter
    private EstadoVenta estado;

    @Column(name = "fecha")
    @Setter@Getter
    private LocalDateTime fecha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    @Getter@Setter
    private Direccion lugar_retiro;
    public Venta(){}

    public Venta(Long sucursal, Long comprador, LocalDateTime fecha,Direccion lugar_retiro) {
        this.sucursal = sucursal;
        this.comprador = comprador;
        this.fecha = fecha;
        this.lugar_retiro=lugar_retiro;
        this.estado = EstadoVenta.EN_ESPERA;
    }


    public List<DtProducto> getDtProductos(){
        List<DtProducto> productos=new ArrayList<>();
        for (Producto p:this.getProductos()){
            productos.add(p.getDtProducto());
        }
        return productos;
    }




}
