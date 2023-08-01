package com.example.proyecto2023s1g2.domain;

import com.example.proyecto2023s1g2.datatype.DtRegaloProducto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="regaloproducto")
@PrimaryKeyJoinColumn(referencedColumnName = "promocion_id")
public class RegaloProducto extends Promocion{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    @Getter @Setter
    private Long id;

    @Column(name="producto")
    @Getter@Setter
    Long id_Prod;

    @Column(name="stock")
    @Getter@Setter
    int stock;

    public RegaloProducto(){}

    public RegaloProducto(String titulo, String descripcion, LocalDateTime fecha_I, LocalDateTime fecha_F, Long id_Prod, int stock) {
        super(titulo, descripcion, fecha_I, fecha_F);
        this.id_Prod = id_Prod;
        this.stock = stock;
    }
}
