package com.example.proyecto2023s1g2.service;

import com.example.proyecto2023s1g2.datatype.*;
import com.example.proyecto2023s1g2.domain.*;
import com.example.proyecto2023s1g2.enumeradores.TipoPromocion;
import com.example.proyecto2023s1g2.repository.ProductoRepo;
import com.example.proyecto2023s1g2.repository.PromocionRepo;
import com.example.proyecto2023s1g2.repository.SucursalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class PromocionService {
    @Autowired
    private PromocionRepo promocionRepo;
    @Autowired
    private ProductoRepo productoRepo;
    @Autowired
    private SucursalRepo sucursalRepo;
    public String altaDescuentoCompraFinal(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int descuento, Long sucursal_id) {
    try{
        Sucursal sucursal=sucursalRepo.findById(sucursal_id).get();
        List<Promocion> promociones=sucursal.getPromociones();
        /*
        List<DescuentoCompraFinal> promocionesDF = new ArrayList<>();

        if(!promociones.isEmpty()){
            for(Promocion p : promociones){
                if(p instanceof DescuentoCompraFinal){
                    promocionesDF.add(p);
                }
            }
        }*/
        if(rangoFechaIgualDF(promociones,fechaInicio,fechaFin)){
            return "Ya existe una promocion Descuento en esas fecha";
        }else {
            DescuentoCompraFinal descuentoCompraFinal=new DescuentoCompraFinal(titulo, descripcion,fechaInicio,fechaFin,descuento);
            sucursal.getPromociones().add(descuentoCompraFinal);
            promocionRepo.save(descuentoCompraFinal);
            return "se creo la promocion";
        }



    }catch (Exception e){
        return "No existe una sucursal con ese id";
    }


    }




    // Verificar si la nueva fecha está en el mismo mes que alguna de las promociones creadas
    /*private boolean mismaFecha(List<Promocion> promociones,LocalDateTime nuevaFecha) {
        boolean mismaFecha = promociones.stream()
                .map(promo -> YearMonth.from(promo.getFecha_I()))
                .anyMatch(yearMonth -> yearMonth.equals(YearMonth.from(nuevaFecha)));

        return mismaFecha;
    }*/
    private boolean rangoFechaIgualDF(List<Promocion> promociones,LocalDateTime fechaInicio,LocalDateTime fechaFin) {
        boolean existe=false;
        boolean rangoEnPromocion = promociones.stream()
                .anyMatch(promo -> {
                    LocalDate inicioPromo = promo.getFecha_I().toLocalDate();
                    LocalDate finPromo = promo.getFecha_F().toLocalDate();
                    YearMonth yearMonthInicioPromo = YearMonth.from(inicioPromo);
                    YearMonth yearMonthFinPromo = YearMonth.from(finPromo);
                    YearMonth yearMonthInicio = YearMonth.from(fechaInicio);
                    YearMonth yearMonthFin = YearMonth.from(fechaFin);
                    return yearMonthInicioPromo.equals(yearMonthInicio) || yearMonthInicioPromo.equals(yearMonthFin)
                            || yearMonthFinPromo.equals(yearMonthInicio) || yearMonthFinPromo.equals(yearMonthFin);
                });

        if(rangoEnPromocion){
            List<Promocion> promocionesEnMes = new ArrayList<>();
            for(Promocion p: promociones) {
                if (p instanceof DescuentoCompraFinal) {
                    if (p.getFecha_I().getMonthValue() == fechaInicio.getMonthValue() && p.getFecha_F().getMonthValue() == fechaFin.getMonthValue()) {
                        promocionesEnMes.add(p);
                    }
                }
            }
            for (Promocion p:promocionesEnMes) {
                    if (fechaInicio.isBefore(p.getFecha_F()) || fechaFin.isBefore(p.getFecha_F()) || fechaInicio.isBefore(p.getFecha_I())) {
                        existe = true;
                    }
            }
        }
        return existe;
    }

    public String altaDescuentoProducto(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, int porcentaje, Long sucursal_id, List<Long> productos){
        try{
            Sucursal sucursal=sucursalRepo.findById(sucursal_id).get();
            List<Promocion> promociones=sucursal.getPromociones();

            if(rangoFechaIgualDP(promociones,fechaInicio,fechaFin, productos)){
                return "Ya existe una promocion Descuento Producto con ese producto en esas fechas";
            }else {
                List<Producto> prod = new ArrayList<>();
                for(Long l : productos){
                    prod.add(productoRepo.findById(l).get());
                }
                DescuentoProducto descuentoProducto = new DescuentoProducto(titulo, descripcion,fechaInicio,fechaFin,porcentaje,prod);
                sucursal.getPromociones().add(descuentoProducto);
                promocionRepo.save(descuentoProducto);
                return "se creo la promocion";
            }
        }catch(Exception e){
            return "No existe una sucursal con ese id";
        }

    }

    private boolean rangoFechaIgualDP(List<Promocion> promociones,LocalDateTime fechaInicio,LocalDateTime fechaFin, List<Long> productos) {
        boolean existe=false;
        boolean rangoEnPromocion = promociones.stream()
                .anyMatch(promo -> {
                    LocalDate inicioPromo = promo.getFecha_I().toLocalDate();
                    LocalDate finPromo = promo.getFecha_F().toLocalDate();
                    YearMonth yearMonthInicioPromo = YearMonth.from(inicioPromo);
                    YearMonth yearMonthFinPromo = YearMonth.from(finPromo);
                    YearMonth yearMonthInicio = YearMonth.from(fechaInicio);
                    YearMonth yearMonthFin = YearMonth.from(fechaFin);
                    return yearMonthInicioPromo.equals(yearMonthInicio) || yearMonthInicioPromo.equals(yearMonthFin)
                            || yearMonthFinPromo.equals(yearMonthInicio) || yearMonthFinPromo.equals(yearMonthFin);
                });

        if(rangoEnPromocion){
            List<Promocion> promocionesEnMes = new ArrayList<>();
            for(Promocion p: promociones) {
                if (p instanceof DescuentoProducto) {
                    if (p.getFecha_I().getMonthValue() == fechaInicio.getMonthValue() && p.getFecha_F().getMonthValue() == fechaFin.getMonthValue()) {
                        promocionesEnMes.add(p);
                    }
                }
            }
            for (Promocion p:promocionesEnMes) {
                if (fechaInicio.isBefore(p.getFecha_F()) || fechaFin.isBefore(p.getFecha_F()) || fechaInicio.isBefore(p.getFecha_I())) {
                    for (Producto prod: ((DescuentoProducto) p).getProductos()) {
                        for (Long l: productos){
                            if (l.equals(prod.getId())){
                                existe = true;
                            }
                        }
                    }
                }
            }
        }
        return existe;
    }

    public String altaRegaloProducto(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, Long producto_id, int stock, Long sucursal_id) {
        try{
            Sucursal sucursal=sucursalRepo.findById(sucursal_id).get();
            List<Promocion> promociones=sucursal.getPromociones();

            if(rangoFechaIgualRP(promociones,fechaInicio,fechaFin, producto_id)){
                return "Ya existe una promocion Regalo Producto con ese producto en esas fechas";
            }else {
                RegaloProducto regaloProducto = new RegaloProducto(titulo, descripcion,fechaInicio,fechaFin,producto_id,stock);
                sucursal.getPromociones().add(regaloProducto);
                promocionRepo.save(regaloProducto);
                return "se creo la promocion";
            }
        }catch(Exception e){
            return "No existe una sucursal con ese id";
        }
    }

    private boolean rangoFechaIgualRP(List<Promocion> promociones,LocalDateTime fechaInicio,LocalDateTime fechaFin, Long producto_id) {
        boolean existe=false;
        boolean rangoEnPromocion = promociones.stream()
                .anyMatch(promo -> {
                    LocalDate inicioPromo = promo.getFecha_I().toLocalDate();
                    LocalDate finPromo = promo.getFecha_F().toLocalDate();
                    YearMonth yearMonthInicioPromo = YearMonth.from(inicioPromo);
                    YearMonth yearMonthFinPromo = YearMonth.from(finPromo);
                    YearMonth yearMonthInicio = YearMonth.from(fechaInicio);
                    YearMonth yearMonthFin = YearMonth.from(fechaFin);
                    return yearMonthInicioPromo.equals(yearMonthInicio) || yearMonthInicioPromo.equals(yearMonthFin)
                            || yearMonthFinPromo.equals(yearMonthInicio) || yearMonthFinPromo.equals(yearMonthFin);
                });

        if(rangoEnPromocion){
            List<Promocion> promocionesEnMes = new ArrayList<>();
            for(Promocion p: promociones) {
                if (p instanceof RegaloProducto) {
                    if (p.getFecha_I().getMonthValue() == fechaInicio.getMonthValue() && p.getFecha_F().getMonthValue() == fechaFin.getMonthValue()) {
                        promocionesEnMes.add(p);
                    }
                }
            }
            for (Promocion p:promocionesEnMes) {
                if (fechaInicio.isBefore(p.getFecha_F()) || fechaFin.isBefore(p.getFecha_F()) || fechaInicio.isBefore(p.getFecha_I())) {
                    if(((RegaloProducto) p).getId_Prod().equals(producto_id)){
                        existe = true;
                    }
                }
            }
        }
        return existe;
    }

    public List<DtPromocion> listarPromocionVigente(Long sucursalId) {
        try{
            List<DtPromocion> promociones=new ArrayList<>();
            Sucursal sucursal=sucursalRepo.findById(sucursalId).get();
            for (Promocion p:sucursal.getPromociones()){
               if(p.getFecha_I().isBefore(LocalDateTime.now().withNano(0).withSecond(0)) && p.getFecha_F().isAfter(LocalDateTime.now().withNano(0).withSecond(0))){
                   getDtPromocion(promociones, p);
               }
            }
            return promociones;
        }catch (Exception e){
            return null;
        }
    }

    private void getDtPromocion(List<DtPromocion> promociones, Promocion p) {
        if(p instanceof RegaloProducto){
            Producto producto=productoRepo.findById(((RegaloProducto) p).getId_Prod()).get();
            DtRegaloProducto dtrp=new DtRegaloProducto(p.getId(),p.getTitulo(),p.getDescripcion(),p.getFecha_I(),p.getFecha_F(),producto.getDtProducto());
            promociones.add(dtrp);
        } else if (p instanceof DescuentoCompraFinal) {
            DtDescuentoCompraFinal dtDescuentoCompraFinal=new DtDescuentoCompraFinal(p.getId(),p.getTitulo(),p.getDescripcion(),p.getFecha_I(),p.getFecha_F(),((DescuentoCompraFinal) p).getDescuento());
            promociones.add(dtDescuentoCompraFinal);
        } else if (p instanceof DescuentoProducto){
            List<DtProducto> productos=new ArrayList<>();
            for (Producto producto:((DescuentoProducto) p).getProductos()){
                productos.add(producto.getDtProducto());
            }
            DtDescuentoProducto dtDescuentoProducto=new DtDescuentoProducto(p.getId(),p.getTitulo(),p.getDescripcion(),p.getFecha_I(),p.getFecha_F(),((DescuentoProducto) p).getPorcentaje(),productos);
            promociones.add(dtDescuentoProducto);
        }
    }

    public List<DtPromocion> getPromociones() {
        List<DtPromocion> dtpromociones=new ArrayList<>();
        List<Promocion> promociones=promocionRepo.findAll();
        for (Promocion p:promociones){
            getDtPromocion(dtpromociones, p);
        }
        return dtpromociones;
    }

    public List<DtPromocion> listarPromocion(Long sucursalId){
        try{
            List<DtPromocion> promociones=new ArrayList<>();
            Sucursal sucursal=sucursalRepo.findById(sucursalId).get();
            for (Promocion p:sucursal.getPromociones()){
                    getDtPromocion(promociones, p);
            }
            return promociones;
        }catch (Exception e){
            return null;
        }
    }

    public DtPromocion getPromocion(Long promocionId) {
        try{
            Promocion promocion=promocionRepo.findById(promocionId).get();
            return getDtPromocionId(promocion);
        }catch (Exception e){
            return null;
        }
    }

    private DtPromocion getDtPromocionId(Promocion p) {
        if(p instanceof RegaloProducto){
            Producto producto=productoRepo.findById(((RegaloProducto) p).getId_Prod()).get();
            DtRegaloProducto dtrp=new DtRegaloProducto(p.getId(),p.getTitulo(),p.getDescripcion(),p.getFecha_I(),p.getFecha_F(),producto.getDtProducto());
            return dtrp;
        } else if (p instanceof DescuentoCompraFinal) {
            DtDescuentoCompraFinal dtDescuentoCompraFinal=new DtDescuentoCompraFinal(p.getId(),p.getTitulo(),p.getDescripcion(),p.getFecha_I(),p.getFecha_F(),((DescuentoCompraFinal) p).getDescuento());
            return dtDescuentoCompraFinal;
        } else if (p instanceof DescuentoProducto){
            List<DtProducto> productos=new ArrayList<>();
            for (Producto producto:((DescuentoProducto) p).getProductos()){
                productos.add(producto.getDtProducto());
            }
            DtDescuentoProducto dtDescuentoProducto=new DtDescuentoProducto(p.getId(),p.getTitulo(),p.getDescripcion(),p.getFecha_I(),p.getFecha_F(),((DescuentoProducto) p).getPorcentaje(),productos);
            return dtDescuentoProducto;
        }

        return null;

    }

}
