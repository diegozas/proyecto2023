package com.example.proyecto2023s1g2.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.proyecto2023s1g2.datatype.DtCategoria;
import com.example.proyecto2023s1g2.datatype.DtProducto;
import com.example.proyecto2023s1g2.datatype.DtQuitarSubCategoria;
import com.example.proyecto2023s1g2.domain.Categoria;
import com.example.proyecto2023s1g2.domain.Producto;
import com.example.proyecto2023s1g2.domain.Sucursal;
import com.example.proyecto2023s1g2.repository.CategoriaRepo;
import com.example.proyecto2023s1g2.repository.SucursalRepo;
import org.apache.coyote.Response;
import org.apache.tomcat.util.digester.SystemPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class CategoriaService{
    @Autowired
    private CategoriaRepo categoriaRepo;
    @Autowired
    private SucursalRepo sucursalRepo;

    public Long guardarCategoria(String titulo) {
        Categoria categoria = new Categoria(titulo);
        categoriaRepo.save(categoria);
        //Obtengo todas las sucursales del sistema y le agrego la categoria nueva a cada una
        List<Sucursal> sucursales=sucursalRepo.findAll();
        for (Sucursal s:sucursales) {
            s.getCategorias().add(categoria);
            sucursalRepo.save(s);
        }
        Long id = categoriaRepo.getMaxId();
        return id;
    }
    /*Devuelve un list con todas las categorias con sus sub categorias si tienen
        y las categorias que no estan relacionadas a ninguna sub categoria*/
    public List<DtCategoria> getCategoriasConSubCategorias(){
        List<Categoria> categorias=categoriaRepo.findByRelacionFalse(Sort.by("titulo"));
        List<DtCategoria> categorias_retornar=new ArrayList<>();
        for (Categoria c:categorias){
            categorias_retornar.add(c.getDtcategoria());
        }
        return categorias_retornar;

    }

    //Devuelve todas las categorias sin relacion
    public List<DtCategoria> getCategorias(){
        List<Categoria> categorias=categoriaRepo.findAll(Sort.by("titulo"));
        List<DtCategoria> categorias_retornar=new ArrayList<>();

        for (Categoria c:categorias) {
            categorias_retornar.add(c.getDtcategoria());
        }
        return categorias_retornar;
    }

    public ResponseEntity<?> buscarCategoriaPorNombre(String nombre){
            List<Categoria> categorias = categoriaRepo.findAll();
            DtCategoria dtC = new DtCategoria();
            for(Categoria c: categorias){
                if(!c.getTitulo().isEmpty()){
                    if(c.getTitulo().toLowerCase().equals(nombre.toLowerCase())){
                        dtC = c.getDtcategoria();
                    }
                }
            }
        if(dtC != null){
            return ResponseEntity.ok(dtC);
        }else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe Categoria con ese nombre");
        }
    }

    public ResponseEntity<?> agregarCategoria(Long categoria_buscar, Long agregar){
        try{
            Categoria categoria = categoriaRepo.findById(categoria_buscar).get();
            try{
                Categoria insertar = categoriaRepo.findById(agregar).get();
                if(insertar.isTiene_categoria()){
                    Map<String, String> respuestaError = new HashMap<>();
                    respuestaError.put("mensaje", "La categoria que quiere agregar posee subcategorias");
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(respuestaError);
                }else if(insertar.isRelacion()){
                    Map<String, String> respuestaError = new HashMap<>();
                    respuestaError.put("mensaje", "La categoria que quiere agregar ya es subcategoria de otra Categoria");
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(respuestaError);
                }else if(categoria.isRelacion()){
                    Map<String, String> respuestaError = new HashMap<>();
                    respuestaError.put("mensaje", "La categoria 'Madre' ya es subcategoria de otra Categoria");
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(respuestaError);
                }else if (categoria_buscar.equals(agregar)){
                    Map<String, String> respuestaError = new HashMap<>();
                    respuestaError.put("mensaje", "Una categoria no puede ser subcategoria de si misma");
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(respuestaError);
                }else{
                    categoria.getCategorias().add(insertar);
                    insertar.setRelacion(true);
                    categoria.setTiene_categoria(true);

                    if (!insertar.getProductos().isEmpty()) {
                        for (Producto p : insertar.getProductos()) {
                            if(!existeProducto(categoria.getProductos(),p)){
                                categoria.getProductos().add(p);
                            }

                        }
                    }
                    categoriaRepo.save(insertar);
                    categoriaRepo.save(categoria);
                    return ResponseEntity.ok("Se agrego la categoria "+ insertar.getTitulo()+" como sub categoria de "+ categoria.getTitulo()+" correctamente");
                }
            }catch (Exception e){
                Map<String, String> respuestaError = new HashMap<>();
                respuestaError.put("mensaje", "No existe la categoria a agregar con se id");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(respuestaError);
            }

        }catch(Exception e){
            Map<String, String> respuestaError = new HashMap<>();
            respuestaError.put("mensaje", "No existe la categoria 'Madre' con ese id");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(respuestaError);
        }
    }

    private boolean existeProducto(List<Producto> productos,Producto producto) {
        boolean existe=false;
        for (Producto p:productos){
            if(p.getId().equals(producto.getId())){
                existe=true;
                break;
            }
        }
        return existe;
    }


    public List<DtCategoria> getSubCategoriasYcategoriasSinSub(){
        List<Categoria> categorias=categoriaRepo.buscarSubCategoriasYcategoriasSinRelacion(Sort.by("titulo"));
        List<DtCategoria> categorias_retornar=new ArrayList<>();

        for (Categoria c:categorias) {
            categorias_retornar.add(c.getDtcategoria());
        }
        return categorias_retornar;

        /*if ((!insertar.isTiene_categoria() && !insertar.isRelacion() && !categoria.isRelacion()) && !categoria_buscar.equals(agregar)){
            categoria.getCategorias().add(insertar);
            insertar.setRelacion(true);
            categoria.setTiene_categoria(true);

            if (!insertar.getProductos().isEmpty()) {
                for (Producto p : insertar.getProductos()) {
                    categoria.getProductos().add(p);
                }
            }
            categoriaRepo.save(insertar);
            categoriaRepo.save(categoria);
            return categoria.getDtcategoria();
        } else {
            return null;
        }
        */
    }

    /*
    public DtCategoria buscarCategoria(Long categoria_id, Long sucursal_id) {
        Sucursal sucursal = sucursalRepo.findById(sucursal_id).get();
        List<Categoria> categorias = sucursal.getCategorias();
    }*/

    public List<DtProducto> listarProductosCategoria(Long categoria_id, Long sucursal_id) {
        List<DtProducto> productos_retornar = new ArrayList<>();
        List<Categoria> categorias = sucursalRepo.findById(sucursal_id).get().getCategorias();
        for(Categoria c:categorias){
            if(c.getId().equals(categoria_id) && !(c.getProductos().isEmpty())){
                List<Producto> productos = (c.getProductos());
                Comparator<Producto> comparadorPorNombre = Comparator.comparing(Producto::getNombre);
                Collections.sort(productos, comparadorPorNombre);
                for (Producto p: productos){
                    if(!p.isEliminado()){
                        productos_retornar.add(p.getDtProducto());
                    }
                }
            }
        }
        return productos_retornar;
    }

    public List<DtCategoria> getCategoriasSinRelacion(Long categoriaId) {
        List<Categoria> categoriasSinRelacion=categoriaRepo.getCategoriaSinRelacion(Sort.by("titulo"));
        List<DtCategoria> dtCategorias=new ArrayList<>();
        for (Categoria c:categoriasSinRelacion){
            if(!c.getId().equals(categoriaId)){
                dtCategorias.add(c.getDtcategoria());
            }

        }
        return dtCategorias;
    }

    public DtCategoria buscarCategoriaPorId(Long id) {
        return categoriaRepo.findById(id).get().getDtcategoria();
    }

    public String quitarSubCategoria(DtQuitarSubCategoria dtqsc) {
            Categoria categoria=categoriaRepo.findById(dtqsc.getCategoria()).get();
            if(!categoria.getCategorias().isEmpty()){
                eliminarCategoriaPorId(categoria.getCategorias(),dtqsc.getSubCategoria());
                if(categoria.getCategorias().isEmpty()){
                    categoria.setTiene_categoria(false);
                }
                Categoria subCategoria=categoriaRepo.findById(dtqsc.getSubCategoria()).get();
                List<Producto> productos=subCategoria.getProductos();
                categoria.getProductos().removeAll(productos);
                categoriaRepo.save(categoria);
                return "ok";
            }else {
                return "La categoria no tiene sub categoria";
            }
    }

    public void eliminarCategoriaPorId(List<Categoria> categorias,Long id) {
        Iterator<Categoria> iterator = categorias.iterator();
        while (iterator.hasNext()) {
            Categoria categoria = iterator.next();
            if (categoria.getId() == id) {
                iterator.remove();
                categoria.setRelacion(false);
                categoriaRepo.save(categoria);
                break; // Si solo quieres eliminar la primera coincidencia, puedes usar 'break' para salir del bucle después de eliminarla.
            }
        }
    }
}