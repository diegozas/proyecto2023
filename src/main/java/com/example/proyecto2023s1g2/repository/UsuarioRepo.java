package com.example.proyecto2023s1g2.repository;

import com.example.proyecto2023s1g2.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByEmail(String email);


}
