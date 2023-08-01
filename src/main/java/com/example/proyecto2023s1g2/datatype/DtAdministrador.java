package com.example.proyecto2023s1g2.datatype;

import com.example.proyecto2023s1g2.enumeradores.TipoUsuario;
import lombok.Getter;

public class DtAdministrador extends DtUsuario {
    @Getter
    private Long id;
    @Getter
    private String email;
    @Getter
    private TipoUsuario tipoUsuario;

    public DtAdministrador(){}
    public DtAdministrador(Long id,String email){
        this.id=id;
        this.email=email;
        this.tipoUsuario=TipoUsuario.ADMINISTRATOR;
    }

}
