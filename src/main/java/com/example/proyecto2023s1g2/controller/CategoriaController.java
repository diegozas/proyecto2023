package com.example.proyecto2023s1g2.controller;

import com.example.proyecto2023s1g2.datatype.DtCategoria;
import com.example.proyecto2023s1g2.datatype.DtProducto;
import com.example.proyecto2023s1g2.datatype.DtQuitarSubCategoria;
import com.example.proyecto2023s1g2.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/categoria/alta")
    public Long altaCategoria(@RequestBody DtCategoria categoria){
        return categoriaService.guardarCategoria(categoria.getTitulo());
    }

    @GetMapping("/categoria/listar")
    public List<DtCategoria> listarCategorias(){return categoriaService.getCategorias();}

    @GetMapping("/categoria/listarCategoriaSinRelacion")
    public List<DtCategoria> listarCategoriasSinRelacion(@RequestParam Long categoria_id){
        return categoriaService.getCategoriasSinRelacion(categoria_id);
    }


    @GetMapping("/categoria/categoriaConSub")
    public List<DtCategoria> categoriasConSubCategoria(){return categoriaService.getCategoriasConSubCategorias();}

    @GetMapping("/categoria/buscarPorNombre")
    public ResponseEntity<?> buscarCategoriaPorNombre(@RequestParam("nombre") String nombre){
        return categoriaService.buscarCategoriaPorNombre(nombre);
    }

    @GetMapping("/categoria/buscarCategoriaPorId")
    public DtCategoria buscarCategoriaPorId(@RequestParam("id") Long id){
        return categoriaService.buscarCategoriaPorId(id);
    }

    @PostMapping("/categoria/agregar")
    public ResponseEntity<?> agregarSubCategoria(@RequestParam("categoria") Long id, @RequestParam("agregar") Long agregar){
        return categoriaService.agregarCategoria(id,agregar);
    }

    @GetMapping("/categoria/categoriaSubCategorias")
    public List<DtCategoria> getCategoriasSubCategoriasYsinRelacion(){
        return categoriaService.getSubCategoriasYcategoriasSinSub();
    }
/*
    @GetMapping("/categoria/Buscar")
    public DtCategoria buscarCategoria(@RequestParam("id_categoria")Long categoria_id,
                                       @RequestParam("id_sucursal")Long sucursal_id){
        return categoriaService.buscarCategoria(categoria_id, sucursal_id);
    }
*/
    @GetMapping("/categoria/listarProductos")
    public List<DtProducto> listarProductosCategoria(@RequestParam("id_categoria")Long categoria_id,
                                                     @RequestParam("id_sucursal")Long sucursal_id){
        return categoriaService.listarProductosCategoria(categoria_id, sucursal_id);
    }


    @DeleteMapping("/categoria/quitarSubCategoria")
    public String quitarSubCategoria(@RequestBody DtQuitarSubCategoria dtqsc){
        return categoriaService.quitarSubCategoria(dtqsc);
    }

}
