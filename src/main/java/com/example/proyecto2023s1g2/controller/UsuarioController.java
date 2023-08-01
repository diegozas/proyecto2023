package com.example.proyecto2023s1g2.controller;

import com.example.proyecto2023s1g2.datatype.*;
import com.example.proyecto2023s1g2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/obtenerUsuarioComprador")
    public DtComprador obtenerUsuarioComprador(@RequestParam("id_usuario") Long id_usuario) {
        return usuarioService.obtenerUsuarioComprador(id_usuario);
    }

    @GetMapping("/usuario/obtenerUsuarioSucursal")
    public DtUsuarioSucursal obtenerUsuarioSucursal(@RequestParam("id_usuario") Long id_usuario) {
        return usuarioService.obtenerUsuarioSucursal(id_usuario);
    }

    @PostMapping("/usuario/recuperar_password")
    public ResponseEntity<String> recuperarPassword(@RequestBody DtUsuario dtU) {
        // Verificar si el correo electrónico corresponde a un usuario registrado
        if (usuarioService.getUsuario(dtU.getEmail())==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El correo electrónico no está registrado");
        }
        usuarioService.enviarMail(dtU.getEmail());
        return ResponseEntity.ok("se envio el mail");
    }
    @PostMapping("/usuario/recuperarContraseña")
    public ResponseEntity<String> recuperarContraseña(@RequestBody DtUsuario dtU) {
        // Verificar si el correo electrónico corresponde a un usuario registrado
        if (usuarioService.getUsuario(dtU.getEmail())==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El correo electrónico no está registrado");
        }
        usuarioService.enviarMailPorApi(dtU.getEmail());
        return ResponseEntity.ok("se envio el mail");
    }


    @PostMapping("/usuario/crearComprador")
    public String crearComprador(@RequestBody DtComprador dtC) throws NoSuchAlgorithmException {
        return usuarioService.altaComprador(dtC.getEmail(),dtC.getPassword(),dtC.getNombre(),dtC.getApellido(),dtC.getTelefono(),dtC.getFecha_nac().toString(),dtC.getFoto());
    }




    @PostMapping("/usuario/crearUsuarioSucursal")
    public String crearUsuarioSucursal(@RequestBody DtCrearUsuarioSucursal dtUS) throws NoSuchAlgorithmException{
        return usuarioService.altaUsuarioSucursal(dtUS.getEmail(),dtUS.getPassword(),dtUS.getNombre(),dtUS.getApellido(),dtUS.getFecha_nac().toString(),dtUS.getSucursal_id());
    }

    /*
    @PostMapping("/usuario/crearDireccionComprador")
    public String crearDireccionComprador(@RequestParam("calle")String calle,
                                    @RequestParam("numero")String numero,
                                    @RequestParam("apartamento")String apartamento,
                                    @RequestParam("esquina")String esquina,
                                    @RequestParam("barrio")String barrio,
                                    @RequestParam("localidad")String localidad,
                                    @RequestParam("codigo_postal")String codigo_postal,
                                    @RequestParam("manzana")String manzana,
                                    @RequestParam("solar")String solar,
                                    @RequestParam("comprador")Long comprador_id){

        if(codigo_postal.isEmpty()){
            codigo_postal="0";
        }
        if(manzana.isEmpty()){
            manzana="-";
        }
        if(solar.isEmpty()){
            solar="-";
        }
        return usuarioService.altaDireccionComprador(calle,numero,apartamento,esquina,barrio,localidad,codigo_postal,manzana,solar,comprador_id);
    }
    */

    @PostMapping("/usuario/crearDireccionComprador")
    public String crearDireccionComprador(@RequestBody DtCrearDireccionComprador dtCM){
        return usuarioService.crearDireccionComprador(dtCM);
    }

    @GetMapping("/usuario/buscarDireccionPorId")
    public DtDireccion buscarDireccionPorId(@RequestParam("id")Long id){
        return usuarioService.buscarDireccionPorId(id);
    }


    @GetMapping("/usuario/listarDomicilios")
    public List<DtDireccion> listarDomicilios(@RequestParam("comprador_id")Long comprador_id){
        return usuarioService.getDomiciliosComprador(comprador_id);
    }


    @PutMapping("/usuario/modificarComprador")
    public String modificarComprador(@RequestBody DtCompradorMod dtcMod){
        return usuarioService.modComprador(dtcMod);
    }


    @PutMapping("/usuario/modificarUsuarioSucursal")
    public String modificarUsuario(@RequestBody DtUsuarioSucursalMod dtusMod){
        return usuarioService.modUsuarioSucursal(dtusMod);
    }


    @GetMapping("/usuario/listarUsuarios")
    public List<DtUsuario> listarUsuarios(){
                return usuarioService.listarUsuarios();
        }

    @GetMapping("/usuario/buscarUsuario")
    public DtUsuario buscarUsuario(@RequestParam("usuario_id")Long usuario_id){
        return usuarioService.buscarUsuario(usuario_id);
    }

    @PostMapping("/usuario/iniciarReclamo")
    public String iniciarReclamo(@RequestBody DtInicioReclamo dtInicioReclamo){
        return usuarioService.iniciarRec(dtInicioReclamo);
    }


    @GetMapping("/usuario/listarComprasComprador")
    public List<DtVentaCantidad> listarVentasComprador(@RequestParam("comprador_id")Long comprador_id){
        return usuarioService.getComprasComprador(comprador_id);
    }

    @PutMapping("/usuario/cambiar-contrasena")
    public String cambiarContrasena(@RequestBody DtUsuario dtU) throws NoSuchAlgorithmException {
        return usuarioService.actualizarPassword(dtU.getToken(), dtU.getPassword());
    }

    @PostMapping("/usuario/iniciarSesion")
    public ResponseEntity<?> iniciarSession(@RequestBody DtUsuario dtU){
        return usuarioService.iniciarSesion(dtU.getEmail(), dtU.getPassword());

    }

    @PostMapping(value = "/usuario/passwordigual")
    public boolean verificarPassword(@RequestBody DtUsuario dtU) throws NoSuchAlgorithmException {
        return usuarioService.verificarPassword(dtU.getEmail(),dtU.getPassword());
    }


    @DeleteMapping("/usuario/eliminarUsuarioComprador")
    public String eliminarUsuarioComprador(@RequestBody DtComprador dtC){
        return usuarioService.eliminarComprador(dtC.getId());
    }

    @PutMapping("/usuario/activarUsuarioComprador")
    public String activarUsuarioComprador(@RequestBody DtComprador dtC){
        return usuarioService.activarComprador(dtC.getId());
    }

    @DeleteMapping("/usuario/bloquearUsuarioComprador")
    public String bloquearUsuarioComprador(@RequestBody DtComprador dtC){
        return usuarioService.bloquearComprador(dtC.getId());
    }

    @PutMapping("/usuario/desbloquearUsuarioComprador")
    public String desbloquearUsuarioComprador(@RequestBody DtComprador dtC){
        return usuarioService.desbloquearComprador(dtC.getId());
    }

    @DeleteMapping("/direccion/eliminarDireccionComprador")
    public String eliminarDireccion(@RequestBody DtDireccion dtD){
        return usuarioService.eliminarDireccion(dtD.getId());
    }

    @GetMapping("/usuario/listarCompraEnEsperaComprador")
    public List<DtVenta> getVentasComprador(@RequestBody DtComprador dtC){
        return usuarioService.getVentasEnEsperaComprador(dtC.getId());
    }

    @PostMapping("/usuario/cancelarCompra")
    public String cancelarCompra(@RequestBody DtVenta dtV){
        return usuarioService.cancelarCompra(dtV.getId());
    }



    @PutMapping("/direccion/modificarDireccion")
    public String modificarDireccion(@RequestBody DtDireccion dtCM){
        return usuarioService.modificarDomicilio(dtCM);
    }



    @GetMapping("/usuario/listarCompradores")
    public List<DtComprador> getCompradores(){
        return usuarioService.getCompradores();
    }

    @GetMapping("/usuario/listarUsuariosSucursal")
    public List<DtUsuarioSucursal> getUsuarioSucursal(){
        return usuarioService.getUsuariosSucursal();
    }

}
