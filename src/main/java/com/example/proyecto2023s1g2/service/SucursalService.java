package com.example.proyecto2023s1g2.service;

import com.example.proyecto2023s1g2.datatype.*;
import com.example.proyecto2023s1g2.domain.*;
import com.example.proyecto2023s1g2.enumeradores.Departamentos;
import com.example.proyecto2023s1g2.enumeradores.EstadoVenta;
import com.example.proyecto2023s1g2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Service
public class SucursalService {
    @Autowired
    private SucursalRepo sucursalRepo;
    @Autowired
    private DireccionRepo direccionRepo;
    @Autowired
    private SucursalProductoRepo sucursalProductoRepo;
    @Autowired
    private CategoriaRepo categoriaRepo;
    @Autowired
    private ProductoRepo productoRepo;
    @Autowired
    private CompradorRepo compradorRepo;
    @Autowired
    private VentaRepo ventaRepo;
    @Autowired
    private ReclamoRepo reclamoRepo;
    @Autowired
    private JavaMailSender mail;

    public String cargarSucursal() {
        //String calle, String numero, String apartamento, String esquina, String barrio,
        //String localidad, int codigo_postal, String manzana, String solar

        Direccion direccion = new Direccion("8 de octubre", "1234", "105", "20 de febrero", "union", "nose", 5028, "-", "-");
        Direccion direccion1 = new Direccion("Av.Uruguay", "544556", "5", "Colombia y Paraiso", "Solymar Norte", "Solymar", 2098, "73", "14");

        Sucursal sucursal = new Sucursal("The Great Shop",direccion,"12345",Departamentos.MONTEVIDEO);
        Sucursal sucursal1 = new Sucursal("The Great Shop Solymar",direccion1,"58912",Departamentos.CANELONES);

        sucursalRepo.save(sucursal);
        sucursalRepo.save(sucursal1);
        return "ok";
    }


    public List<DtSucursal> getSucursales() {
        List<Sucursal> sucursales = sucursalRepo.findAll();
        List<DtSucursal> dtSucursales=new ArrayList<>();
        for (Sucursal s: sucursales) {
            dtSucursales.add(s.getDtSucursal());
        }
        return dtSucursales;
    }


    public DtSucursal getSucursal(Long id) {
        try {
            return sucursalRepo.findById(id).get().getDtSucursal();
        } catch (Exception e) {
            System.out.println("Estoy en el catch getSucursal");
            return null;
        }
    }

    public void modificarStockProducto(Long idSucursal, Long idProducto, int stock) {
        try {
            Sucursal sucursal = sucursalRepo.findById(idSucursal).get();
            Iterator<SucursalProducto> iterador = sucursal.getProductos().iterator();
            boolean flag = true;
            while (iterador.hasNext() && flag) {
                SucursalProducto elemento = iterador.next();
                if (elemento.getProducto().getId().equals(idProducto)) {
                    elemento.setStock(stock);
                    sucursalProductoRepo.save(elemento);
                    flag = false;
                }
            }
        }catch(Exception e){
            System.out.println("Estoy en el catch modificarStockProducto");
        }
    }

    public List<DtProducto> listarProductosCategoria(Long idSucursal, Long idCategoria){
        try {
            Categoria categoria = categoriaRepo.findById(idCategoria).get();
            List<DtProducto> productos_retornar = new ArrayList<>();

            //ordena los productos por nombre
            List<Producto> productos = new ArrayList<>(categoria.getProductos());
            Comparator<Producto> productosPorNombre = Comparator.comparing(Producto::getNombre);
            Collections.sort(productos, productosPorNombre);

            for (Producto p : productos) {
                if (!p.isEliminado()) {
                    for(SucursalProducto sucursalProducto : p.getSucursales()){
                        if(sucursalProducto.getSucursal().getId().equals(idSucursal)){
                            DtProducto dtProducto=new DtProducto(p.getId(),p.getNombre(),p.getDescripcion(),p.getPrecio(),sucursalProducto.getStock(),p.getImagen());
                            productos_retornar.add(dtProducto);
                        }
                    }
                    //productos_retornar.add(p.getDtProducto());
                }
            }
            return productos_retornar;
        }catch (Exception e){
            System.out.println("Entre al catch");
            return null;
        }
    }


