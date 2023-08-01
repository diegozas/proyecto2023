package com.example.proyecto2023s1g2.domain;

import com.example.proyecto2023s1g2.datatype.DtDireccion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="direccion")
public class Direccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter@Setter
    @Column(name = "direccion_id")
    private Long id;

    @Column(name="calle")
    @Getter@Setter
    private String calle;

    @Column(name="numero")
    @Getter@Setter
    private String numero;

    @Column(name="departamento")
    @Getter@Setter
    private String apartamento;

    @Column(name="esquina")
    @Getter@Setter
    private String esquina;

    @Column(name="barrio")
    @Getter@Setter
    private String barrio;

    @Column(name="localidad")
    @Getter@Setter
    private String localidad;

    @Column(name="codigo_postal")
    @Getter@Setter
    private int codigo_postal;

    @Column(name="manzana")
    @Getter@Setter
    private String manzana;

    @Column(name="solar")
    @Getter@Setter
    private String solar;

    @Column(name = "eliminada")
    @Getter@Setter
    private boolean eliminada;

    public Direccion(){}
    public Direccion(String calle, String numero, String apartamento, String esquina, String barrio, String localidad, int codigo_postal, String manzana, String solar) {
        this.calle = calle;
        this.numero = numero;
        this.apartamento = apartamento;
        this.esquina = esquina;
        this.barrio = barrio;
        this.localidad = localidad;
        this.codigo_postal = codigo_postal;
        this.manzana = manzana;
        this.solar = solar;
        this.eliminada=false;
    }


    public DtDireccion getDtDireccion(){
        return new DtDireccion(this.id,this.calle,this.numero,this.apartamento,this.esquina,this.barrio,this.localidad,this.codigo_postal,this.manzana,this.solar);
    }


    @Override
    public String toString() {
        return "Direccion{" +
                "calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", apartamento='" + apartamento + '\'' +
                ", esquina='" + esquina + '\'' +
                ", barrio='" + barrio + '\'' +
                ", localidad='" + localidad + '\'' +
                ", codigo_postal=" + codigo_postal +
                ", manzana='" + manzana + '\'' +
                ", solar='" + solar + '\'' +
                '}';
    }
}
