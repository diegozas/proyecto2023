package com.example.proyecto2023s1g2.repository;

import com.example.proyecto2023s1g2.domain.Reclamo;
import com.example.proyecto2023s1g2.domain.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepo extends JpaRepository<Sucursal,Long> {

    //Devuelve la sucursal que contenga un UsuarioSucursal con el id que pasan por parametro
    @Query("SELECT s FROM Sucursal s JOIN s.usuariosSucursal u WHERE u.id = :usuarioSucursalId")
    Sucursal findByUsuarioSucursalId(@Param("usuarioSucursalId") Long usuarioSucursalId);
}
