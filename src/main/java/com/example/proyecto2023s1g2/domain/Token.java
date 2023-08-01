package com.example.proyecto2023s1g2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    @Getter
    @Setter
    private String token;

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    @Getter @Setter
    private Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date fechaCreacion;


    public Token() {
    }

    public Token(Usuario usuario, String tokenValue) {
        this.usuario=usuario;
        this.token=tokenValue;
    }


}