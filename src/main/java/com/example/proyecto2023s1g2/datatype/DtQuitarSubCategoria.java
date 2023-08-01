package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

@Getter
public class DtQuitarSubCategoria {
    private Long categoria;
    private Long subCategoria;

    public DtQuitarSubCategoria(){};

    public DtQuitarSubCategoria(Long categoria, Long subCategoria) {
        this.categoria = categoria;
        this.subCategoria = subCategoria;
    }
}
