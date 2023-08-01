package com.example.proyecto2023s1g2.service;

import com.example.proyecto2023s1g2.datatype.*;
import com.example.proyecto2023s1g2.domain.*;
import com.example.proyecto2023s1g2.enumeradores.EstadoVenta;
import com.example.proyecto2023s1g2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.dialect.LockClause;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private CompradorRepo compradorRepo;
    @Autowired
    private ReclamoRepo reclamoRepo;
    @Autowired
    private UsuarioSucursalRepo usuarioSucursalRepo;
    @Autowired
    private DireccionRepo direccionRepo;
    @Autowired
    private VentaRepo ventaRepo;
    @Autowired
    private SucursalRepo sucursalRepo;
    @Autowired
    private SucursalProductoRepo sucursalProductoRepo;
    @Autowired
    private TokenRepo tokenRepo;
    @Autowired
    private JavaMailSender mail;
    private PasswordHasher passwordHasher;






    public Usuario getUsuario(String email) {
        try {
            return usuarioRepo.findByEmail(email).get();
        }catch (Exception e){
            return null;
        }
    }

    public String enviarMail(String email) {
        Optional<Usuario> persona=usuarioRepo.findByEmail(email);
        if(!persona.isEmpty()){
            Usuario enviar_mail=persona.get();

            // Generar un token único para el usuario
            String token = UUID.randomUUID().toString();

            // Crear la entidad Token y guardarla en la base de datos
            Token tokenEntity = new Token();
            tokenEntity.setToken(token);
            tokenEntity.setFechaCreacion(new Date());
            tokenEntity.setUsuario(enviar_mail);
            tokenRepo.save(tokenEntity);

            // Crear el enlace con el token
            //String enlace = "https://mi-sitio-web.com/recuperar-contrasena/cambiar-contrasena?token=" + token;
            //String ejemplo="\"link para recuperar password: https://example.com/recoverPassword?token=abcdef12345\"";

            //String en="http://localhost:3000/recuperar-cuenta/"+ token;
            String en="http://52.67.247.230/recuperar-cuenta/" +token;
            // Enviar el correo electrónico al usuario con el enlace
            SimpleMailMessage email_mandar = new SimpleMailMessage();
            email_mandar.setTo(email);
            email_mandar.setFrom("proyectogrupo2tecnologo@gmail.com");
            email_mandar.setSubject("Recuperar contraseña");
            email_mandar.setText("Ingrese al siguiente link para recuperar su contraseña" + "\n" + en);
            mail.send(email_mandar);
            return "ok";
        }else {
            return "no existe un usuario con ese email: " + email;
        }

    }
    public String enviarMailPorApi(String email) {
        Optional<Usuario> persona=usuarioRepo.findByEmail(email);
        if(!persona.isEmpty()){
            Usuario enviar_mail=persona.get();

            // Generar un token único para el usuario
            String token = UUID.randomUUID().toString();

            // Crear la entidad Token y guardarla en la base de datos
            Token tokenEntity = new Token();
            tokenEntity.setToken(token);
            tokenEntity.setFechaCreacion(new Date());
            tokenEntity.setUsuario(enviar_mail);
            tokenRepo.save(tokenEntity);

            // Crear el enlace con el token
            //String enlace = "https://mi-sitio-web.com/recuperar-contrasena/cambiar-contrasena?token=" + token;
            //String ejemplo="\"link para recuperar password: https://example.com/recoverPassword?token=abcdef12345\"";

            String en="http://52.67.247.230/recuperar-cuenta/" + token;
            // Enviar el correo electrónico al usuario con el enlace
            SimpleMailMessage email_mandar = new SimpleMailMessage();
            email_mandar.setTo(email);
            email_mandar.setFrom("proyectogrupo2tecnologo@gmail.com");
            email_mandar.setSubject("Recuperar contraseña");
            email_mandar.setText("Ingrese al siguiente link para recuperar su contraseña" + "\n" + en);
            mail.send(email_mandar);
            return "ok";
        }else {
            return "no existe un usuario con ese email: " + email;
        }

    }

    public String actualizarPassword(String token, String password) throws NoSuchAlgorithmException {

        // Buscar el token en la base de datos
        Token tokenEntity = tokenRepo.findByToken(token);

        // Verificar si el token es válido y si no ha expirado
        if (tokenEntity == null || tokenEntity.getFechaCreacion().before(new Date(new Date().getTime() - (24 * 60 * 60 * 1000)))) {
            return "El token no es válido o ha expirado";
        }

        Usuario persona = tokenEntity.getUsuario();
        // Cambiar la contraseña del usuario y marcarla como cambiada
        passwordHasher=new PasswordHasher();
        String password_nueva=passwordHasher.hashPassword(password);
        persona.setPassword(password_nueva);
        usuarioRepo.save(persona);

        // Eliminar el token de la base de datos
        tokenEntity.setUsuario(null);
        tokenRepo.deleteById(tokenEntity.getId());

        return "se cambio la contraseña";
    }


    public ResponseEntity<?> iniciarSesion(String email, String password){
       passwordHasher=new PasswordHasher();
        try {
            Usuario iniciarSesion = usuarioRepo.findByEmail(email).get();
            if(!passwordHasher.verifyPassword(password,iniciarSesion.getPassword())){
                Map<String, String> respuestaError = new HashMap<>();
                respuestaError.put("mensaje", "Contraseña incorrecta");
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(respuestaError);
           }else{
                ResponseEntity<?> REQUEST = getResponseEntity(iniciarSesion);
                if (REQUEST != null) return REQUEST;
            }
       }catch (Exception e){
            Map<String, String> respuestaError = new HashMap<>();
            respuestaError.put("mensaje", "No existe un usuario con ese mail");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(respuestaError);
       }
        return ResponseEntity.ok("no entre a ningun if");
    }

    private ResponseEntity<?> getResponseEntity(Usuario iniciarSesion) {
        if(iniciarSesion instanceof Comprador){
           if(((Comprador) iniciarSesion).isBloqueado()){
               Map<String, String> respuestaError = new HashMap<>();
               respuestaError.put("mensaje", "Usuario bloqueado");
               return ResponseEntity
                       .status(HttpStatus.BAD_REQUEST)
                       .body(respuestaError);
           }else if(((Comprador) iniciarSesion).isEliminado()){
               Map<String, String> respuestaError = new HashMap<>();
               respuestaError.put("mensaje", "Usuario eliminado");
               return ResponseEntity
                       .status(HttpStatus.BAD_REQUEST)
                       .body(respuestaError);
            }else{
               return ResponseEntity.ok(((Comprador) iniciarSesion).getDtComprador());
           }
        }else if (iniciarSesion instanceof UsuarioSucursal){
            if(((UsuarioSucursal) iniciarSesion).isBloqueado()){
                Map<String, String> respuestaError = new HashMap<>();
                respuestaError.put("mensaje", "Usuario bloqueado");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(respuestaError);
            }else{
                try {
                    Sucursal sucursal=sucursalRepo.findByUsuarioSucursalId(iniciarSesion.getId());
                    DtSucursal dtSucursal=sucursal.getDtSucursal();
                    DtUsuarioSucursal dtUsuarioSucursal=new DtUsuarioSucursal(iniciarSesion.getId(),((UsuarioSucursal) iniciarSesion).getNombre(),((UsuarioSucursal) iniciarSesion).getApellido(),((UsuarioSucursal) iniciarSesion).getEdad(),((UsuarioSucursal) iniciarSesion).getFecha_nac(),((UsuarioSucursal) iniciarSesion).isBloqueado(),dtSucursal);
                    //return ResponseEntity.ok(((UsuarioSucursal) iniciarSesion).getDtUsuarioSucursal());
                    return ResponseEntity.ok(dtUsuarioSucursal);
                }catch (Exception e){
                    Map<String, String> respuestaError = new HashMap<>();
                    respuestaError.put("mensaje", "El usuario no esta en ninguna sucursal");
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(respuestaError);
                }

            }
        }else if (iniciarSesion instanceof Administrador){
            return ResponseEntity.ok(((Administrador) iniciarSesion).getDtAdministrador());
        }
        return null;
    }


    public boolean verificarPassword(String email,String password) throws NoSuchAlgorithmException {
        passwordHasher=new PasswordHasher();
        return passwordHasher.verifyPassword(password,usuarioRepo.findByEmail(email).get().getPassword());
    }


    public String altaComprador(String email, String password, String nombre, String apellido, String telefono, String fechaNac, String foto) throws NoSuchAlgorithmException {
        Optional<Usuario> usuario = usuarioRepo.findByEmail(email);
        if(usuario.isPresent()){
            return "Ya existe un usuario con ese email";
        }else{
            LocalDate fechaNacimiento = LocalDate.parse(fechaNac);
            Comprador comprador = new Comprador(nombre,apellido,email,password,fechaNacimiento,telefono,foto);
            passwordHasher = new PasswordHasher();
            comprador.setPassword(passwordHasher.hashPassword(password));
            usuarioRepo.save(comprador);
            return "se creo el nuevo usuario correctamente";
        }
    }

    public List<DtUsuario> listarUsuarios() {
        List<Usuario> usuarios=usuarioRepo.findAll();
        List<DtUsuario> dtUsuarios=new ArrayList<>();

        for (Usuario u:usuarios){
            if(u instanceof Comprador){
                dtUsuarios.add(((Comprador) u).getDtComprador());
            } else if (u instanceof UsuarioSucursal) {
                dtUsuarios.add(((UsuarioSucursal) u).getDtUsuarioSucursal());
            }else if(u instanceof Administrador){
                dtUsuarios.add(((Administrador) u).getDtAdministrador());
            }

        }
        return dtUsuarios;
    }

    public String altaUsuarioSucursal(String email, String password, String nombre, String apellido, String fechaNac,Long sucursal_id) throws NoSuchAlgorithmException {
        Optional<Usuario> usuario=usuarioRepo.findByEmail(email);
        if(usuario.isPresent()){
            return "Ya existe un usuario con ese email";
        }else{
            LocalDate fechaNacimiento = LocalDate.parse(fechaNac);
            passwordHasher = new PasswordHasher();
            String pass = passwordHasher.hashPassword(password);
            UsuarioSucursal usuarioSucursal = new UsuarioSucursal(email,pass,nombre,apellido,fechaNacimiento);
            try {
                Sucursal sucursal = sucursalRepo.findById(sucursal_id).get();
                sucursal.getUsuariosSucursal().add(usuarioSucursal);
                usuarioSucursal.setSucursal(sucursal);
                usuarioRepo.save(usuarioSucursal);
                sucursalRepo.save(sucursal);
                return "se creo el nuevo usuario correctamente";
            }catch (Exception e){
                return "No existe una sucursal con ese id";
            }

        }

    }

    public String altaDireccionComprador(String calle, String numero,String apartamento, String esquina, String barrio, String localidad, String codigoPostal, String manzana, String solar, Long compradorId) {
        try{
            Usuario usuario = usuarioRepo.findById(compradorId).get();
           try {
               int codigo_postal = Integer.parseInt(codigoPostal);
               Direccion direccion = new Direccion(calle,numero,apartamento,esquina,barrio,localidad,codigo_postal,manzana,solar);
               if(usuario instanceof Comprador){
                   ((Comprador) usuario).getDirecciones().add(direccion);
                   usuarioRepo.save(usuario);
               }
           }catch (Exception e){
               return "El codigo postal debe ser un numero valido";
           }
            return "ok";
        }catch (Exception e){
            return "No existe un usuario con ese id";
        }
    }


    public DtComprador obtenerUsuarioComprador(Long id_usuario) {
        try{
            Comprador comprador = (Comprador) usuarioRepo.findById(id_usuario).get();
            return comprador.getDtComprador();
        }catch (Exception e){
            return null;
        }

    }

    public DtUsuarioSucursal obtenerUsuarioSucursal(Long id_usuario) {
        try{
            UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuarioRepo.findById(id_usuario).get();
            return usuarioSucursal.getDtUsuarioSucursalId();
        }catch(Exception e){
            return null;
        }

    }
    public List<DtVenta> getVentasEnEsperaComprador(Long compradorId) {
        List<DtVenta> ventas_comprador=new ArrayList<>();
        try{
            Comprador comprador=compradorRepo.findById(compradorId).get();
            for (Venta v:comprador.getHistorial_venta()){
                if(v.getEstado().equals(EstadoVenta.EN_ESPERA)){
                    Sucursal sucursal=sucursalRepo.findById(v.getSucursal()).get();
                    DtVenta dtv=new DtVenta(v.getId(),sucursal.getDtSucursal(),v.getLugar_retiro().getDtDireccion(),v.getFecha(),v.getEstado(),v.getTotal(),v.getDtProductos());
                    ventas_comprador.add(dtv);
                }
            }
            return ventas_comprador;
        }catch (Exception e){
            return null;
        }

    }

    public String cancelarCompra(Long compraId) {
        try {
            Venta venta = ventaRepo.findById(compraId).get();
            Comprador comprador = compradorRepo.findById(venta.getComprador()).get();
            if(venta.getEstado().equals(EstadoVenta.EN_ESPERA)){
                venta.setEstado(EstadoVenta.CANCELADO);
                double total=comprador.getBilletera();
                comprador.setBilletera(total+venta.getTotal());
                modificarStockCompra(venta.getProductos(),venta.getSucursal());
                compradorRepo.save(comprador);
                ventaRepo.save(venta);
                return "ok";
            }else {
                return "solo se pueden cancelar compras que esten EN_ESPERA";
            }
        }catch (Exception e){
            return "No existe un usuario comprador con ese id";
        }
    }

    private void modificarStockCompra(List<Producto> productos, Long id_sucursal) {
        Map<Long, Integer> cantidadIds = new HashMap<>();
        for (Producto p : productos) {
            cantidadIds.put(p.getId(), cantidadIds.getOrDefault(p.getId(), 0) + 1);
        }
        List<SucursalProducto> productosConStock = sucursalProductoRepo.findAll();

        List<Producto> productosConStockInsuficiente = new ArrayList<Producto>();

        for (SucursalProducto sp : productosConStock) {
            Long id = sp.getProducto().getId();
            Integer cantidad = cantidadIds.get(id);
            if (cantidad!=null && sp.getSucursal().getId().equals(id_sucursal)) {
                sp.setStock(sp.getStock()+cantidad);
                sucursalProductoRepo.save(sp);
            }
        }
    }

    public String crearDireccionComprador(DtCrearDireccionComprador dtCDC){
        try{
            String calle=dtCDC.getDomicilio().getCalle();
            String numero=dtCDC.getDomicilio().getNumero();
            String apartamento=dtCDC.getDomicilio().getApartamento();
            String esquina=dtCDC.getDomicilio().getEsquina();
            String barrio=dtCDC.getDomicilio().getBarrio();
            String localidad=dtCDC.getDomicilio().getLocalidad();
            int codigo_postal=dtCDC.getDomicilio().getCodigo_postal();
            String manzana=dtCDC.getDomicilio().getManzana();
            String solar=dtCDC.getDomicilio().getSolar();

            Direccion domicilio=new Direccion(calle,numero,apartamento,esquina,barrio,localidad,codigo_postal,manzana,solar);
            direccionRepo.save(domicilio);
            Comprador comprador = compradorRepo.findById(dtCDC.getId()).get();
            comprador.getDirecciones().add(domicilio);
            compradorRepo.save(comprador);
            direccionRepo.save(domicilio);
            return "ok";
        }catch (Exception e){
            return "no existe un comprador con ese id";
        }
    }

    public String modificarDomicilio(DtDireccion direccion) {
        try {
            Direccion direccionMod=direccionRepo.findById(direccion.getId()).get();
            direccionMod.setCalle(direccion.getCalle());
            direccionMod.setNumero(direccion.getNumero());
            direccionMod.setApartamento(direccion.getApartamento());
            direccionMod.setEsquina(direccion.getEsquina());
            direccionMod.setBarrio(direccion.getBarrio());
            direccionMod.setLocalidad(direccion.getLocalidad());
            direccionMod.setCodigo_postal(direccion.getCodigo_postal());
            direccionMod.setManzana(direccion.getManzana());
            direccionMod.setSolar(direccion.getSolar());
            direccionRepo.save(direccionMod);
            return "ok";
        }catch (Exception e){
            return "No existe una direccion con ese id";
        }

    }

    public String eliminarComprador(Long compradorId) {
        try{
            Comprador comprador=compradorRepo.findById(compradorId).get();
            comprador.setEliminado(true);
            compradorRepo.save(comprador);
            return "ok";

        }catch (Exception e){
            return "No existe un usuario con ese id";
        }
    }

    public String activarComprador(Long compradorId) {
        try{
            Comprador comprador=compradorRepo.findById(compradorId).get();
            comprador.setEliminado(false);
            compradorRepo.save(comprador);
            return "ok";

        }catch (Exception e){
            return "No existe un usuario con ese id";
        }
    }

    public String bloquearComprador(Long compradorId) {
        try{
            Comprador comprador=compradorRepo.findById(compradorId).get();
            if(comprador.isBloqueado()){
                return "Este usuario ya esta bloqueado";
            }else {
                comprador.setBloqueado(true);
                compradorRepo.save(comprador);
                return "ok";
            }
        }catch (Exception e){
            return "No existe un usuario con ese id";
        }
    }

    public String desbloquearComprador(Long compradorId) {
        try{
            Comprador comprador=compradorRepo.findById(compradorId).get();
            if(!comprador.isBloqueado()){
                return "El usuario no esta bloqueado";
            }else {
                comprador.setBloqueado(false);
                compradorRepo.save(comprador);
                return "ok";
            }
        }catch (Exception e){
            return "No existe un usuario con ese id";
        }
    }

    public String eliminarDireccion(Long direccionId) {
        try{
            Direccion direccion=direccionRepo.findById(direccionId).get();
            direccion.setEliminada(true);
            direccionRepo.save(direccion);
            return "ok";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "No existe una direccion con ese id";
        }
    }



    public List<DtDireccion> getDomiciliosComprador(Long compradorId) {
        try {
            Comprador comprador = compradorRepo.findById(compradorId).get();
            List<DtDireccion> dtDirecciones = new ArrayList<>();
            for(Direccion d:comprador.getDirecciones()){
                if(!d.isEliminada()){
                    dtDirecciones.add(d.getDtDireccion());
                }
            }
            return dtDirecciones;
        }catch (Exception e){
            return null;
        }
    }

    public String modComprador(DtCompradorMod dtcMod) {
        try{
            Comprador comprador=compradorRepo.findById(dtcMod.getId()).get();
            comprador.setApellido(dtcMod.getApellido());
            comprador.setNombre(dtcMod.getNombre());
            comprador.setTelefono(dtcMod.getTelefono());
            comprador.setFecha_nac(dtcMod.getFecha_nac());
            comprador.setFoto(dtcMod.getFoto());
            compradorRepo.save(comprador);
            return "ok";
        }catch (Exception e){
            return "No existe un comprador con ese id";
        }



    }

    public String modUsuarioSucursal(DtUsuarioSucursalMod dtusMod) {
        try{
            UsuarioSucursal usuarioSucursal=usuarioSucursalRepo.findById(dtusMod.getId()).get();
            usuarioSucursal.setApellido(dtusMod.getApellido());
            usuarioSucursal.setNombre(dtusMod.getNombre());
            usuarioSucursal.setFecha_nac(dtusMod.getFecha_nac());
            usuarioSucursalRepo.save(usuarioSucursal);
            return "ok";
        }catch (Exception e){
            return "No existe un usuario sucursal con ese id";
        }

    }







    public DtUsuario buscarUsuario(Long usuarioId) {
        try {
            Usuario usuario=usuarioRepo.findById(usuarioId).get();
            if(usuario instanceof UsuarioSucursal){
                return ((UsuarioSucursal) usuario).getDtUsuarioSucursalId();
            }else if(usuario instanceof Comprador){
                return ((Comprador) usuario).getDtComprador();
            } else if (usuario instanceof Administrador) {
                return ((Administrador) usuario).getDtAdministrador();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<DtVentaCantidad> getComprasComprador(Long compradorId) {
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



    public String iniciarRec(DtInicioReclamo dtInicioReclamo) {
        try{
            Venta venta=ventaRepo.findById(dtInicioReclamo.getVenta_id()).get();
            Sucursal sucursal=sucursalRepo.findById(venta.getSucursal()).get();
            Reclamo reclamo=new Reclamo();
            reclamo.setTitulo(dtInicioReclamo.getAsunto());
            reclamo.setDescripcion(dtInicioReclamo.getComentario());
            reclamo.setId_venta(dtInicioReclamo.getVenta_id());
            reclamo.setSolucionado(false);
            reclamoRepo.save(reclamo);
            sucursal.getReclamos().add(reclamo);
            sucursalRepo.save(sucursal);
            return "ok";
        }catch (Exception e){
            return "error: " + e.getMessage();
        }
    }

    public DtDireccion buscarDireccionPorId(Long id) {
        return direccionRepo.findById(id).get().getDtDireccion();
    }

    public List<DtComprador> getCompradores() {
        List<Comprador> compradores=compradorRepo.findAll();
        List<DtComprador> dtCompradores=new ArrayList<>();
        for (Comprador c:compradores){
            dtCompradores.add(c.getDtComprador());
        }
        return dtCompradores;
    }

    public List<DtUsuarioSucursal> getUsuariosSucursal() {
        List<DtUsuarioSucursal> dtUsuariosSucursal=new ArrayList<>();
        List<UsuarioSucursal> usuariosSucursal=usuarioSucursalRepo.findAll();
        for(UsuarioSucursal us:usuariosSucursal){
            dtUsuariosSucursal.add(us.getDtUsuarioSucursal());
        }
        return dtUsuariosSucursal;
    }
    
    
    
    
    

   /* private DtSucursal getDtSucursalUsuarioSucursal(Long idUsuarioSucursal){
        List<Sucursal> sucursal=sucursalRepo.findAll();
        for(Sucursal s:sucursal){

        }

        Iterator<Sucursal> iterador = sucursal.iterator();
        while (iterador.hasNext()) {
            Sucursal sucursalIT = iterador.next();
            if () {
                iterador.remove();
                break;
            }
        }
    }
*/
}