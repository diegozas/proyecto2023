package com.example.proyecto2023s1g2.repository;

import com.example.proyecto2023s1g2.domain.DescuentoCompraFinal;
import com.example.proyecto2023s1g2.domain.DescuentoProducto;
import com.example.proyecto2023s1g2.domain.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocionRepo extends JpaRepository<Promocion,Long> {
    @Query("SELECT df FROM DescuentoCompraFinal df")
    List<DescuentoCompraFinal> getdescuentoCompraFinal();
}
