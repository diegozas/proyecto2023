package com.example.proyecto2023s1g2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.proyecto2023s1g2.domain.Comprador;

@Repository
public interface CompradorRepo extends JpaRepository<Comprador,Long> {
    @Query("SELECT c FROM Comprador c JOIN c.direcciones d WHERE d.id = :direccionId")
    Comprador findByDireccionId(@Param("direccionId") Long direccionId);
}