    public String altaSucursal(String nombre, String telefono, Departamentos departamento, String calle, String numero, String apartamento, String esquina, String barrio, String localidad, int codigo_postal, String manzana, String solar) {
        List<SucursalProducto> sucursalesProductos = new ArrayList<>();
        Direccion domicilio = new Direccion(calle, numero, apartamento, esquina, barrio, localidad, codigo_postal, manzana, solar);
        Sucursal sucursal = new Sucursal(nombre, domicilio, telefono, departamento);
        List<Categoria> categorias = categoriaRepo.findAll();
        sucursal.setCategorias(categorias);
        List<Producto> productos = productoRepo.findAll();
        for(Producto p: productos){
            SucursalProducto sucursalProducto = new SucursalProducto(sucursal,p);
            sucursalesProductos.add(sucursalProducto);
        }
        sucursal.setProductos(sucursalesProductos);
        sucursalRepo.save(sucursal);
        return "ok";
    }

    public List<DtDireccion> listarDireccion() {
        List<Direccion> direcciones = direccionRepo.findAll();
        List<DtDireccion> dtDirecciones = new ArrayList<>();
        for (Direccion d:direcciones){
            dtDirecciones.add(d.getDtDireccion());
        }
        return dtDirecciones;
    }

    public List<DtVentaCantidad> getVentas(Long sucursalId) {
        try{
            Sucursal sucursal=sucursalRepo.findById(sucursalId).get();
            List<DtVentaCantidad> ventas=new ArrayList<>();
            for (Venta v: sucursal.getVentas()){
                Comprador comprador=compradorRepo.findById(v.getComprador()).get();
                List<DtProductoCantidad> dtProductosCantidad=getDtProductosCantidad(v.getProductos());
                DtVentaCantidad dtVentaCantidad=new DtVentaCantidad(v.getId(),comprador.getDtComprador(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal(),dtProductosCantidad);
                ventas.add(dtVentaCantidad);
            }
            // Ordenar la lista en orden cronológico descendente
            Collections.sort(ventas, (v1, v2) -> v2.getFecha().compareTo(v1.getFecha()));
            return ventas;

        }catch (Exception e){
            return null;
        }
    }

    /*public List<DtVentaCantidad> getComprasComprador(Long compradorId) {
        try{
            Comprador comprador=compradorRepo.findById(compradorId).get();
            List<DtVentaCantidad> dtVentas=new ArrayList<>();
            for(Venta v:comprador.getHistorial_venta()){
                Sucursal sucursal=sucursalRepo.findById(v.getSucursal()).get();
                List<DtProductoCantidad> dtProductosCantidad=getDtProductosCantidad(v.getProductos());
                DtVentaCantidad dtv=new DtVentaCantidad(v.getId(),sucursal.getDtSucursal(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal());
                for (DtProductoCantidad dtProductoCantidad:dtProductosCantidad){
                    dtv.getProductos().add(dtProductoCantidad);
                }
                dtVentas.add(dtv);
            }
            // Ordenar la lista en orden cronológico descendente
            Collections.sort(dtVentas, (v1, v2) -> v2.getFecha().compareTo(v1.getFecha()));
            return dtVentas;
        }catch (Exception e){
            return null;
        }
    }*/



    private List<DtProductoCantidad> getDtProductosCantidad(List<Producto> listaProductos) {
        Map<Producto, Integer> contadorProductos = new HashMap<>();
        // Contar la cantidad de ocurrencias de cada producto
        for (Producto producto : listaProductos) {
            contadorProductos.put(producto, contadorProductos.getOrDefault(producto, 0) + 1);
        }
        // Crear la lista de DtProductosCantidad con los productos y sus cantidades
        List<DtProductoCantidad> listaProductosCantidad = new ArrayList<>();
        for (Map.Entry<Producto, Integer> entry : contadorProductos.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            DtProductoCantidad dtProductosCantidad = new DtProductoCantidad(producto.getId(),producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getImagen(), cantidad);
            listaProductosCantidad.add(dtProductosCantidad);
        }
        return listaProductosCantidad;
    }



    public List<DtReclamo> getReclamosSinSolucion(Long sucursalId) {
        List<DtReclamo> dtReclamos=new ArrayList<>();
        try{
            Sucursal sucursal=sucursalRepo.findById(sucursalId).get();
            for(Reclamo r:sucursal.getReclamos()){
                if(!r.isSolucionado()){
                    getDtReclamo(dtReclamos, r);
                }
            }
            return dtReclamos;
        }catch (Exception e){
            return null;
        }
    }
    public List<DtReclamo> getReclamos(Long sucursalId) {
        List<DtReclamo> dtReclamos=new ArrayList<>();
        try{
            Sucursal sucursal=sucursalRepo.findById(sucursalId).get();
            for(Reclamo r:sucursal.getReclamos()){
                getDtReclamo(dtReclamos, r);
            }
            return dtReclamos;
        }catch (Exception e){
            return null;
        }
    }

    private void getDtReclamo(List<DtReclamo> dtReclamos, Reclamo r) {
        Venta v=ventaRepo.findById(r.getId_venta()).get();
        Comprador comprador=compradorRepo.findById(v.getComprador()).get();
        DtVenta dtv=new DtVenta(v.getId(),comprador.getDtComprador(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal(),v.getDtProductos());
        DtReclamo dtReclamo=new DtReclamo(r.getId(),r.getTitulo(),r.getDescripcion(),dtv,null,false);
        dtReclamos.add(dtReclamo);
    }

    public DtReclamo getReclamo(Long reclamoId) {
        try{
            Reclamo reclamo=reclamoRepo.findById(reclamoId).get();
            Venta v=ventaRepo.findById(reclamo.getId_venta()).get();
            Comprador comprador=compradorRepo.findById(v.getComprador()).get();
            DtVenta dtv=new DtVenta(v.getId(),comprador.getDtComprador(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal(),v.getDtProductos());
            return new DtReclamo(reclamo.getId(),reclamo.getTitulo(),reclamo.getDescripcion(),dtv,reclamo.getResolucion(),reclamo.isSolucionado());
        }catch (Exception e){
            return null;
        }
    }

    public String solucionarReclamo(DtSolucionReclamo dtSolucionReclamo) {
        try{
            Reclamo reclamo=reclamoRepo.findById(dtSolucionReclamo.getReclamo_id()).get();
            reclamo.setResolucion(dtSolucionReclamo.getSolucion());
            reclamo.setSolucionado(true);
            Venta venta=ventaRepo.findById(reclamo.getId_venta()).get();
            Comprador comprador=compradorRepo.findById(venta.getComprador()).get();
            double total=comprador.getBilletera();
            comprador.setBilletera(total+200);
            reclamoRepo.save(reclamo);
            compradorRepo.save(comprador);
            enviarMail(dtSolucionReclamo.getSolucion(),comprador.getEmail());
            return "ok";
        }catch (Exception e){
            return "error: "+ e.getMessage();
        }
    }

    private void enviarMail(String solucion,String email) {
        SimpleMailMessage email_mandar = new SimpleMailMessage();
        email_mandar.setTo(email);
        email_mandar.setFrom("proyectogrupo2tecnologo@gmail.com");
        email_mandar.setSubject("Atención Del Reclamo");
        email_mandar.setText(solucion + "\n" + "También se le acredito 200$ a su billetera para que pueda usarla en su siguiente compra"+"\n"+"Lamentamos el inconveniente que tuvo");
        mail.send(email_mandar);
    }


    public String modSucursal(DtModSucursal dtModSucursal) {
        try {
            Sucursal sucursal=sucursalRepo.findById(dtModSucursal.getSucursal_id()).get();
            sucursal.setNombre(dtModSucursal.getNombre());
            sucursal.setTelefono(dtModSucursal.getTelefono());
            sucursal.setDepartamento(dtModSucursal.getDepartamento());
            sucursalRepo.save(sucursal);
            return "ok";
        }catch (Exception e){
            return "error";
        }
    }

    public List<DtRecaudacionPorSucursal> rankingRecaudacionPorSucursal() {
        List<Sucursal> sucursales = sucursalRepo.findAll();
        List<DtRecaudacionPorSucursal> recaudaciones = new ArrayList<>();

        for(Sucursal s: sucursales){
            double totalVentas = 0;
            DtRecaudacionPorSucursal dtRPS = null;
            if(!s.getVentas().isEmpty()){
                List<Venta> ventas = s.getVentas();
                for(Venta v: ventas){
                    totalVentas = totalVentas + v.getTotal();
                }
                dtRPS = new DtRecaudacionPorSucursal(s.getNombre(),totalVentas);
            }
            if(dtRPS == null){
                dtRPS = new DtRecaudacionPorSucursal(s.getNombre(),0);
            }
            recaudaciones.add(dtRPS);
        }
        Collections.sort(recaudaciones, Comparator.comparingDouble(DtRecaudacionPorSucursal::getRecaudado).reversed());
        return recaudaciones;
    }

    public List<DtRecaudacionMensual> recaudacion(Long sucursal_id) {
        Sucursal sucursal = sucursalRepo.findById(sucursal_id).get();
        List<DtRecaudacionMensual> dtRM = new ArrayList<>();
        for(int i=1; i<= 12; i++){
            DtRecaudacionMensual RM = new DtRecaudacionMensual(sucursal.getNombre(), Month.of(i),0);
            dtRM.add(RM);
        }
        LocalDateTime fechaActual = LocalDateTime.now();
        double totalVentas = 0;
        if(!sucursal.getVentas().isEmpty()){
            List<Venta> ventas = sucursal.getVentas();
            for(DtRecaudacionMensual dtrm: dtRM){
                for(Venta v: ventas){
                    if(v.getFecha().getMonth().equals(dtrm.getMes())){
                        dtrm.setRecaudado(dtrm.getRecaudado()+v.getTotal());
                    }
                }
            }
        }
        return dtRM;
    }

    public List<DtReclamosPorSucursal> rankingCantidadReclamosPorSucursal() {
        List<Sucursal> sucursales = sucursalRepo.findAll();
        List<DtReclamosPorSucursal> cantidadReclamos = new ArrayList<>();
        int cantidad_reclamos = 0;
        DtReclamosPorSucursal dtRPS = null;
        for(Sucursal s: sucursales){
            cantidad_reclamos = s.getReclamos().size();
            dtRPS = new DtReclamosPorSucursal(s.getNombre(),cantidad_reclamos);
            cantidadReclamos.add(dtRPS);
            }
        Collections.sort(cantidadReclamos, Comparator.comparingInt(DtReclamosPorSucursal::getCantidad_reclamos));
        return cantidadReclamos;
    }

    public int cantidadReclamos(Long sucursal_id) {
        Sucursal sucursal = sucursalRepo.findById(sucursal_id).get();
        int cantidad_reclamos = 0;
        if(!sucursal.getReclamos().isEmpty()){
             cantidad_reclamos = sucursal.getReclamos().size();
        }
        return cantidad_reclamos;
    }

    public List<DtComprasPorSucursal> rankingCantidadComprasPorSucursal() {
        List<Sucursal> sucursales = sucursalRepo.findAll();
        List<DtComprasPorSucursal> cantidadCompras = new ArrayList<>();

        for(Sucursal s: sucursales){
            int cantidad_compras = 0;
            DtComprasPorSucursal dtCPS = null;
            cantidad_compras = s.getVentas().size();
            dtCPS = new DtComprasPorSucursal(s.getNombre(),cantidad_compras);
            cantidadCompras.add(dtCPS);
        }
        Collections.sort(cantidadCompras, Comparator.comparingInt(DtComprasPorSucursal::getCantidad_compras).reversed());
        return cantidadCompras;
    }

    public List<DtProductoVendido> productoMasVendido(Long sucursal_id) {
        List<DtProductoVendido> dtPV = getDtProductoVendidos(sucursal_id);
        Collections.sort(dtPV, Comparator.comparingInt(DtProductoVendido::getCantidad).reversed());
        if(dtPV.stream().count() <= 10){
            return dtPV;
        }else {
            List<DtProductoVendido> resultado = new ArrayList<>();
            for (int i = 0; i <= 9; i++) {
                resultado.add(dtPV.get(i));
            }
            return resultado;
        }
    }

    private List<DtProductoVendido> getDtProductoVendidos(Long sucursal_id) {
        Sucursal sucursal = sucursalRepo.findById(sucursal_id).get();
        DtProductoVendido producto = null;
        List<DtProductoVendido> dtPV = getDtProductoVendidos(sucursal);
        if(!sucursal.getVentas().isEmpty()){
            List<Venta> ventas = sucursal.getVentas();
            for(Venta v: ventas){
                List<Producto> prodVenta = v.getProductos();
                for(Producto p: prodVenta){
                    for(DtProductoVendido dtpv: dtPV){
                        if(dtpv.getProducto().getId().equals(p.getId())){
                            dtpv.setCantidad(dtpv.getCantidad()+1);
                        }
                    }
                }
            }
        }
        return dtPV;
    }

    private List<DtProductoVendido> getDtProductoVendidos(Sucursal sucursal) {
        DtProductoVendido producto;
        List<DtProductoVendido> dtPV = new ArrayList<>();
        List<SucursalProducto> sucursalProductos = sucursal.getProductos();
        for(SucursalProducto sp: sucursalProductos){
            producto = new DtProductoVendido(sp.getProducto().getDtProducto(),0);
            dtPV.add(producto);
        }
        return dtPV;
    }

    public List<DtProductoVendido> productoMenosVendido(Long sucursal_id) {
        List<DtProductoVendido> dtPV = getDtProductoVendidos(sucursal_id);
        List<DtProductoVendido> dtPVfiltrado = new ArrayList<>();
        Collections.sort(dtPV, Comparator.comparingInt(DtProductoVendido::getCantidad));
        for(DtProductoVendido dtpv: dtPV){
            if(dtpv.getCantidad() >= 1){
                dtPVfiltrado.add(dtpv);
            }
        }
        if (dtPVfiltrado.stream().count() <= 10) {
            return dtPVfiltrado;
        } else {
            List<DtProductoVendido> resultado = new ArrayList<>();
            for (int i = 0; i <= 9; i++) {
                resultado.add(dtPVfiltrado.get(i));
            }
            return resultado;
        }
    }

    public List<DtProductoVendido> rankingProductosMasVendidos() {
        List<Sucursal> sucursales = sucursalRepo.findAll();
        List<DtProductoVendido> dtProd = getDtProductoVendidos(sucursales.get(1));
        for(Sucursal s: sucursales){
            List<DtProductoVendido> dtPV = getDtProductoVendidos(s.getId());
            for(DtProductoVendido dtProdV: dtProd){
                for(DtProductoVendido dtpv: dtPV){
                    if(dtpv.getProducto().getId().equals(dtProdV.getProducto().getId())){
                        dtProdV.setCantidad(dtpv.getCantidad()+1);
                    }
                }
            }
        }
        Collections.sort(dtProd, Comparator.comparingInt(DtProductoVendido::getCantidad).reversed());
        if(dtProd.stream().count() <= 10){
            return dtProd;
        }else {
            List<DtProductoVendido> resultado = new ArrayList<>();
            for (int i = 0; i <= 9; i++) {
                resultado.add(dtProd.get(i));
            }
            return resultado;
        }
    }

    public List<DtVentasCanceladas> cantidadVentasCanceladas(Long sucursal_id) {
        Sucursal sucursal = sucursalRepo.findById(sucursal_id).get();
        List<DtVentasCanceladas> dtVC = new ArrayList<>();
        for(int i=1; i<= 12; i++){
            DtVentasCanceladas VC = new DtVentasCanceladas(sucursal.getNombre(), Month.of(i),0);
            dtVC.add(VC);
        }
        LocalDateTime fechaActual = LocalDateTime.now();
        if(!sucursal.getVentas().isEmpty()){
            List<Venta> ventas = sucursal.getVentas();
            for(DtVentasCanceladas dtvc: dtVC){
                for(Venta v: ventas){
                    if(v.getEstado().equals(EstadoVenta.CANCELADO) && v.getFecha().getMonth().equals(dtvc.getMes())){
                        dtvc.setCantidad_canceladas(dtvc.getCantidad_canceladas()+1);
                    }
                }
            }
        }
        return dtVC;
    }
}