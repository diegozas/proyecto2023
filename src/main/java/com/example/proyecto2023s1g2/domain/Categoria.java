package com.example.proyecto2023s1g2.domain;

import com.example.proyecto2023s1g2.datatype.DtCategoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    @Getter@Setter
    private Long id;

    @Column(name = "titulo")
    @Getter@Setter
    private String titulo;

    @Column(name="relacion")
    @Setter@Getter
    private boolean relacion;

    @Column(name="tiene_categoria")
    @Setter@Getter
    private boolean tiene_categoria;
/*
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="categoria_producto")
    @JoinColumn(name="categoria_id")
    @JoinColumn(name="producto_id")
    @Setter@Getter
    private List<Producto> productos=new ArrayList<Producto>();
*/
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "categoria_producto",
            joinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "categoria_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "producto_id"))
    @Setter@Getter
    private List<Producto> productos=new ArrayList<Producto>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="categoria_subcategoria")
    @JoinColumn(name="categoria_id")
    @JoinColumn(name="subcategoria_id")
    @Setter@Getter
    private List<Categoria> categorias=new ArrayList<Categoria>();

    public Categoria(String titulo) {
        this.titulo = titulo;
        this.relacion=false;
    }

    public Categoria(){}

    public DtCategoria getDtcategoria() {
        if(!categorias.isEmpty()){
            List<DtCategoria> dtCategorias=new ArrayList<>();
            for (Categoria c:getCategorias()){
                DtCategoria dtc=new DtCategoria(c.getId(),c.getTitulo());
                dtCategorias.add(dtc);
            }
            DtCategoria dtCategoria=new DtCategoria(this.id,this.titulo);
            for (DtCategoria dtc:dtCategorias){
                dtCategoria.getSubCategoria().add(dtc);
            }
            return dtCategoria;
        }else{
            return new DtCategoria(this.id,this.titulo);
        }

    }
}
