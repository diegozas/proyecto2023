package com.example.proyecto2023s1g2.service;

import com.example.proyecto2023s1g2.datatype.DtCategoria;
import com.example.proyecto2023s1g2.datatype.DtProducto;
import com.example.proyecto2023s1g2.datatype.DtProductoMod;
import com.example.proyecto2023s1g2.domain.*;
import com.example.proyecto2023s1g2.repository.CategoriaRepo;
import com.example.proyecto2023s1g2.repository.ProductoRepo;
import com.example.proyecto2023s1g2.repository.SucursalProductoRepo;
import com.example.proyecto2023s1g2.repository.SucursalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepo productoRepo;
    @Autowired
    private CategoriaRepo categoriaRepo;
    @Autowired
    private SucursalRepo sucursalRepo;
    @Autowired
    private SucursalProductoRepo sucursalProductoRepo;

    public String crearProducto(DtProducto dtProducto, Long categoria_id,String imagen) {
        try {
            Categoria categoria = categoriaRepo.findById(categoria_id).get();
            Producto producto = new Producto(dtProducto.getNombre(), dtProducto.getDescripcion(), dtProducto.getPrecio(),imagen);

            categoria.getProductos().add(producto);
            //si da true significa que esa categoria es sub categoria de otra
            if (categoria.isRelacion()) {
                //obtengo todas las categorias que tienen subcategorias
                List<Categoria> categorias = categoriaRepo.getCategoriaMadre();
                for (Categoria c : categorias) {
                    //recorro las subcategorias de cada categoria
                    agregarProductoCategoria(categoria_id, producto, c);
                }
                categoriaRepo.save(categoria);
                Long maxId = productoRepo.getMaxId();
                crearSucursalProducto(maxId);
            } else {
                categoriaRepo.save(categoria);
                Long maxId = productoRepo.getMaxId();
                crearSucursalProducto(maxId);
            }
            return "ok";
        }catch (Exception e){
            return "No existe una categoria con ese id";
        }
    }

    private void agregarProductoCategoria(Long categoria_id, Producto producto, Categoria c) {
        Iterator<Categoria> iterador = c.getCategorias().iterator();
        Boolean flag = true;
        while (iterador.hasNext() && flag) {
            Categoria elemento = iterador.next();
            if (elemento.getId().equals(categoria_id)) {
                c.getProductos().add(producto);
                categoriaRepo.save(c);
                flag = false;
            }
        }
    }

    private void crearSucursalProducto(Long producto_id) {
        List<Sucursal> sucursales = sucursalRepo.findAll();
        Producto producto = productoRepo.findById(producto_id).get();
        for (Sucursal s: sucursales){
            //Creamos y guardamos una nueva SucursalProducto con el Producto creado y la sucursal seleccionada actualmente
            SucursalProducto sucursalProd = new SucursalProducto();
            sucursalProd.setProducto(producto);
            sucursalProd.setSucursal(s);
            sucursalProductoRepo.save(sucursalProd);

            //guardamos la nueva SucursalProducto en la sucursal seleccionada actualmente
            s.getProductos().add(sucursalProd);
            sucursalRepo.save(s);

            //guardamos la nueva SucursalProducto en el producto creado
            producto.getSucursales().add(sucursalProd);
            productoRepo.save(producto);

        }
    }

    public List<DtProducto> getPorductosCategoria(Long idCategoria) {
        try {
            Categoria categoria = categoriaRepo.findById(idCategoria).get();
            List<DtProducto> productos_retornar = new ArrayList<>();

            //ordena los productos por nombre
            List<Producto> productos = new ArrayList<>(categoria.getProductos());
            Comparator<Producto> comparadorPorNombre = Comparator.comparing(Producto::getNombre);
            Collections.sort(productos, comparadorPorNombre);

            for (Producto p : productos) {
                if (!p.isEliminado()) {
                    productos_retornar.add(p.getDtProducto());
                }
            }
            return productos_retornar;
        }catch (Exception e){
            System.out.println("Entre al catch");
            return null;
        }
    }

    public List<DtProducto> getPorductosCategoriaConStock(Long idCategoria,Long idSucursal) {
        try {
            Categoria categoria = categoriaRepo.findById(idCategoria).get();
            List<DtProducto> productos_retornar = new ArrayList<>();

            //ordena los productos por nombre
            List<Producto> productos = new ArrayList<>(categoria.getProductos());
            Comparator<Producto> comparadorPorNombre = Comparator.comparing(Producto::getNombre);
            Collections.sort(productos, comparadorPorNombre);

            for (Producto p : productos) {
                if (!p.isEliminado()){
                    for(SucursalProducto sp:p.getSucursales()){
                        if(sp.getSucursal().getId().equals(idSucursal)){
                            DtProducto dtp=new DtProducto(p.getId(),p.getNombre(),p.getDescripcion(),p.getPrecio(),sp.getStock(),p.getImagen());
                            productos_retornar.add(dtp);
                        }
                    }

                }
            }
            Comparator<DtProducto> comparadorPorNombreDt = Comparator.comparing(DtProducto::getNombre);
            Collections.sort(productos_retornar, comparadorPorNombreDt);
            return productos_retornar;
        }catch (Exception e){
            System.out.println("Entre al catch");
            return null;
        }
    }

    public List<DtProducto> getPorductosConStock(Long idSucursal) {
        try {
            List<DtProducto> productos_retornar = new ArrayList<>();
            //ordena los productos por nombre
            List<Producto> productos = productoRepo.findAll();
            Comparator<Producto> comparadorPorNombre = Comparator.comparing(Producto::getNombre);
            Collections.sort(productos, comparadorPorNombre);
            for (Producto p : productos) {
                if (!p.isEliminado()){
                    for(SucursalProducto sp:p.getSucursales()){
                        if(sp.getSucursal().getId().equals(idSucursal)){
                            //DtProducto dtp=new DtProducto(p.getId(),p.getNombre(),p.getDescripcion(),p.getPrecio(),sp.getStock(),p.getImagen());
                            DtProducto dtp=agregarCategoriaDtProductoConStock(p,sp.getStock());
                            productos_retornar.add(dtp);
                        }
                    }

                }
            }

            Comparator<DtProducto> comparadorPorNombreDt = Comparator.comparing(DtProducto::getNombre);
            Collections.sort(productos_retornar, comparadorPorNombreDt);
            return productos_retornar;
        }catch (Exception e){
            System.out.println("Entre al catch");
            return null;
        }
    }

    private DtProducto agregarCategoriaDtProductoConStock(Producto p,int stock) {
        DtProducto dtp=new DtProducto(p.getId(),p.getNombre(),p.getDescripcion(),p.getPrecio(),stock,p.getImagen());
        List<Categoria> categorias=categoriaRepo.findByProductoId(dtp.getId());
        if(categorias.size()>1){
            for (Categoria c:categorias){
                if(c.isRelacion()){
                    DtCategoria dtc=new DtCategoria(c.getId(),c.getTitulo());
                    dtp.getCategorias().add(dtc);
                }
            }
        }else{
            for (Categoria c:categorias){
                DtCategoria dtc=new DtCategoria(c.getId(),c.getTitulo());
                dtp.getCategorias().add(dtc);
            }
        }

        return dtp;
    }

    public String eliminarProducto(Long productoId) {
        try {
            Producto producto=productoRepo.findById(productoId).get();
            producto.setEliminado(true);
            productoRepo.save(producto);
            return "ok";
        }catch (Exception e){
            return "No existe un producto con ese id";
        }

    }

    public String modrProducto(DtProductoMod dtpMod) {
        try {
            Producto producto=productoRepo.findById(dtpMod.getId()).get();
            producto.setPrecio(dtpMod.getPrecio());
            producto.setNombre(dtpMod.getNombre());
            producto.setDescripcion(dtpMod.getDescripcion());
            producto.setImagen(dtpMod.getImagen());
            productoRepo.save(producto);
            return "ok";
        }catch (Exception e){
            return "no existe un producto con ese id";
        }
    }

    public List<DtProducto> getPorductos() {
        List<Producto> productos=productoRepo.findAll(Sort.by("nombre"));
        List<DtProducto> dtProductos=new ArrayList<>();
        for (Producto p:productos){
            if(!p.isEliminado()) {
                List<Categoria> categorias=categoriaRepo.findByProductoId(p.getId());
                dtProductos.add(getDtProductoConCategoria(categorias,p));
            }
        }

        return dtProductos;
    }


    private DtProducto getDtProductoConCategoria(List<Categoria> categorias,Producto producto){
        List<DtCategoria> dtCategorias=new ArrayList<>();
        DtProducto dtp=producto.getDtProducto();
        if(categorias.size()>1){
            for (Categoria c:categorias){
                if(c.isRelacion()){
                    DtCategoria dtc=new DtCategoria(c.getId(),c.getTitulo());
                    dtCategorias.add(dtc);
                }
            }
        }else{
            for (Categoria c:categorias) {
                DtCategoria dtc = new DtCategoria(c.getId(), c.getTitulo());
                dtCategorias.add(dtc);
            }
        }
        for (DtCategoria dtc:dtCategorias){
            dtp.getCategorias().add(dtc);
        }
        return dtp;
    }

    public DtProducto getPorducto(Long productoId) {
        try {
            List<Categoria> categorias=categoriaRepo.findByProductoId(productoId);
            Producto producto=productoRepo.findById(productoId).get();
            //return producto.getDtProducto();
            return getDtProductoConCategoria(categorias,producto);
        }catch (Exception e){
            return null;
        }
    }

    public DtProducto getPorductoConStocl(Long productoId, Long sucursalId) {
        try {
            Producto producto=productoRepo.findById(productoId).get();
            return getDtProducto(producto,sucursalId);
        }catch (Exception e){
            return null;
        }
    }
    private DtProducto getDtProducto(Producto producto,Long sucursal_id){
        List<Categoria> categorias=categoriaRepo.findByProductoId(producto.getId());
        List<DtCategoria> dtCategorias=new ArrayList<>();
        for (Categoria c:categorias){
            DtCategoria dtc=new DtCategoria(c.getId(),c.getTitulo());
            dtCategorias.add(dtc);
        }
        Iterator<SucursalProducto> iterador = producto.getSucursales().iterator();
        while (iterador.hasNext()) {
            SucursalProducto sucursalProducto = iterador.next();
            if (sucursalProducto.getSucursal().getId() == sucursal_id) {
                //DtProducto dtProducto = new DtProducto(producto.getId(),producto.getNombre(),producto.getDescripcion(),producto.getPrecio(),sucursalProducto.getStock(),producto.getImagen());
                DtProducto dtProducto=agregarCategoriaDtProductoConStock(producto,sucursalProducto.getStock());
                return dtProducto;
            }
        }
        return null;
    }

    public void cargarStock() {
        List<Producto> productos=productoRepo.findAll();
        for (Producto p:productos){
            for(SucursalProducto sp:p.getSucursales()){
                sp.setStock(20);
                sucursalProductoRepo.save(sp);
            }
        }
    }
    /*private void eliminarDtProductoVenta(List<DtProducto> productos,Long id){
        Iterator<DtProducto> iterador = productos.iterator();
        while (iterador.hasNext()) {
            DtProducto producto = iterador.next();
            if (producto.getId() == id) {
                iterador.remove();
                break;
            }
        }
    }
    */




/*
    public List<SucursalProducto> getProductosSucursalProducto() {
        Producto producto = productoRepo.findById(1L).get();
        return producto.getSucursales();
    }

 */
    /*
    public DtProducto buscarProductoConStock(Long producto_id, Long sucursal_id) {
        Sucursal sucursal = sucursalRepo.findById(sucursal_id).get();
        DtProducto dtp = new DtProducto();
        for (SucursalProducto sp: sucursal.getProductos()){
            if(!sp.getProducto().isEliminado() && sp.getProducto().getId().equals(producto_id)){
                dtp = new DtProducto(sp.getProducto().getId(),sp.getProducto().getNombre(),sp.getProducto().getDescripcion(),sp.getProducto().getPrecio(),sp.getStock(),sp.getProducto().getImagen().getNombre());
            }
        }
        return dtp;
    }
    */
}