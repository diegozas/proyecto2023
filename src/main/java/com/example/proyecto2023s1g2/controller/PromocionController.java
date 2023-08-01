package com.example.proyecto2023s1g2.controller;

import com.example.proyecto2023s1g2.datatype.*;
import com.example.proyecto2023s1g2.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PromocionController {
    @Autowired
    private PromocionService promocionService;

    @PostMapping("/promocion/crearDescuentoCompraFinal")
    public String altaDescuentoCompraFinal(@RequestBody DtCrearDescuentoCompraFinal dtdcf){
       /* if (fecha_inicio.isBefore(LocalDateTime.now().withSecond(0).withNano(0))||fecha_fin.isBefore(LocalDateTime.now().withSecond(0).withNano(0))){
            return "La fechas deben ser mayores al dia actual";
        }*/
       /* else*/ if(dtdcf.getFecha_inicio().withSecond(0).withNano(0).isAfter(dtdcf.getFecha_fin().withSecond(0).withNano(0))){
            return "La fecha inicio debe ser igual o menor a la fecha fin";
        }else {
            return promocionService.altaDescuentoCompraFinal(dtdcf.getTitulo(), dtdcf.getDescripcion(), dtdcf.getFecha_inicio().withSecond(0).withNano(0), dtdcf.getFecha_fin().withSecond(0).withNano(0), dtdcf.getDescuento(), dtdcf.getSucursal_id());
        }
    }

    @PostMapping("/promocion/crearDescuentoProducto")
    public String altaDescuentoProducto(@RequestBody DtCrearDescuentoProducto dtcdp){
        /*if (fecha_inicio.isBefore(LocalDateTime.now().withSecond(0).withNano(0))||fecha_fin.isBefore(LocalDateTime.now().withSecond(0).withNano(0))){
            return "La fechas deben ser mayores al dia actual";
        }
        else*/ if(dtcdp.getFecha_inicio().withSecond(0).withNano(0).isAfter(dtcdp.getFecha_fin().withSecond(0).withNano(0))){
            return "La fecha inicio debe ser igual o menor a la fecha fin";
        }else {
            return promocionService.altaDescuentoProducto(dtcdp.getTitulo(), dtcdp.getDescripcion(), dtcdp.getFecha_inicio().withSecond(0).withNano(0), dtcdp.getFecha_fin().withSecond(0).withNano(0), dtcdp.getDescuento(), dtcdp.getSucursal_id(), dtcdp.getProductos());
        }
    }

    @PostMapping("/promocion/crearRegaloProducto")
    public String altaRegaloProducto(@RequestBody DtCrearRegaloProducto dtcrp){

        /*if (fecha_inicio.isBefore(LocalDateTime.now().withSecond(0).withNano(0))||fecha_fin.isBefore(LocalDateTime.now().withSecond(0).withNano(0))){
            return "La fechas deben ser mayores al dia actual";
        }*/
        /*else*/ if(dtcrp.getFecha_inicio().withSecond(0).withNano(0).isAfter(dtcrp.getFecha_fin().withSecond(0).withNano(0))){
            return "La fecha inicio debe ser igual o menor a la fecha fin";
        }else {
            return promocionService.altaRegaloProducto(dtcrp.getTitulo(), dtcrp.getDescripcion(), dtcrp.getFecha_inicio().withSecond(0).withNano(0), dtcrp.getFecha_fin().withSecond(0).withNano(0), dtcrp.getProducto_id(), dtcrp.getStock(), dtcrp.getSucursal_id());
        }
    }


    @GetMapping("/promocion/listarPromocionSucursalVigente")
    public List<DtPromocion> listarPromocionesVigente(@RequestParam("sucursal_id")Long sucursal_id){
        return promocionService.listarPromocionVigente(sucursal_id);
    }

    @GetMapping("/promocion/listarPromocionSucursal")
    public List<DtPromocion> listarPromociones(@RequestParam("sucursal_id")Long sucursal_id){
        return promocionService.listarPromocion(sucursal_id);
    }

    @GetMapping("/promocion/listarPromocion")
    public List<DtPromocion> listarPromociones(){
        return promocionService.getPromociones();
    }


    @GetMapping("/promocion/obtenerPromocion")
    public DtPromocion getPromocion(@RequestParam Long promocion_id){
        return promocionService.getPromocion(promocion_id);
    }



}
