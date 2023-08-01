package com.example.proyecto2023s1g2.service;

import com.example.proyecto2023s1g2.datatype.*;
import com.example.proyecto2023s1g2.domain.*;
import com.example.proyecto2023s1g2.enumeradores.EstadoVenta;
import com.example.proyecto2023s1g2.repository.*;
import com.example.proyecto2023s1g2.domain.Comprador;
import com.example.proyecto2023s1g2.domain.Producto;
import com.example.proyecto2023s1g2.domain.Sucursal;
import com.example.proyecto2023s1g2.domain.Venta;
import com.example.proyecto2023s1g2.repository.CompradorRepo;
import com.example.proyecto2023s1g2.repository.ProductoRepo;
import com.example.proyecto2023s1g2.repository.SucursalRepo;
import com.example.proyecto2023s1g2.repository.VentaRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepo ventaRepo;

    @Autowired
    private ProductoRepo productoRepo;

    @Autowired
    private CompradorRepo compradorRepo;

    @Autowired
    private SucursalRepo sucursalRepo;

    @Autowired
    private DireccionRepo direccionRepo;

    @Autowired
    private JavaMailSender mail;

    @Autowired
    private SucursalProductoRepo sucursalProductoRepo;

    public ResponseEntity<?> altaVenta(Long idSucursal, Long idComprador, Long idDireccion,List<Long> productos) {
        try {
            List<Producto> productosConStockInsuficiente = getProductosConStockInsuficiente(productos,idSucursal);
            Venta venta = new Venta();
            List<Producto> productosCompra=new ArrayList<>();
            // si da true significa que hay suficiente stock
            //para los productos que se quieren comprar
            if (productosConStockInsuficiente.isEmpty()){
                for (Long l : productos) {
                    Producto producto = productoRepo.findById(l).get();
                    productosCompra.add(producto);
                }

                List<DtProducto> dtProductos= comprobarDescuentoProducto(idSucursal, productosCompra);
                double total=0;
                /*for (Producto p:productosCompra){
                        total=total+p.getPrecio();
                }*/
                for (DtProducto dtp:dtProductos){
                    total=total+dtp.getPrecio();
                }
                Direccion direccionRetiro=direccionRepo.findById(idDireccion).get();
                LocalDateTime fecha_actual = LocalDateTime.now().withSecond(0).withNano(0);
                venta.setSucursal(idSucursal);
                venta.setComprador(idComprador);
                venta.setFecha(fecha_actual);
                venta.setTotal(total);
                venta.setLugar_retiro(direccionRetiro);
                venta.setEstado(EstadoVenta.EN_ESPERA);
                for (DtProducto dtp:dtProductos){
                    Producto p=productoRepo.findById(dtp.getId()).get();
                    venta.getProductos().add(p);
                }
                for (DtProducto producto:dtProductos){
                    System.out.println("producto id: "+producto.getId() +" precio: "+producto.getPrecio() );
                }
                System.out.println("----------------");
                Comprador comprador = compradorRepo.findById(idComprador).get();
                System.out.println("Total a pagar sin descuento "+ total);
                total=aplicarDescuentoCompraFinalSiExiste(idSucursal,total);
                System.out.println("Total a pagar con descuento "+ total);
                double totalMod;
                if(comprador.getBilletera() >= total){
                    comprador.setBilletera(comprador.getBilletera()-total);
                    totalMod = 0;
                }else{
                    totalMod = total - comprador.getBilletera();
                    comprador.setBilletera(0);
                }
                comprador.getHistorial_venta().add(venta);
                try {
                    Sucursal sucursal = sucursalRepo.findById(idSucursal).get();
                    sucursal.getVentas().add(venta);
                    ventaRepo.save(venta);
                    compradorRepo.save(comprador);
                    modificarStockCompra(productos,idSucursal);
                    /*List<Long> productosCompraId=new ArrayList<>();
                    for (Producto p:productosCompra){
                        productosCompraId.add(p.getId());
                    }*/
                    //=devolverListDtProducto(productosCompraId);
                    Long id = ventaRepo.getMaxId();
                    DtVenta dtVenta=new DtVenta(id,comprador.getDtComprador(),sucursal.getDtSucursal(),direccionRetiro.getDtDireccion(),fecha_actual,EstadoVenta.EN_ESPERA,totalMod,dtProductos);
                    return ResponseEntity.ok(dtVenta);
                } catch (Exception e) {
                    Map<String, String> respuestaError = new HashMap<>();
                    respuestaError.put("mensaje", "No existe una sucursal con ese id");
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(respuestaError);
                }


            } else {
                List<DtProducto> dtProductos=new ArrayList<>();
                for (Producto p: productosConStockInsuficiente){
                    dtProductos.add(p.getDtProducto());
                }

                Map<String, String> respuestaError = new HashMap<>();
                List<String> nombresProductos = dtProductos.stream()
                        .map(DtProducto::getNombre)
                        .collect(Collectors.toList());

                String mensajeError = "No hay stock suficiente de los siguientes productos: "
                        + String.join(", ", nombresProductos);

                //mensajeError = mensajeError.replace("\n", "\\n");

                respuestaError.put("mensaje", mensajeError);

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(respuestaError);


                /*Map<String, String> respuestaError = new HashMap<>();
                respuestaError.put("mensaje", "no hay stock suficiente de los siguiens prodcutos\n" + dtProductos.toString());
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(respuestaError);*/
            }
        } catch (Exception e) {
            Map<String, String> respuestaError = new HashMap<>();
            respuestaError.put("mensaje", "No existe un usuario con ese id");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(respuestaError);
        }
    }

    private void eliminarDtProductoVenta(List<DtProducto> productos,Long id){
        Iterator<DtProducto> iterador = productos.iterator();
        while (iterador.hasNext()) {
            DtProducto producto = iterador.next();
            if (producto.getId() == id) {
                iterador.remove();
                break;
            }
        }
    }

    private double aplicarDescuentoCompraFinalSiExiste(Long sucursal_id,double total){
        Sucursal sucursal = sucursalRepo.findById(sucursal_id).get();
        if(!sucursal.getPromociones().isEmpty()){
            for(Promocion p:sucursal.getPromociones()){
                if(p instanceof DescuentoCompraFinal){
                    if (p.getFecha_I().isBefore(LocalDateTime.now().withSecond(0).withNano(0))
                            && LocalDateTime.now().withSecond(0).withNano(0).isBefore(p.getFecha_F())
                            || LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_I())
                            || LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_F())){

                        double descuento= total * ((DescuentoCompraFinal) p).getDescuento() / 100;
                        total = total- descuento;
                    }
                }
            }
        }
        return total;
    }

   /*private List<Producto> comprobarDescuentoProducto1(Long idSucursal,List<Producto> productos){
        Sucursal sucursal = sucursalRepo.findById(idSucursal).get();
        //List<Producto> productosRes = new ArrayList<>();
        if(!sucursal.getPromociones().isEmpty()) {
            for (Promocion p : sucursal.getPromociones()) {
                if (p instanceof DescuentoProducto) {
                    if (p.getFecha_I().isBefore(LocalDateTime.now().withSecond(0).withNano(0)) &&
                            LocalDateTime.now().withSecond(0).withNano(0).isBefore(p.getFecha_F()) ||
                            LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_I()) ||
                            LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_F())) {
                        for (Producto pp : ((DescuentoProducto) p).getProductos()) {
                            for (Producto pr : productos) {
                                if (pr.getId().equals(pp.getId())) {
                                    double descuento = pr.getPrecio() * ((DescuentoProducto) p).getPorcentaje() / 100;
                                   // pr.setPrecio(pr.getPrecio() - descuento);
                                   // productosRes.add(pr);
                                } else {
                                    //productosRes.add(pr);
                                }
                            }
                        }
                    //}
                } else if (p instanceof RegaloProducto) {
                    if (p.getFecha_I().isBefore(LocalDateTime.now().withSecond(0).withNano(0))
                            && LocalDateTime.now().withSecond(0).withNano(0).isBefore(p.getFecha_F())
                            || LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_I())
                            || LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_F())){
                        if (((RegaloProducto) p).getStock() > 0) {
                            double precio = 0;
                            Producto producto = productoRepo.findById(((RegaloProducto) p).getId_Prod()).get();
                            producto.setPrecio(precio);
                            productos.add(producto);
                            // TO DO!!!!! cambiar stock de promocion regalo producto.
                        }
                    }
                }
            }
        }
        return productos;
    }*/


    private List<DtProducto> comprobarDescuentoProducto(Long idSucursal,List<Producto> productos){
        Sucursal sucursal = sucursalRepo.findById(idSucursal).get();
        List<DtProducto> productoRes = new ArrayList<>();
        for (Producto p: productos){
            productoRes.add(p.getDtProducto());
        }
        if(!sucursal.getPromociones().isEmpty()) {
            for (Promocion p : sucursal.getPromociones()) {
                if (p instanceof DescuentoProducto) {
                    /*if (p.getFecha_I().isBefore(LocalDateTime.now().withSecond(0).withNano(0)) &&
                            LocalDateTime.now().withSecond(0).withNano(0).isBefore(p.getFecha_F()) ||
                            LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_I()) ||
                            LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_F())) {*/
                    for (Producto pp : ((DescuentoProducto) p).getProductos()) {
                        for (Producto pr : productos) {
                            if (pr.getId().equals(pp.getId())){
                                double descuento = pr.getPrecio() * ((DescuentoProducto) p).getPorcentaje() / 100;
                                //esta funcion elimina el DtProducto que tiene descuento
                                //y se crea en la siguiente linea el mismo DtProducto con el precio modificado
                                eliminarDtProductoVenta(productoRes,pr.getId());
                                DtProducto dtp=new DtProducto(pr.getId(),pr.getNombre(),pr.getDescripcion(),pr.getPrecio()-descuento,0,pr.getImagen());
                                productoRes.add(dtp);
                            }
                        }
                    }
                    //}
                } else if (p instanceof RegaloProducto) {
                    if (p.getFecha_I().isBefore(LocalDateTime.now().withSecond(0).withNano(0))
                            && LocalDateTime.now().withSecond(0).withNano(0).isBefore(p.getFecha_F())
                            || LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_I())
                            || LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_F())) {
                        if (((RegaloProducto) p).getStock() > 0) {
                            Producto producto = productoRepo.findById(((RegaloProducto) p).getId_Prod()).get();
                            DtProducto dtp=new DtProducto(producto.getId(),producto.getNombre(),producto.getDescripcion(),0d,0,producto.getImagen());
                            ((RegaloProducto) p).setStock(((RegaloProducto) p).getStock() - 1);
                            productoRes.add(dtp);
                            // TO DO!!!!! cambiar stock de promocion regalo producto.
                        }
                    }
                }
            }
        }
        return productoRes;
    }

    private List<DtProducto> devolverListDtProducto(List<Long> productos){
        List<DtProducto> listaProductos = new ArrayList<>();
        for (Long l : productos) {
            Producto producto = productoRepo.findById(l).get();
            listaProductos.add(producto.getDtProducto());
        }
        return listaProductos;
    }

    private List<Producto> getProductosConStockInsuficiente(List<Long> ids, Long id_sucursal) {
        /*
        La expresión cantidadIds.getOrDefault(id, 0) devuelve el valor asociado con la clave id
        en el mapa cantidadIds. Si no existe ninguna clave id en el mapa, se devuelve el valor
        predeterminado 0.
        Luego, se agrega 1 al valor devuelto (o al valor predeterminado 0 si no hay ninguna clave id),
        y el resultado se almacena en el mapa cantidadIds asociado con la clave id.

         El map agrupa los ids productos iguales y los suma
         */
        Map<Long, Integer> cantidadIds = new HashMap<>();
        for (Long id : ids) {
            cantidadIds.put(id, cantidadIds.getOrDefault(id, 0) + 1);
        }
        List<SucursalProducto> productosConStock = sucursalProductoRepo.findAll();

        List<Producto> productosConStockInsuficiente = new ArrayList<Producto>();
        /*
            En este for pregunta por cada SucursalProducto si el stock es mayor a la cantidad
            del producto que se quiere comprar en la Sucursal que pasan por parametro.
            En caso de que por ejemplo se quiere comprar 3 Producto con Id 1 pero el stock
            de ese Producto en la Sucursal es 2 se agrega al List.
            Ese List devuelve los Producto si no hay suficiente stock
            para la cantidad que se quiere comprar
         */
        for (SucursalProducto sp : productosConStock) {
            Long id = sp.getProducto().getId();
            Integer cantidad = cantidadIds.get(id);
            if (cantidad!=null && sp.getStock() < cantidad.intValue() && sp.getSucursal().getId().equals(id_sucursal)) {
                productosConStockInsuficiente.add(sp.getProducto());
            }
        }
        return productosConStockInsuficiente;
    }


    private void modificarStockCompra(List<Long> ids, Long id_sucursal) {
        /*
        La expresión cantidadIds.getOrDefault(id, 0) devuelve el valor asociado con la clave id
        en el mapa cantidadIds. Si no existe ninguna clave id en el mapa, se devuelve el valor
        predeterminado 0.
        Luego, se agrega 1 al valor devuelto (o al valor predeterminado 0 si no hay ninguna clave id),
        y el resultado se almacena en el mapa cantidadIds asociado con la clave id.

         El map agrupa los ids productos iguales y los suma
         */
        Map<Long, Integer> cantidadIds = new HashMap<>();
        for (Long id : ids) {
            cantidadIds.put(id, cantidadIds.getOrDefault(id, 0) + 1);
        }

        List<SucursalProducto> productosConStock = sucursalProductoRepo.findAll();

        List<Producto> productosConStockInsuficiente = new ArrayList<Producto>();


        /*
            En este for pregunta por cada SucursalProducto si el stock es mayor a la cantidad
            del producto que se quiere comprar en la Sucursal que pasan por parametro.
            En caso de que por ejemplo se quiere comprar 3 Producto con Id 1 pero el stock
            de ese Producto en la Sucursal es 2 se agrega al List.
            Ese List devuelve los Producto si no hay suficiente stock
            para la cantidad que se quiere comprar
         */
        for (SucursalProducto sp : productosConStock) {
            Long id = sp.getProducto().getId();
            Integer cantidad = cantidadIds.get(id);
            if (cantidad!=null && sp.getSucursal().getId().equals(id_sucursal)) {
                sp.setStock(sp.getStock()-cantidad);
                sucursalProductoRepo.save(sp);
            }
        }
    }


   /* public List<DtVenta> getVentaEspera(Long sucursalId) {
        try {
            List<DtVenta> ventas=new ArrayList<>();
            Sucursal sucursal=sucursalRepo.findById(sucursalId).get();
            for(Venta v: sucursal.getVentas()){
                if(v.getEstado().equals(EstadoVenta.EN_ESPERA)){
                    Comprador comprador = compradorRepo.findById(v.getComprador()).get();
                    DtVenta dtv = new DtVenta(v.getId(),comprador.getDtComprador(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal(),v.getDtProductos());
                    ventas.add(dtv);
                }
            }

            Comparator<DtVenta> comparadorPorFecha = Comparator.comparing(DtVenta::getFecha);
            Collections.sort(ventas, comparadorPorFecha);
            return ventas;

        }catch (Exception e){
            return null;
        }
    }*/


    public List<DtVentaCantidad> getVentaEspera(Long sucursalId) {
        try{
            Sucursal sucursal=sucursalRepo.findById(sucursalId).get();
            List<DtVentaCantidad> ventas=new ArrayList<>();
            for (Venta v: sucursal.getVentas()){
                if(v.getEstado().equals(EstadoVenta.EN_ESPERA)){
                    Comprador comprador=compradorRepo.findById(v.getComprador()).get();
                    List<DtProductoCantidad> dtProductosCantidad=getDtProductosCantidad(v.getProductos());
                    DtVentaCantidad dtVentaCantidad=new DtVentaCantidad(v.getId(),comprador.getDtComprador(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal(),dtProductosCantidad);
                    ventas.add(dtVentaCantidad);
                }

            }
            // Ordenar la lista en orden cronológico descendente
            Collections.sort(ventas, (v1, v2) -> v2.getFecha().compareTo(v1.getFecha()));
            return ventas;

        }catch (Exception e){
            return null;
        }
    }


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




    public String confirmarCompraEnEspera(Long ventaId) {
        Venta venta = ventaRepo.findById(ventaId).get();
        Sucursal sucursal = sucursalRepo.findById(venta.getSucursal()).get();
        if (!venta.getEstado().equals(EstadoVenta.EN_ESPERA)) {
            return "Solo se pueden confirmar ventas que esten en espera";
        } else {
            LocalDate fechaCompra = LocalDate.of(venta.getFecha().getYear(), venta.getFecha().getMonth(), venta.getFecha().getDayOfMonth());
            if (sucursal.getDomicilio().equals(venta.getLugar_retiro())) {
                String ubicacion = sucursal.getDomicilio().getDtDireccion().toString();
                String email = "Su compra hecha el dia :" + fechaCompra + " esta lista para ser retirada en la sucursal " + sucursal.getNombre() + " ubicada :" + "\n" + ubicacion;
                enviarMail(venta.getComprador(), email);
            } else {
                String ubicacion = venta.getLugar_retiro().getDtDireccion().toString();
                String email = "Su compra hecha el dia: " + fechaCompra + " esta lista, sera entregada a la siguiente direccion " + "\n" + ubicacion;
                enviarMail(venta.getComprador(), email);
            }
            venta.setEstado(EstadoVenta.CONFIRMADO);
            ventaRepo.save(venta);
            return "ok";
        }
    }

    /*public List<DtVenta> getVentas(Long sucursal_id){
        Sucursal sucursal=sucursalRepo.findById(sucursal_id).get();
        List<DtVenta> ventas=new ArrayList<>();
        for (Venta v:sucursal.getVentas()){
            Comprador comprador=compradorRepo.findById(v.getComprador()).get();
            DtVenta dtv=new DtVenta(v.getId(),comprador.getDtComprador(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal(),v.getDtProductos());
            ventas.add(dtv);
        }
        return ventas;
    }*/

    private void enviarMail(Long usuario_id,String email_enviar){
        Comprador comprador=compradorRepo.findById(usuario_id).get();
        String email=comprador.getEmail();
        SimpleMailMessage email_mandar = new SimpleMailMessage();
        email_mandar.setTo(email);
        email_mandar.setFrom("proyectogrupo2tecnologo@gmail.com");
        email_mandar.setSubject("Compra Confirmada");
        email_mandar.setText(email_enviar);
        mail.send(email_mandar);
    }


    public String finalizarCompra(Long ventaId) {
        Venta venta=ventaRepo.findById(ventaId).get();
        if(!venta.getEstado().equals(EstadoVenta.CONFIRMADO)){
            return "Solo se pueden finalizar compras Confirmado";
        }else{
            venta.setEstado(EstadoVenta.FINALIZADO);
            ventaRepo.save(venta);
            return "ok";
        }
    }

   /* public List<DtVenta> getVentaConfirmada(Long sucursalId) {
        try{
            List<DtVenta> ventas=new ArrayList<>();
            Sucursal sucursal=sucursalRepo.findById(sucursalId).get();
            for(Venta v: sucursal.getVentas()){
                if(v.getEstado().equals(EstadoVenta.CONFIRMADO)){
                    Comprador comprador=compradorRepo.findById(v.getComprador()).get();
                    DtVenta dtv=new DtVenta(v.getId(),comprador.getDtComprador(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal(),v.getDtProductos());
                    ventas.add(dtv);
                }
            }
            return ventas;
        }catch (Exception e){
            return null;
        }
    }
*/

    public List<DtVentaCantidad> getVentaConfirmada(Long sucursalId) {
        try{
            Sucursal sucursal=sucursalRepo.findById(sucursalId).get();
            List<DtVentaCantidad> ventas=new ArrayList<>();
            for (Venta v: sucursal.getVentas()){
                if(v.getEstado().equals(EstadoVenta.CONFIRMADO)){
                    Comprador comprador=compradorRepo.findById(v.getComprador()).get();
                    List<DtProductoCantidad> dtProductosCantidad=getDtProductosCantidad(v.getProductos());
                    DtVentaCantidad dtVentaCantidad=new DtVentaCantidad(v.getId(),comprador.getDtComprador(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal(),dtProductosCantidad);
                    ventas.add(dtVentaCantidad);
                }

            }
            // Ordenar la lista en orden cronológico descendente
            Collections.sort(ventas, (v1, v2) -> v2.getFecha().compareTo(v1.getFecha()));
            return ventas;

        }catch (Exception e){
            return null;
        }
    }





    public DtSimularVenta simularVenta(Long idSucursal, Long idComprador, Long lugarRetiro, List<Long> productos) {
        List<Producto> productosConStockInsuficiente = getProductosConStockInsuficiente(productos,idSucursal);
        DtSimularVenta simularVenta = new DtSimularVenta();
        List<Producto> productosCompra=new ArrayList<>();
        // si da true significa que hay suficiente stock
        //para los productos que se quieren comprar
        if (productosConStockInsuficiente.isEmpty()){
            for (Long l : productos) {
                Producto producto = productoRepo.findById(l).get();
                productosCompra.add(producto);
            }

            List<DtProductoDescuento> dtProductos = simularDescuentoProducto(idSucursal, productosCompra);
            double total=0;

            for (DtProductoDescuento dtp:dtProductos){
                if(dtp.getPrecio_con_descuento()!=0){
                    total = total + dtp.getPrecio_con_descuento();
                }else{
                    total = total + dtp.getPrecio();
                }
            }
            double total_con_descuento = aplicarDescuentoCompraFinalSiExiste(idSucursal,total);
            simularVenta = new DtSimularVenta(dtProductos, total, total_con_descuento);
        }
        return simularVenta;
    }

    private List<DtProductoDescuento> simularDescuentoProducto(Long idSucursal,List<Producto> productos){
        Sucursal sucursal = sucursalRepo.findById(idSucursal).get();
        List<DtProductoDescuento> productoRes = new ArrayList<>();
        for (Producto p: productos){
            productoRes.add(p.getDtProductoDescuento());
        }
        if(!sucursal.getPromociones().isEmpty()) {
            for (Promocion p : sucursal.getPromociones()) {
                if (p instanceof DescuentoProducto) {
                    /*if (p.getFecha_I().isBefore(LocalDateTime.now().withSecond(0).withNano(0)) &&
                            LocalDateTime.now().withSecond(0).withNano(0).isBefore(p.getFecha_F()) ||
                            LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_I()) ||
                            LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_F())) {*/
                    for (Producto pp : ((DescuentoProducto) p).getProductos()) {
                        for (Producto pr : productos) {
                            if (pr.getId().equals(pp.getId())){
                                double descuento = pr.getPrecio() * ((DescuentoProducto) p).getPorcentaje() / 100;
                                //esta funcion elimina el DtProducto que tiene descuento
                                //y se crea en la siguiente linea el mismo DtProducto con el precio modificado
                                quitarDtProductoVenta(productoRes,pr.getId());
                                List<SucursalProducto> sucursalesProducto = productoRepo.findById(pr.getId()).get().getSucursales();
                                int stock = 0;
                                for(SucursalProducto sp: sucursalesProducto){
                                    if(sp.getSucursal().getId().equals(sucursal.getId())){
                                        stock = sp.getStock();
                                    }
                                }
                                DtProductoDescuento dtpd = new DtProductoDescuento(pr.getId(),pr.getNombre(),pr.getDescripcion(),pr.getPrecio(),stock,pr.getImagen(),descuento,p.getId(),pr.getPrecio()-descuento);
                                productoRes.add(dtpd);
                            }
                        }
                    }
                    //}
                } else if (p instanceof RegaloProducto) {
                    if (p.getFecha_I().isBefore(LocalDateTime.now().withSecond(0).withNano(0))
                            && LocalDateTime.now().withSecond(0).withNano(0).isBefore(p.getFecha_F())
                            || LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_I())
                            || LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_F())) {
                        if (((RegaloProducto) p).getStock() > 0) {
                            Producto producto = productoRepo.findById(((RegaloProducto) p).getId_Prod()).get();
                            DtProductoDescuento dtpd = new DtProductoDescuento(producto.getId(),producto.getNombre(),producto.getDescripcion(),0d,((RegaloProducto) p).getStock(),producto.getImagen(),p.getId());
                            productoRes.add(dtpd);
                        }
                    }
                }
            }
        }
        return productoRes;
    }

    private void quitarDtProductoVenta(List<DtProductoDescuento> productos,Long id){
        Iterator<DtProductoDescuento> iterador = productos.iterator();
        while (iterador.hasNext()) {
            DtProductoDescuento producto = iterador.next();
            if (producto.getId() == id) {
                iterador.remove();
                break;
            }
        }
    }

    public DtPromoTotal promoYTotal(Long idSucursal, Long idProducto, int cantidad) {
        Sucursal sucursal = sucursalRepo.findById(idSucursal).get();
        Producto producto = productoRepo.findById(idProducto).get();
        DtPromoTotal dtpt = new DtPromoTotal();
        Boolean promo = false;
        Boolean stock = false;
        double total_sin = 0;
        double total_con = 0;
        if(!sucursal.getPromociones().isEmpty()) {
            for (Promocion p : sucursal.getPromociones()) {
                if (p instanceof DescuentoProducto) {
                    /*if (p.getFecha_I().isBefore(LocalDateTime.now().withSecond(0).withNano(0)) &&
                            LocalDateTime.now().withSecond(0).withNano(0).isBefore(p.getFecha_F()) ||
                            LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_I()) ||
                            LocalDateTime.now().withSecond(0).withNano(0).equals(p.getFecha_F())) {*/
                    for (Producto pp : ((DescuentoProducto) p).getProductos()) {
                        if (producto.getId().equals(pp.getId())){
                            promo = true;
                            double descuento = producto.getPrecio() * ((DescuentoProducto) p).getPorcentaje() / 100;
                            total_con = (producto.getPrecio() - descuento) * cantidad;
                        }
                    }
                }
            }
        }
        List<SucursalProducto> sucursalesProducto = productoRepo.findById(producto.getId()).get().getSucursales();
        for(SucursalProducto sp: sucursalesProducto){
            if(sp.getSucursal().getId().equals(sucursal.getId())){
                if(sp.getStock() >= cantidad){
                    stock = true;
                }
            }
        }
        total_sin = (producto.getPrecio()) * cantidad;
        if(total_con == 0){
            total_con = total_sin;
        }
        dtpt = new DtPromoTotal(promo, stock, total_con, total_sin );
        return dtpt;
    }
}