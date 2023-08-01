package com.example.proyecto2023s1g2.repository;

import com.example.proyecto2023s1g2.domain.Categoria;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepo extends JpaRepository<Categoria,Long> {
    //devuelve todas las categorias con subcategorias y categorias que no estan asociada a otra categoria
    @Query("SELECT c FROM Categoria c WHERE c.relacion = false or c.tiene_categoria=true")
    List<Categoria> findByRelacionFalse(Sort sort);

    @Query("SELECT c FROM Categoria c WHERE c.relacion = false and c.tiene_categoria=false")
    List<Categoria> getCategoriaSinRelacion(Sort sort);


    @Query("SELECT c FROM Categoria c WHERE c.relacion = true or c.tiene_categoria=false")
    List<Categoria> buscarSubCategoriasYcategoriasSinRelacion(Sort sort);
    List<Categoria> findAll(Sort sort);

    @Query("SELECT c FROM Categoria c WHERE c.tiene_categoria=true")
    List<Categoria> getCategoriaMadre();

    @Query("SELECT coalesce(max(id), 0) FROM Categoria")
    Long getMaxId();

    @Query("SELECT c FROM Categoria c WHERE c.titulo = :nombre")
    List<Categoria> findByTitulo(@Param("nombre") String nombre);

    //devuelve las categorias a la que pertenece el producto que pasan por parametro
    @Query("SELECT c FROM Categoria c JOIN c.productos p WHERE p.id = :productoId")
    List<Categoria> findByProductoId(@Param("productoId") Long productoId);




}

