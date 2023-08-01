package com.example.proyecto2023s1g2.domain;

import com.example.proyecto2023s1g2.datatype.DtAdministrador;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="administrador")
@PrimaryKeyJoinColumn(referencedColumnName = "usuario_id")
public class Administrador extends Usuario{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter@Setter
    private Long id;
    public Administrador(){}

    public Administrador(String email, String password) {
        super(email, password);
    }


    public DtAdministrador getDtAdministrador(){
        return new DtAdministrador(this.id,getEmail());
    }
}
