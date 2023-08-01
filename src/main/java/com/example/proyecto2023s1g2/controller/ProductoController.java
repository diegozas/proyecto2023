package com.example.proyecto2023s1g2.controller;

import com.example.proyecto2023s1g2.datatype.DtProducto;
import com.example.proyecto2023s1g2.datatype.DtProductoMod;
import com.example.proyecto2023s1g2.domain.SucursalProducto;
import com.example.proyecto2023s1g2.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/producto/alta")
    public String altaProducto(@RequestBody DtProducto dtP) {
        DtProducto dtProducto = new DtProducto(dtP.getNombre(), dtP.getDescripcion(), dtP.getPrecio());
        return productoService.crearProducto(dtProducto, dtP.getCategoria(), dtP.getImagen());
    }

    @GetMapping("/producto/productoPorCategoria")
    public List<DtProducto> productos(@RequestParam("id") Long id_categoria) {
        return productoService.getPorductosCategoria(id_categoria);
    }

    @GetMapping("/producto/listar")
    public List<DtProducto> productos() {
        return productoService.getPorductos();
    }

    @GetMapping("/producto/buscarProducto")
    public DtProducto obtenerProducto(@RequestParam("producto_id")Long producto_id){
        return productoService.getPorducto(producto_id);
    }

    @GetMapping("/producto/buscarProductoConStock")
    public DtProducto obtenerProducto(@RequestParam("producto_id")Long producto_id,@RequestParam("sucursal_id")Long sucursal_id){
        return productoService.getPorductoConStocl(producto_id,sucursal_id);
    }

    @DeleteMapping("/producto/eliminar")
    public String eliminarProducto(@RequestParam("producto_id")Long producto_id){
        return productoService.eliminarProducto(producto_id);
    }

    @PutMapping("/producto/modificarProducto")
    public String modificarProducto(@RequestBody DtProductoMod dtpMod){
        return productoService.modrProducto(dtpMod);
    }

/*
    @GetMapping("/producto/sucursalProducto")
    public List<SucursalProducto> productoSucursalProducto(){
        return productoService.getProductosSucursalProducto();
    }

 */

    @GetMapping("/producto/obtenerProductosConStock")
    public List<DtProducto> productosConStock(@RequestParam("sucursal") Long sucursal_id){
        return productoService.getPorductosConStock(sucursal_id);
    }

    @GetMapping("/producto/obtenerProductosCategoriaConStock")
    public List<DtProducto> productosCategoriaConStock(@RequestParam("sucursal") Long sucursal_id,
                                                       @RequestParam("categoria")Long categoria_id){
        return productoService.getPorductosCategoriaConStock(categoria_id,sucursal_id);
    }
/*
    @GetMapping("/producto/buscarProductoConStock")
    public DtProducto buscarProducto(@RequestParam("producto_id") Long producto_id,
                                     @RequestParam("sucursal_id") Long sucursal_id){
        return productoService.buscarProductoConStock(producto_id,sucursal_id);
    }
 */









}