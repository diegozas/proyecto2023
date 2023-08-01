package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.domain.Producto;
import com.example.proyecto2023s1g2.domain.Sucursal;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class DtSucursalProducto implements Serializable {

    @Getter
    private Long id;

    @Getter
    private Long sucursal_id;

    @Getter
    private Long producto_id;

    @Getter
    private int stock;

    public DtSucursalProducto() {
    }

    public DtSucursalProducto(Long sucursal, Long producto) {
        this.sucursal_id = sucursal;
        this.producto_id = producto;
        this.stock = 0;
    }

    public DtSucursalProducto(Long sucursal, Long producto,int stock) {
        this.sucursal_id = sucursal;
        this.producto_id = producto;
        this.stock = stock;
    }


}
