package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

public class DtPromoTotal {

    @Getter
    private Boolean tiene_promo;

    @Getter
    private Boolean tiene_stock;

    @Getter
    private double total_con_descuento;

    @Getter
    private double total_sin_descuento;

    public DtPromoTotal(){}

    public DtPromoTotal(Boolean tiene_promo, Boolean tiene_stock, double total_con_descuento, double total_sin_descuento) {
        this.tiene_promo = tiene_promo;
        this.tiene_stock = tiene_stock;
        this.total_con_descuento = total_con_descuento;
        this.total_sin_descuento = total_sin_descuento;
    }
}
