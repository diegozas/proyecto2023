package com.example.proyecto2023s1g2.datatype;

import lombok.Getter;

public class DtUsuario {

    @Getter
    private String email;

    @Getter
    private String password;

    @Getter
    private String token;
    public DtUsuario(){
    }

    public DtUsuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public DtUsuario(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }
}
