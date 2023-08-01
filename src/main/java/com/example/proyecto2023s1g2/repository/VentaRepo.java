package com.example.proyecto2023s1g2.repository;

import com.example.proyecto2023s1g2.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepo extends JpaRepository<Venta,Long> {
    @Query("SELECT coalesce(max(id), 0) FROM Venta")
    Long getMaxId();

    @Query("SELECT v FROM Venta v WHERE v.lugar_retiro.id = :direccionId")
    Venta findByDireccionId(@Param("direccionId") Long direccionId);

}
