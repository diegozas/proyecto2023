package com.example.proyecto2023s1g2.repository;

import com.example.proyecto2023s1g2.domain.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepo extends JpaRepository<Direccion,Long> {
}
