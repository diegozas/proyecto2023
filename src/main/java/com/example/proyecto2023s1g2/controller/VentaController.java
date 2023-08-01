package com.example.proyecto2023s1g2.controller;

import com.example.proyecto2023s1g2.datatype.*;
import com.example.proyecto2023s1g2.repository.CompradorRepo;
import com.example.proyecto2023s1g2.repository.UsuarioRepo;
import com.example.proyecto2023s1g2.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VentaController {
    @Autowired
    private VentaService ventaService;
    @Autowired
    private CompradorRepo compradorRepo;
    @Autowired
    private UsuarioRepo usuarioRepo;
/*
    @PostMapping("/venta/alta")
    public ResponseEntity<?> altaVenta(@RequestParam("id_sucursal")Long id_sucursal, @RequestParam("id_comprador")Long id_comprador,@RequestParam("direccion") Long id_direccion, @RequestParam("productos") List<Long> productos){
        return ventaService.altaVenta(id_sucursal, id_comprador, id_direccion,productos);
    }
*/
    @PostMapping("/venta/alta")
    public ResponseEntity<?> altaVenta(@RequestBody DtVentaMod dtV){
        return ventaService.altaVenta(dtV.getId_sucursal(), dtV.getId_comprador(), dtV.getLugarRetiro(), dtV.getProductos());
    }

    @GetMapping ("/venta/listarVentaEnEspera")
    public List<DtVentaCantidad> listarVentaEnEspera(@RequestParam("sucursal_id")Long sucursal_id){
        return ventaService.getVentaEspera(sucursal_id);
    }

    @PostMapping("/venta/confirmarCompra")
    public String confirmarCompra(@RequestParam("compra_id")Long venta_id){
        return ventaService.confirmarCompraEnEspera(venta_id);
    }

    @PostMapping("/venta/finalizarCompra")
    public String finalizarCompra(@RequestParam("compra_id")Long venta_id){
        return ventaService.finalizarCompra(venta_id);
    }
    @GetMapping ("/venta/listarVentaConfirmada")
    public List<DtVentaCantidad> listarVentaConfirmada(@RequestParam("sucursal_id")Long sucursal_id){
        return ventaService.getVentaConfirmada(sucursal_id);
    }

    /*@GetMapping("/venta/sucursal")
    public List<DtVenta> obtenerVentasSucursal(@RequestParam("sucursal_id")Long sucursal_id){
        return ventaService.getVentas(sucursal_id);
    }
    */
    @PostMapping("/venta/simularVenta")
    public DtSimularVenta simularVenta(@RequestBody DtVentaMod dtV){
        return ventaService.simularVenta(dtV.getId_sucursal(), dtV.getId_comprador(), dtV.getLugarRetiro(), dtV.getProductos());
    }

    @GetMapping("/venta/promoYTotal")
    public DtPromoTotal promoYTotal(@RequestParam("id_sucursal")Long id_sucursal,
                                    @RequestParam("id_producto")Long id_producto,
                                    @RequestParam("cantidad")int cantidad){
        //@RequestBody DtVentaPromo dtVP
        //return ventaService.promoYTotal(dtVP.getId_sucursal(), dtVP.getId_producto(), dtVP.getCantidad());
        return ventaService.promoYTotal(id_sucursal, id_producto, cantidad);
    }

}
