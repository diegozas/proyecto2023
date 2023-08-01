package com.example.proyecto2023s1g2.repository;

import com.example.proyecto2023s1g2.domain.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamoRepo extends JpaRepository<Reclamo,Long> {
}
