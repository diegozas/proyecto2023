package com.example.proyecto2023s1g2.repository;

import com.example.proyecto2023s1g2.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductoRepo extends JpaRepository<Producto,Long> {
    @Query("SELECT p FROM Producto p WHERE p.eliminado=false")
    List<Producto> getPorductos();

    @Query("SELECT coalesce(max(id), 0) FROM Producto")
    Long getMaxId();
}
