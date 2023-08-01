package com.example.proyecto2023s1g2.controller;

import com.example.proyecto2023s1g2.datatype.*;
import com.example.proyecto2023s1g2.domain.Direccion;
import com.example.proyecto2023s1g2.domain.Sucursal;
import com.example.proyecto2023s1g2.enumeradores.Departamentos;
import com.example.proyecto2023s1g2.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;

    @PostMapping("/sucursal/alta")
    public String altaSucursal(@RequestBody DtAltaSucursal dtS){
        DtDireccion dtD = dtS.getDomicilio();
        return sucursalService.altaSucursal(dtS.getNombre(), dtS.getTelefono(), dtS.getDepartamento(), dtD.getCalle(), dtD.getNumero(), dtD.getApartamento(), dtD.getEsquina(), dtD.getBarrio(), dtD.getLocalidad(), dtD.getCodigo_postal(), dtD.getManzana(), dtD.getSolar());
    }

    @PutMapping("/sucursal/modificarSucursal")
    public String modificarSucursal(@RequestBody DtModSucursal dtModSucursal){
        return sucursalService.modSucursal(dtModSucursal);
    }

    @GetMapping("/sucursal/obtener")
    public DtSucursal getSucursal(@RequestParam("id")Long id){
        return sucursalService.getSucursal(id);
    }

    @GetMapping("/sucursal/listar")
    public List<DtSucursal> getSucursales(){
        return sucursalService.getSucursales();
    }
/*
    @PutMapping("/sucursal/modificarStockProducto")
    public String modificarStockProducto(@RequestParam("id_sucursal")Long id_sucursal, @RequestParam("id_producto")Long id_producto, @RequestParam("stock")int stock){
        sucursalService.modificarStockProducto(id_sucursal, id_producto, stock);
        return "ok";
    }

 */
    @PutMapping("/sucursal/modificarStockProducto")
    public String modificarStockProducto(@RequestBody DtSucursalProducto dtSP){
        sucursalService.modificarStockProducto(dtSP.getSucursal_id(), dtSP.getProducto_id(), dtSP.getStock());
        return "ok";
    }

    @GetMapping("/sucursal/listarProductosCategoria")
    public List<DtProducto> listarProductosCategoria(@RequestParam("id_sucursal")Long id_sucursal, @RequestParam("id_categoria")Long id_categoria){
        return sucursalService.listarProductosCategoria(id_sucursal, id_categoria);
    }

    /*@PostMapping("/sucursal/crearSucursal")
    public String crearSucursal(@RequestParam("")Long id_sucursal,
                                @RequestParam("id_producto")Long id_producto,
                                @RequestParam("stock")int stock){
        return sucursalService.altaSucursal(id_sucursal, id_producto, stock);

    }
    */

    @GetMapping("/sucursal/listarVentas")
    public List<DtVentaCantidad> listarVentas(@RequestParam("sucursal_id")Long sucursal_id){
       return sucursalService.getVentas(sucursal_id);
    }

    @GetMapping("/sucursal/listarReclamosSinSolucion")
    public List<DtReclamo> listarReclamosSinSolucion(@RequestParam("sucursal_id")Long sucursal_id){
        return sucursalService.getReclamosSinSolucion(sucursal_id);
    }
    @GetMapping("/sucursal/listarReclamos")
    public List<DtReclamo> listarReclamos(@RequestParam("sucursal_id")Long sucursal_id){
        return sucursalService.getReclamos(sucursal_id);
    }

    @GetMapping("/sucursal/obtenerReclamo")
    public DtReclamo obtenerReclamo(@RequestParam("reclamo_id")Long reclamo_id){
        return sucursalService.getReclamo(reclamo_id);
    }

    @PostMapping("/sucursal/atencionReclamo")
    public String atenderReclamo(@RequestBody DtSolucionReclamo dtSolucionReclamo){
        return sucursalService.solucionarReclamo(dtSolucionReclamo);
    }

    @GetMapping("/direccion/listar")
    public List<DtDireccion> listarDirecciones(){
        return sucursalService.listarDireccion();
    }

    @GetMapping("/sucursal/rankingRecaudacionPorSucursal")
    public List<DtRecaudacionPorSucursal> rankingRecaudacionPorSucursal(){
        return sucursalService.rankingRecaudacionPorSucursal();
    }

    @GetMapping("/sucursal/recaudacion")
    public List<DtRecaudacionMensual> recaudacion(@RequestParam("sucursal_id")Long sucursal_id){
        return sucursalService.recaudacion(sucursal_id);
    }

    @GetMapping("/sucursal/rankingCantidadReclamosPorSucursal")
    public List<DtReclamosPorSucursal> rankingCantidadReclamosPorSucursal(){
        return sucursalService.rankingCantidadReclamosPorSucursal();
    }

    @GetMapping("/sucursal/cantidadReclamos")
    public int cantidadReclamos(@RequestParam("sucursal_id")Long sucursal_id){
        return sucursalService.cantidadReclamos(sucursal_id);
    }

    @GetMapping("/sucursal/rankingCantidadComprasPorSucursal")
    public List<DtComprasPorSucursal> rankingCantidadComprasPorSucursal(){
        return sucursalService.rankingCantidadComprasPorSucursal();
    }

    @GetMapping("/sucursal/productosMasVendidos")
    public List<DtProductoVendido> productoMasVendido(@RequestParam("sucursal_id")Long sucursal_id){
        return sucursalService.productoMasVendido(sucursal_id);
    }

    @GetMapping("/sucursal/productosMenosVendidos")
    public List<DtProductoVendido> productoMenosVendido(@RequestParam("sucursal_id")Long sucursal_id){
        return sucursalService.productoMenosVendido(sucursal_id);
    }

    @GetMapping("/sucursal/rankingProductosMasVendidos")
    public List<DtProductoVendido> rankingProductosMasVendidos(){
        return sucursalService.rankingProductosMasVendidos();
    }

    @GetMapping("/sucursal/cantidadVentasCanceladas")
    public List<DtVentasCanceladas> cantidadVentasCanceladas(@RequestParam("sucursal_id")Long sucursal_id){
        return sucursalService.cantidadVentasCanceladas(sucursal_id);
    }
}
