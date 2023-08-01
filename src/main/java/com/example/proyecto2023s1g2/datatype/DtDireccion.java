package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;


public class DtDireccion {
    @Getter
    private Long id;
    @Getter
    private String calle;
    @Getter
    private String numero;
    @Getter
    private String apartamento;
    @Getter
    private String esquina;
    @Getter
    private String barrio;
    @Getter
    private String localidad;
    @Getter
    private int codigo_postal;
    @Getter
    private String manzana;
    @Getter
    private String solar;


    public DtDireccion(){}

    public DtDireccion(Long id, String calle, String numero, String apartamento, String esquina, String barrio, String localidad, int codigo_postal, String manzana, String solar) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.apartamento = apartamento;
        this.esquina = esquina;
        this.barrio = barrio;
        this.localidad = localidad;
        this.codigo_postal = codigo_postal;
        this.manzana = manzana;
        this.solar = solar;
    }

    @Override
    public String toString() {
        return
                "calle: " + calle + "\n" +
                "numero: " + numero + "\n" +
                "apartamento: " + apartamento + "\n" +
                "esquina: " + esquina + "\n" +
                "barrio: " + barrio + "\n" +
                "localidad: " + localidad + "\n" +
                "codigo postal: " + codigo_postal + "\n"+
                "manzana: " + manzana + "\n" +
                "solar: " + solar + "\n";
    }
}
