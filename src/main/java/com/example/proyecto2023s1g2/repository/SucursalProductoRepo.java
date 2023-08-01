package com.example.proyecto2023s1g2.repository;

import com.example.proyecto2023s1g2.domain.Sucursal;
import com.example.proyecto2023s1g2.domain.SucursalProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalProductoRepo extends JpaRepository<SucursalProducto,Long> {

    /*@Query("SELECT s FROM SucursalProducto s WHERE s.producto = :sucursal")
    List<SucursalProducto> getSucursalProductoDeUnaSucursal(@Param("sucursal") Sucursal sucursal);
    */

}
