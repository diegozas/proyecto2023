package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DtCategoria {
    @Getter
    Long id;

    @Getter
    String titulo;

    @Getter
    List<DtCategoria> subCategoria=new ArrayList<>();

    public DtCategoria(){}
    public DtCategoria(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }


}
