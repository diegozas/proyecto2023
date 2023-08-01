package com.example.proyecto2023s1g2.controller;

import com.example.proyecto2023s1g2.datatype.DtInicioReclamo;
import com.example.proyecto2023s1g2.datatype.DtProducto;
import com.example.proyecto2023s1g2.domain.*;
import com.example.proyecto2023s1g2.enumeradores.EstadoVenta;
import com.example.proyecto2023s1g2.repository.*;
import com.example.proyecto2023s1g2.service.CategoriaService;
import com.example.proyecto2023s1g2.service.ProductoService;
import com.example.proyecto2023s1g2.service.SucursalService;
import com.example.proyecto2023s1g2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/cargar")
public class CargarController {
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private DireccionRepo direccionRepo;
    @Autowired
    private ProductoRepo productoRepo;
    @Autowired
    private SucursalRepo sucursalRepo;

    @Autowired
    private VentaRepo ventaRepo;

    private PasswordHasher passwordHasher;


    @GetMapping
    public String cargarDatos() throws NoSuchAlgorithmException {
        sucursalService.cargarSucursal();

        Direccion direccion1 = new Direccion("Av.Uruaugy", "-", "-", "Colombia y Paraiso", "Solymar Norte", "Costa de Oro", 4345, "73", "14");
        Direccion direccion2 = new Direccion("Iguá", "1234", "105", "Calle 126", "union", "Montevideo", 5028, "-", "-");
        Direccion direccion3 = new Direccion("Bvar. Artigas", "1825", "5", "Canelones", "La Comercial", "Montevideo", 5028, "-", "-");
        Direccion direccion4 = new Direccion("8 de octubre", "1234", "105", "20 de febrero", "Union", "Montevideo", 1024, "-", "-");
        Direccion direccion5 = new Direccion("Convencion", "1476", "104", "Durazno", "Belvedere", "Montevideo", 11100, "-", "-");

        direccionRepo.save(direccion1);
        direccionRepo.save(direccion2);
        direccionRepo.save(direccion3);
        direccionRepo.save(direccion4);
        direccionRepo.save(direccion5);




        String categoria1 = "Alimentos";
        String categoria2 = "Bebidas";
        String categoria3 = "Electrodomesticos";

        categoriaService.guardarCategoria(categoria1);
        categoriaService.guardarCategoria(categoria2);
        categoriaService.guardarCategoria(categoria3);

        categoriaService.guardarCategoria("Carne");
        categoriaService.guardarCategoria("Vino");
        categoriaService.guardarCategoria("Cerveza");
        categoriaService.guardarCategoria("Congelados");
        categoriaService.guardarCategoria("Jugueteria");

        categoriaService.agregarCategoria(1L,4L);
        categoriaService.agregarCategoria(2L,5L);
        categoriaService.agregarCategoria(2L,6L);
        categoriaService.agregarCategoria(1L,7L);

        DtProducto producto1=new DtProducto("Oreos","galletitas sabor chocolate con relleno de crema",129.9);
        DtProducto producto2=new DtProducto("Vino","Tinto tanat/cabernet saltenio",289.9);
        DtProducto producto3=new DtProducto("Refrigerador","Samsung 130Lts",11200d);

        DtProducto producto4=new DtProducto("Aspiradora","Succiona como ninguna, marca lider",1999.9);
        DtProducto producto5=new DtProducto("Yerba","Yerba LA SELVA para nerviosos 500 g",219.9);
        DtProducto producto6=new DtProducto("Vino Alamos","Desde mendoza Argentina",335d);

        DtProducto producto7=new DtProducto("Baraja española","De las clasicas, de reverso gris",19.9);
        DtProducto producto8=new DtProducto("Jarra electrica","La mejor amiga del que madruga",549.9);
        DtProducto producto9=new DtProducto("Manta calentador","Buena para el invierno",699.9);

        DtProducto producto10=new DtProducto("Coche Ultrax","Diecast metal y plastico, con detalles en el interior",189.9);
        DtProducto producto11=new DtProducto("Mixer","Kit de Mixer INHAUS",850d);
        DtProducto producto12=new DtProducto("Cubo Rubik","Cubo Rubik 3x3",169.99);

        DtProducto producto13=new DtProducto("Choclo","Choclo en grano",179.9);
        DtProducto producto14=new DtProducto("Cerveza Corona","Premium, clásica y auténtica, reconocida mundialmente por su alta calidad",119d);
        DtProducto producto15=new DtProducto("Juguete Veterinario","Toda la diversion de la odontologia veterinaria, sin los riesgos",669.9);

        DtProducto producto16=new DtProducto("Vino Convento","Convento Viejo",229.9);
        DtProducto producto17=new DtProducto("Marias","Galletitas clásicas",129.9);
        DtProducto producto18=new DtProducto("Rapiditas","Rapiditas BIMBO, 10 unidades",133d);

        DtProducto producto19=new DtProducto("Vino Pata Negra","Vino tinto, importado.",335d);
        DtProducto producto20=new DtProducto("Capu-Mattik","Maquina hacedora de cafe glorificado",899d);
        DtProducto producto21=new DtProducto("Latita Pilsen 0","Latita de cerveza sin alcohol, uruguaya.",63d);
        DtProducto producto22=new DtProducto("Cochecitos de Juguete","Para infantes pequeños, muy coloridos",273d);
        DtProducto producto23=new DtProducto("Dinosaurio","Juguete plástico, con apariencia prehistorica.",312d);
        DtProducto producto24=new DtProducto("Galletitas Chiquilin","Un clasico, de chocolate, riquisimas.",145d);
        DtProducto producto25=new DtProducto("Cerveza Franziskaner","Cerveza importada, calidad alemana.",127d);
        DtProducto producto26=new DtProducto("Vale Todo","Juego de mesa poco popular.",358d);
        DtProducto producto27=new DtProducto("Arroz Saman","Arroz blanco",63d);
        DtProducto producto28=new DtProducto("Lavarropas","Blanco,  7 kilos de capacidad.",10620d);
        DtProducto producto29=new DtProducto("Cerveza Mahos","Cerveza Mahos, importada.",103d);
        DtProducto producto30=new DtProducto("Televisor LED","Led 32', de fabricante desconocido.",8700d);
        DtProducto producto31=new DtProducto("Vino Blanco Don Pascual","Vino Blanco Don Pascual.",459d);
        DtProducto producto32=new DtProducto("Juguete Verduleria","Verduritas plasticas re tiernas, para jugar, no comer.",233d);
        DtProducto producto33=new DtProducto("Celular Smartphone","Con camara, wifi, y pantalla táctil, permite usar Whatsapp",9666d);
        DtProducto producto34=new DtProducto("Confitura","Sabor avellanas y cacao.",189d);
        DtProducto producto35=new DtProducto("Arvejas","Verdes, congeladas, medio kilo.",83d);
        DtProducto producto36=new DtProducto("Cerveza Heineken","Cerveza importada.",133d);
        DtProducto producto37=new DtProducto("Fideos Adria","Pasta con huevo,Tirabuzones Adria.",92d);
        DtProducto producto38=new DtProducto("Galletitas Bridge","Galletas waffle rellenas de chocolate.",156d);
        DtProducto producto39=new DtProducto("Hamburguesas Schneck","Clasicas, uruguayas, ricas, con alto contenido de sodio y grasas.",450d);
        DtProducto producto40=new DtProducto("Tonica Paso de los Toros","Bebida sabor Tonica, paso de los toros, litro y medio.",123d);
        DtProducto producto41=new DtProducto("Pepitos","Galletas con chispas de chocolate.",173d);
        DtProducto producto42=new DtProducto("Ravioles","Ravioles de jamon y queso muzzarella, 750g.",287d);
        DtProducto producto43=new DtProducto("Galletitas Solar","Clasico uruguayo, deliciosas, para el desayuno, la merienda o tomar mate",88d);
        DtProducto producto44=new DtProducto("Estufa","Estufa a gas, negra.",2500d);
        DtProducto producto45=new DtProducto("Pulpa de tomate","Salsa de tomate, 1Lt.",121d);
        DtProducto producto46=new DtProducto("Cerveza Pilsen 1lt","Cerveza Pilsen, 1 litro.",137d);
        DtProducto producto47=new DtProducto("Pulpa de tomate","Pulpa de tomate del productor de leche mas popular , 1lt.",137d);
        DtProducto producto48=new DtProducto("Pomelo Paso de los Toros","Refresco sabor Pomelo, Paso de los Toros, 1.5lts",143d);
        DtProducto producto49=new DtProducto("Refresco 7up","Refresco sabor Lima 7up, 1.5lts.",165d);
        DtProducto producto50=new DtProducto("Uno","Juego de cartas.",231d);
        DtProducto producto51=new DtProducto("Fanta","Refresco sabor naranja .",151d);
        DtProducto producto52=new DtProducto("Schweppes Tonica","Refresco sabor Tónico, Schweppes 1.5lts.",147d);
        DtProducto producto53=new DtProducto("Yerba Baldo","Yerba baldo clásica, 1kg.",269d);
        DtProducto producto54=new DtProducto("Coche a bateria","Juguete a batería con eje delantero móvil.",659d);
        DtProducto producto55=new DtProducto("Cerveza Artesanal","Cerveza artesanal, 1Lt.",189d);
        DtProducto producto56=new DtProducto("Monitor 24'","Pantalla led 24 pulgadas.",7689d);
        DtProducto producto57=new DtProducto("Microondas","Microondas LEXA color blanco",4599d);
        DtProducto producto58=new DtProducto("Sprite Zero","2.25Lts, sabor lima , sin azúcar.",229d);
        DtProducto producto59=new DtProducto("Aceite 1Lt","Aceite de Girasol, 1 litro.",139d);
        DtProducto producto60=new DtProducto("Vino Rosado","Vino Rosado Garzón, 1Lt.",329d);

        productoService.crearProducto(producto1,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F03q45jrs.png?alt=media&token=45b6da56-c803-4e9a-b4a0-7185ea2188fc");
        productoService.crearProducto(producto2,5L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F1c3vosqi.png?alt=media&token=d1e1af7d-1ed5-4eda-8cab-2bdfbb6b05cd");
        productoService.crearProducto(producto3,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F1gxsnu3f.png?alt=media&token=1ec3d87b-945c-4ba0-8c28-fbbee5ad1155");
        productoService.crearProducto(producto4,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F1uro6qk7.png?alt=media&token=cf4839e8-1e15-4d09-a1b7-e2f851265ba1");
        productoService.crearProducto(producto5,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F26mnovna.png?alt=media&token=ed8082c8-ea03-4ba5-9122-c540ac13728f");
        productoService.crearProducto(producto6,5L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F276moylv.png?alt=media&token=1df4233e-b9fe-4340-9084-3bf4749b656b");
        productoService.crearProducto(producto7,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F3868fzea.png?alt=media&token=9b67e34e-6752-488e-9689-2c42ffe025d8");
        productoService.crearProducto(producto8,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F3i8k31if.png?alt=media&token=7b458d83-d31a-4afc-a51b-26f316c263d1");
        productoService.crearProducto(producto9,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F3qr8ju28.png?alt=media&token=dfffad1f-bcc7-4cf7-bdf4-9b4b31d74768");
        productoService.crearProducto(producto10,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F3t8w6vx2.png?alt=media&token=304381ea-5be0-43f7-873a-0322de2b4f53");
        productoService.crearProducto(producto11,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F45ixxpsh.png?alt=media&token=ec8d9982-edff-4702-81bb-b6b205363384");
        productoService.crearProducto(producto12,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F4ciw4vxt.png?alt=media&token=96ef09a7-b17d-41bd-8485-1825a66a915f");
        productoService.crearProducto(producto13,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F4em6la6i.png?alt=media&token=11eee7b6-527e-4f72-a3ea-7d82f1420046");
        productoService.crearProducto(producto14,6L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F4q98qmku.png?alt=media&token=fd19f315-502e-4917-a5f9-60b264ddc3d6");
        productoService.crearProducto(producto15,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F4v2hdu49.png?alt=media&token=9b3eadde-6637-40f1-81bf-53e302ecdbc1");
        productoService.crearProducto(producto16,5L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F536prz1s.png?alt=media&token=a686ebe1-b60a-461c-92a3-ead733cb3337");
        productoService.crearProducto(producto17,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F5ixzsqod.png?alt=media&token=d6871e04-9af6-4684-9912-8318060c64e3");
        productoService.crearProducto(producto18,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F7bue6wgg.png?alt=media&token=3320bd3b-addd-4bae-ab49-f82a431b7799");
        productoService.crearProducto(producto19,5L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F7clvti6s.png?alt=media&token=cf5e13e0-9fc6-4058-8ca9-436241731d70");
        productoService.crearProducto(producto20,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F7sfz283q.png?alt=media&token=63318875-8e1d-47d1-b65d-9b01dd91ce1d");
        productoService.crearProducto(producto21,6L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F8lppb9zw.png?alt=media&token=69e8960c-7394-45bd-b5db-a47475958b85");
        productoService.crearProducto(producto22,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F8lv3kvci.png?alt=media&token=465a452a-b521-47e9-b10d-a87c89c3b754");
        productoService.crearProducto(producto23,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F8px79mhj.png?alt=media&token=afe19349-5e59-438b-8fb8-68144891b081");
        productoService.crearProducto(producto24,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F8zrcst64.png?alt=media&token=e72b8bdc-874c-49ae-858b-0c4295ae01d0");
        productoService.crearProducto(producto25,6L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F91ls0m0s.png?alt=media&token=656f507c-0a30-473b-809e-917ada89c5e4");
        productoService.crearProducto(producto26,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F91rkwkow.png?alt=media&token=0ef480b5-ed8c-43a2-9a25-51c6603c8d1d");
        productoService.crearProducto(producto27,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F93skifys.png?alt=media&token=dd02b520-e19f-475a-9e81-d5712ea9476a");
        productoService.crearProducto(producto28,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2F9sy73zjd.png?alt=media&token=0be8f0f9-667a-4625-8249-7c3d22c1b5e0");
        productoService.crearProducto(producto29,6L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fac7nv31m.png?alt=media&token=86f049dc-0f24-46e8-8df1-6df38222e57b");
        productoService.crearProducto(producto30,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fasxgt1w5.png?alt=media&token=6a2c6746-beea-464f-a4d7-dcc28fefe3bf");
        productoService.crearProducto(producto31,5L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fatg9mzob.png?alt=media&token=f590bfba-80a7-4658-a20b-ef75853165ad");
        productoService.crearProducto(producto32,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fbakg3mcn.png?alt=media&token=b6ac0bf8-8c07-47f6-be5e-df054ee9590b");
        productoService.crearProducto(producto33,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fbrvoft7w.png?alt=media&token=dcd593e3-44ca-4dc1-95c2-7591b7113458");
        productoService.crearProducto(producto34,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fc0dkohqk.png?alt=media&token=92c254bb-8fab-458f-a7d5-e500f0dc6fb4");
        productoService.crearProducto(producto35,7L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fctbypztj.png?alt=media&token=d6f54e20-bbf1-4f15-bc12-aa43bcb2ccad");
        productoService.crearProducto(producto36,6L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fd442n1uf.png?alt=media&token=3948d659-95e8-41e6-89a0-1af95f439ff8");
        productoService.crearProducto(producto37,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fdbzwxyye.png?alt=media&token=4f8c3284-27a5-415f-ac0c-e48f1d050f84");
        productoService.crearProducto(producto38,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fdy0ra7ub.png?alt=media&token=f1970cf9-767c-4b3b-94a2-7c3c74bd8e1d");
        productoService.crearProducto(producto39,4L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Febo33ujs.png?alt=media&token=154ba3ab-98b9-4424-998b-92742f9a6e78");
        productoService.crearProducto(producto40,2L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fek5zetbw.png?alt=media&token=ea868891-d611-4228-92af-b4a7cf00466f");
        productoService.crearProducto(producto41,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Felqn1krv.png?alt=media&token=f5e8a37f-2aa2-41f4-8e75-26b7addf6852");
        productoService.crearProducto(producto42,7L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Ffuh3egj9.png?alt=media&token=804106ba-9e86-4034-b27f-18ce328ad3e5");
        productoService.crearProducto(producto43,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fh69k5mn7.png?alt=media&token=57a53192-5674-43be-aa29-00f9d502093c");
        productoService.crearProducto(producto44,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fh78fqrpr.png?alt=media&token=27cb19c4-ce59-4d05-b83f-35bc1754c4a8");
        productoService.crearProducto(producto45,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fhck2gbod.png?alt=media&token=2e44a22c-ea5a-4a41-a4a1-c78e0d305573");
        productoService.crearProducto(producto46,6L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fi2vuo2rt.png?alt=media&token=5dfd74c6-05dd-4c98-9a22-e8ac26b0c8d1");
        productoService.crearProducto(producto47,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fikj1cfhx.png?alt=media&token=e0b925ca-0dd9-4055-8e6a-560d882a172c");
        productoService.crearProducto(producto48,2L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fil4u55no.png?alt=media&token=0aa76ee1-a165-45f8-8eef-4d73cbf823f2");
        productoService.crearProducto(producto49,2L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fise3dx5l.png?alt=media&token=23410abd-ae00-45c0-89e3-3ecc428a5744");
        productoService.crearProducto(producto50,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fj0rd1lu5.png?alt=media&token=a30af499-28a3-4f7c-8f53-2c085e30d2b4");
        productoService.crearProducto(producto51,2L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fl6kx0lta.png?alt=media&token=ed30be12-08c4-4ad4-a959-c6853305121f");
        productoService.crearProducto(producto52,2L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Flnix5t6y.png?alt=media&token=36adce4e-d17d-4f0f-b30f-a9e8ea038315");
        productoService.crearProducto(producto53,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fm2suxcsb.png?alt=media&token=ddd02a14-0b39-4a84-aebe-500070f4b215");
        productoService.crearProducto(producto54,8L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fn6j8bobw.png?alt=media&token=67f2251c-9aca-44d0-a79f-4573875b3b5d");
        productoService.crearProducto(producto55,6L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fn7yjtnom.png?alt=media&token=a1f2a1b5-0b49-43b2-9faa-1d0bca027476");
        productoService.crearProducto(producto56,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fns56wxhg.png?alt=media&token=c75b87d8-e14b-4ceb-8544-a1d27301eea7");
        productoService.crearProducto(producto57,3L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Foka4jeso.png?alt=media&token=e9c028b2-95f3-480c-a05f-4b78327290f4");
        productoService.crearProducto(producto58,2L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fpj11z9gx.png?alt=media&token=3c0ed0cd-38c4-4e72-8466-651ba6ca0011");
        productoService.crearProducto(producto59,1L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fpkc2de4s.png?alt=media&token=71f18a4a-0009-41fd-b69b-74b24d034d1d");
        productoService.crearProducto(producto60,5L,"https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/productos%2Fpz4auxww.png?alt=media&token=40d34880-6f74-4e11-a7d2-0329c0c88d76");

        productoService.cargarStock();

        passwordHasher=new PasswordHasher();
        String password="1234";
        Comprador comprador = new Comprador("Diego","Zas","nicozas_205@hotmail.com","", LocalDate.of(1994,8,20),"1234","https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/usuario%2Fgato1.jpg?alt=media&token=97f61c9b-0041-4ad3-a570-9e3806f41607&_gl=1*ilqrj7*_ga*NDU4NTEwMjQ1LjE2ODY1MDc2OTA.*_ga_CW55HF8NVT*MTY4NjUwNzY4OS4xLjEuMTY4NjUwODM0Ni4wLjAuMA");
        Comprador comprador1 = new Comprador("Romina","Vera","rominavera87@gmail.com","", LocalDate.of(1994,8,20),"1234","https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/usuario%2Fperro1.jpg?alt=media&token=37450ca4-15b8-40ca-9718-7ccf46a81c95&_gl=1*excuz2*_ga*NDU4NTEwMjQ1LjE2ODY1MDc2OTA.*_ga_CW55HF8NVT*MTY4NjUwNzY4OS4xLjEuMTY4NjUwODQzMy4wLjAuMA");
        Comprador comprador2 = new Comprador("Sebastian","Severino","sebasseverino3@gmail.com","", LocalDate.of(1994,8,20),"1234","https://firebasestorage.googleapis.com/v0/b/proyecto-d8ac1.appspot.com/o/usuario%2Fperro2.jpg?alt=media&token=93b969ec-5334-44aa-82e9-7eedba84ea1e&_gl=1*vyn2tf*_ga*NDU4NTEwMjQ1LjE2ODY1MDc2OTA.*_ga_CW55HF8NVT*MTY4NjUwNzY4OS4xLjEuMTY4NjUwODQ0OC4wLjAuMA");

        comprador.setBilletera(0);
        comprador.setPassword(passwordHasher.hashPassword(password));
        comprador.getDirecciones().add(direccion1);
        comprador.getDirecciones().add(direccion2);

        comprador1.setBilletera(0);
        comprador1.setPassword(passwordHasher.hashPassword(password));
        comprador1.getDirecciones().add(direccion3);
        comprador1.getDirecciones().add(direccion4);

        comprador2.setBilletera(0);
        comprador2.setPassword(passwordHasher.hashPassword(password));
        comprador2.getDirecciones().add(direccion5);

        Administrador administrador = new Administrador("admin1@hotmail.com","");
        Administrador administrador1= new Administrador("admin2@hotmail.com","");
        Administrador administrador2 = new Administrador("admin3@hotmail.com","");
        administrador.setPassword(passwordHasher.hashPassword(password));
        administrador1.setPassword(passwordHasher.hashPassword(password));
        administrador2.setPassword(passwordHasher.hashPassword(password));

        usuarioRepo.save(comprador);
        usuarioRepo.save(comprador1);
        usuarioRepo.save(comprador2);
        usuarioRepo.save(administrador);
        usuarioRepo.save(administrador1);
        usuarioRepo.save(administrador2);
        usuarioService.altaUsuarioSucursal("maria@hotmail.com","1234","Maria Noel","Costa Palumbo","1996-10-25",1L);
        usuarioService.altaUsuarioSucursal("juan@gmail.com","1234","Juan","Bourdette","1996-10-25",1L);
        usuarioService.altaUsuarioSucursal("eduardo@gmail.com","1234","Eduardo","De Los Santos","1996-10-25",2L);
        usuarioService.altaUsuarioSucursal("manuel@gmail.com","1234","Manuel","Cabrera","1996-10-25",2L);

        Venta venta1=new Venta();
        Venta venta2=new Venta();
        Venta venta3=new Venta();
        Venta venta4=new Venta();

        Venta venta5=new Venta();
        Venta venta6=new Venta();
        Venta venta7=new Venta();
        Venta venta8=new Venta();

        Venta venta9=new Venta();
        Venta venta10=new Venta();
        Venta venta11=new Venta();
        Venta venta12=new Venta();

        Venta venta13=new Venta();
        Venta venta14=new Venta();
        Venta venta15=new Venta();
        Venta venta16=new Venta();

        Venta venta17=new Venta();
        Venta venta18=new Venta();
        Venta venta19=new Venta();
        Venta venta20=new Venta();

        Venta venta21=new Venta();
        Venta venta22=new Venta();
        Venta venta23=new Venta();
        Venta venta24=new Venta();

        Venta venta25=new Venta();
        Venta venta26=new Venta();
        Venta venta27=new Venta();
        Venta venta28=new Venta();

        Venta venta29=new Venta();
        Venta venta30=new Venta();
        Venta venta31=new Venta();
        Venta venta32=new Venta();

        Venta venta33=new Venta();
        Venta venta34=new Venta();
        Venta venta35=new Venta();
        Venta venta36=new Venta();

        LocalDateTime fecha1=LocalDateTime.of(2023,5,11,12,10);
        LocalDateTime fecha2=LocalDateTime.of(2023,4,8,20,00);
        LocalDateTime fecha3=LocalDateTime.of(2023,6,2,15,30);

        LocalDateTime fecha4=LocalDateTime.of(2023,2,22,9,30);
        LocalDateTime fecha5=LocalDateTime.of(2023,3,12,14,45);
        LocalDateTime fecha6=LocalDateTime.of(2023,1,19,18,54);

        LocalDateTime fecha7=LocalDateTime.of(2023,6,10,22,39);
        LocalDateTime fecha8=LocalDateTime.of(2023,2,1,8,55);
        LocalDateTime fecha9=LocalDateTime.of(2023,6,2,21,10);

        LocalDateTime fecha10=LocalDateTime.of(2023,7,3,12,10);
        LocalDateTime fecha11=LocalDateTime.of(2023,5,6,20,00);
        LocalDateTime fecha12=LocalDateTime.of(2023,7,9,15,30);

        LocalDateTime fecha13=LocalDateTime.of(2023,5,13,9,30);
        LocalDateTime fecha14=LocalDateTime.of(2023,6,15,14,45);
        LocalDateTime fecha15=LocalDateTime.of(2023,7,18,18,54);

        LocalDateTime fecha16=LocalDateTime.of(2023,1,20,22,39);
        LocalDateTime fecha17=LocalDateTime.of(2023,2,25,8,55);
        LocalDateTime fecha18=LocalDateTime.of(2023,3,28,21,10);

        LocalDateTime fecha19=LocalDateTime.of(2023,4,30,12,10);
        LocalDateTime fecha20=LocalDateTime.of(2023,5,1,20,00);
        LocalDateTime fecha21=LocalDateTime.of(2023,6,2,15,30);

        LocalDateTime fecha22=LocalDateTime.of(2023,7,3,9,30);
        LocalDateTime fecha23=LocalDateTime.of(2023,1,28,14,45);
        LocalDateTime fecha24=LocalDateTime.of(2023,2,19,18,54);

        LocalDateTime fecha25=LocalDateTime.of(2023,3,10,22,39);
        LocalDateTime fecha26=LocalDateTime.of(2023,4,1,8,55);
        LocalDateTime fecha27=LocalDateTime.of(2023,5,2,21,10);

        LocalDateTime fecha28=LocalDateTime.of(2023,1,11,12,10);
        LocalDateTime fecha29=LocalDateTime.of(2023,2,8,20,00);
        LocalDateTime fecha30=LocalDateTime.of(2023,3,2,15,30);

        LocalDateTime fecha31=LocalDateTime.of(2023,4,22,9,30);
        LocalDateTime fecha32=LocalDateTime.of(2023,5,12,14,45);
        LocalDateTime fecha33=LocalDateTime.of(2023,6,19,18,54);

        LocalDateTime fecha34=LocalDateTime.of(2023,7,10,22,39);
        LocalDateTime fecha35=LocalDateTime.of(2023,1,1,8,55);
        LocalDateTime fecha36=LocalDateTime.of(2023,2,2,21,10);

        Direccion  sucursal1=direccionRepo.findById(1L).get();
        Direccion  sucursal2=direccionRepo.findById(2L).get();

        venta1.setSucursal(1L);
        venta1.setComprador(1L);
        venta1.setFecha(fecha1);
        venta1.setLugar_retiro(direccion1);
        venta1.setEstado(EstadoVenta.CONFIRMADO);

        venta2.setSucursal(2L);
        venta2.setComprador(1L);
        venta2.setFecha(fecha2);
        venta2.setLugar_retiro(direccion2);
        venta2.setEstado(EstadoVenta.CONFIRMADO);

        venta3.setSucursal(2L);
        venta3.setComprador(1L);
        venta3.setFecha(fecha3);
        venta3.setLugar_retiro(sucursal2);
        venta3.setEstado(EstadoVenta.CONFIRMADO);

        venta4.setSucursal(1L);
        venta4.setComprador(1L);
        venta4.setFecha(LocalDateTime.now().withSecond(0).withNano(0));
        venta4.setLugar_retiro(sucursal1);
        venta4.setEstado(EstadoVenta.CONFIRMADO);

        venta5.setSucursal(2L);
        venta5.setComprador(2L);
        venta5.setFecha(fecha4);
        venta5.setLugar_retiro(direccion3);
        venta5.setEstado(EstadoVenta.CONFIRMADO);

        venta6.setSucursal(2L);
        venta6.setComprador(2L);
        venta6.setFecha(fecha5);
        venta6.setLugar_retiro(sucursal2);
        venta6.setEstado(EstadoVenta.CONFIRMADO);

        venta7.setSucursal(1L);
        venta7.setComprador(2L);
        venta7.setFecha(fecha6);
        venta7.setLugar_retiro(sucursal1);
        venta7.setEstado(EstadoVenta.CONFIRMADO);


        venta8.setSucursal(1L);
        venta8.setComprador(2L);
        venta8.setFecha(LocalDateTime.now().withSecond(0).withNano(0));
        venta8.setLugar_retiro(direccion4);
        venta8.setEstado(EstadoVenta.CONFIRMADO);

        venta9.setSucursal(1L);
        venta9.setComprador(3L);
        venta9.setFecha(fecha7);
        venta9.setLugar_retiro(sucursal1);
        venta9.setEstado(EstadoVenta.CONFIRMADO);

        venta10.setSucursal(2L);
        venta10.setComprador(3L);
        venta10.setFecha(fecha8);
        venta10.setLugar_retiro(sucursal2);
        venta10.setEstado(EstadoVenta.CONFIRMADO);

        venta11.setSucursal(2L);
        venta11.setComprador(3L);
        venta11.setFecha(fecha9);
        venta11.setLugar_retiro(sucursal2);
        venta11.setEstado(EstadoVenta.CONFIRMADO);

        venta12.setSucursal(1L);
        venta12.setComprador(3L);
        venta12.setFecha(LocalDateTime.now().withSecond(0).withNano(0));
        venta12.setLugar_retiro(direccion5);
        venta12.setEstado(EstadoVenta.CONFIRMADO);

        venta13.setSucursal(1L);
        venta13.setComprador(1L);
        venta13.setFecha(fecha13);
        venta13.setLugar_retiro(direccion1);
        venta13.setEstado(EstadoVenta.CANCELADO);

        venta14.setSucursal(1L);
        venta14.setComprador(1L);
        venta14.setFecha(fecha14);
        venta14.setLugar_retiro(direccion1);
        venta14.setEstado(EstadoVenta.CANCELADO);

        venta15.setSucursal(1L);
        venta15.setComprador(1L);
        venta15.setFecha(fecha15);
        venta15.setLugar_retiro(direccion2);
        venta15.setEstado(EstadoVenta.CANCELADO);

        venta16.setSucursal(1L);
        venta16.setComprador(1L);
        venta16.setFecha(fecha16);
        venta16.setLugar_retiro(direccion2);
        venta16.setEstado(EstadoVenta.CANCELADO);


        venta17.setSucursal(2L);
        venta17.setComprador(1L);
        venta17.setFecha(fecha17);
        venta17.setLugar_retiro(direccion1);
        venta17.setEstado(EstadoVenta.EN_ESPERA);

        venta18.setSucursal(2L);
        venta18.setComprador(1L);
        venta18.setFecha(fecha18);
        venta18.setLugar_retiro(direccion2);
        venta18.setEstado(EstadoVenta.EN_ESPERA);

        venta19.setSucursal(2L);
        venta19.setComprador(1L);
        venta19.setFecha(fecha19);
        venta19.setLugar_retiro(direccion1);
        venta19.setEstado(EstadoVenta.CANCELADO);

        venta20.setSucursal(2L);
        venta20.setComprador(1L);
        venta20.setFecha(fecha20);
        venta20.setLugar_retiro(direccion2);
        venta20.setEstado(EstadoVenta.EN_ESPERA);

        venta21.setSucursal(1L);
        venta21.setComprador(2L);
        venta21.setFecha(fecha21);
        venta21.setLugar_retiro(sucursal1);
        venta21.setEstado(EstadoVenta.EN_ESPERA);

        venta22.setSucursal(1L);
        venta22.setComprador(2L);
        venta22.setFecha(fecha22);
        venta22.setLugar_retiro(direccion3);
        venta22.setEstado(EstadoVenta.CANCELADO);

        venta23.setSucursal(1L);
        venta23.setComprador(2L);
        venta23.setFecha(fecha23);
        venta23.setLugar_retiro(direccion4);
        venta23.setEstado(EstadoVenta.EN_ESPERA);

        venta24.setSucursal(1L);
        venta24.setComprador(2L);
        venta24.setFecha(fecha24);
        venta24.setLugar_retiro(direccion4);
        venta24.setEstado(EstadoVenta.EN_ESPERA);

        venta25.setSucursal(2L);
        venta25.setComprador(2L);
        venta25.setFecha(fecha25);
        venta25.setLugar_retiro(direccion4);
        venta25.setEstado(EstadoVenta.CANCELADO);

        venta26.setSucursal(2L);
        venta26.setComprador(2L);
        venta26.setFecha(fecha26);
        venta26.setLugar_retiro(direccion3);
        venta26.setEstado(EstadoVenta.EN_ESPERA);

        venta27.setSucursal(2L);
        venta27.setComprador(2L);
        venta27.setFecha(fecha27);
        venta27.setLugar_retiro(direccion4);
        venta27.setEstado(EstadoVenta.EN_ESPERA);

        venta28.setSucursal(2L);
        venta28.setComprador(2L);
        venta28.setFecha(fecha28);
        venta28.setLugar_retiro(direccion3);
        venta28.setEstado(EstadoVenta.EN_ESPERA);

        venta29.setSucursal(1L);
        venta29.setComprador(3L);
        venta29.setFecha(fecha29);
        venta29.setLugar_retiro(direccion5);
        venta29.setEstado(EstadoVenta.EN_ESPERA);

        venta30.setSucursal(1L);
        venta30.setComprador(3L);
        venta30.setFecha(fecha30);
        venta30.setLugar_retiro(direccion5);
        venta30.setEstado(EstadoVenta.CANCELADO);

        venta31.setSucursal(1L);
        venta31.setComprador(3L);
        venta31.setFecha(fecha31);
        venta31.setLugar_retiro(sucursal1);
        venta31.setEstado(EstadoVenta.EN_ESPERA);

        venta32.setSucursal(1L);
        venta32.setComprador(3L);
        venta32.setFecha(fecha32);
        venta32.setLugar_retiro(direccion5);
        venta32.setEstado(EstadoVenta.EN_ESPERA);

        venta33.setSucursal(2L);
        venta33.setComprador(3L);
        venta33.setFecha(fecha33);
        venta33.setLugar_retiro(direccion5);
        venta33.setEstado(EstadoVenta.EN_ESPERA);

        venta34.setSucursal(2L);
        venta34.setComprador(3L);
        venta34.setFecha(fecha34);
        venta34.setLugar_retiro(direccion5);
        venta34.setEstado(EstadoVenta.EN_ESPERA);

        venta35.setSucursal(2L);
        venta35.setComprador(3L);
        venta35.setFecha(fecha35);
        venta35.setLugar_retiro(sucursal2);
        venta35.setEstado(EstadoVenta.EN_ESPERA);

        venta36.setSucursal(2L);
        venta36.setComprador(3L);
        venta36.setFecha(fecha36);
        venta36.setLugar_retiro(direccion5);
        venta36.setEstado(EstadoVenta.EN_ESPERA);

        List<Producto> listaProductos =productoRepo.findAll();
        Producto productoObtener1= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Oreos"))
                .findFirst().get();

        Producto productoObtener2= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Aspiradora"))
                .findFirst().get();

        Producto productoObtener3= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Yerba"))
                .findFirst().get();

        Producto productoObtener4= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Baraja española"))
                .findFirst().get();

        Producto productoObtener5= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Cubo Rubik"))
                .findFirst().get();

        Producto productoObtener6= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Choclo"))
                .findFirst().get();

        Producto productoObtener7= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Jarra electrica"))
                .findFirst().get();

        Producto productoObtener8= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Marias"))
                .findFirst().get();

        Producto productoObtener9= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Refrigerador"))
                .findFirst().get();

        Producto productoObtener10= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Coche Ultrax"))
                .findFirst().get();

        Producto productoObtener11= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Arvejas"))
                .findFirst().get();

        Producto productoObtener12= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Choclo"))
                .findFirst().get();

        Producto productoObtener13= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Vino"))
                .findFirst().get();

        Producto productoObtener14= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Vino Alamos"))
                .findFirst().get();

        Producto productoObtener15= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Manta calentador"))
                .findFirst().get();

        Producto productoObtener16= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Mixer"))
                .findFirst().get();

        Producto productoObtener17= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Cerveza Corona"))
                .findFirst().get();

        Producto productoObtener18= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Juguete Veterinario"))
                .findFirst().get();

        Producto productoObtener19= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Vino Convento"))
                .findFirst().get();

        Producto productoObtener20= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Rapiditas"))
                .findFirst().get();

        Producto productoObtener21= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Vino Pata Negra"))
                .findFirst().get();

        Producto productoObtener22= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Capu-Mattik"))
                .findFirst().get();

        Producto productoObtener23= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Latita Pilsen 0"))
                .findFirst().get();

        Producto productoObtener24= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Cochecitos de Juguete"))
                .findFirst().get();

        Producto productoObtener25= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Dinosaurio"))
                .findFirst().get();

        Producto productoObtener26= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Galletitas Chiquilin"))
                .findFirst().get();

        Producto productoObtener27= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Cerveza Franziskaner"))
                .findFirst().get();

        Producto productoObtener28= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Vale Todo"))
                .findFirst().get();

        Producto productoObtener29= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Arroz Saman"))
                .findFirst().get();

        Producto productoObtener30= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Lavarropas"))
                .findFirst().get();

        Producto productoObtener31= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Cerveza Mahos"))
                .findFirst().get();

        Producto productoObtener32= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Televisor LED"))
                .findFirst().get();

        Producto productoObtener33= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Vino Blanco Don Pascual"))
                .findFirst().get();

        Producto productoObtener34= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Juguete Verduleria"))
                .findFirst().get();

        Producto productoObtener35= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Celular Smartphone"))
                .findFirst().get();

        Producto productoObtener36= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Confitura"))
                .findFirst().get();

        Producto productoObtener37= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Arvejas"))
                .findFirst().get();

        Producto productoObtener38= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Cerveza Heineken"))
                .findFirst().get();

        Producto productoObtener39= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Fideos Adria"))
                .findFirst().get();

        Producto productoObtener40= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Galletitas Bridge"))
                .findFirst().get();

        Producto productoObtener42= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Tonica Paso de los Toros"))
                .findFirst().get();

        Producto productoObtener43= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Pepitos"))
                .findFirst().get();

        Producto productoObtener44= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Ravioles"))
                .findFirst().get();

        Producto productoObtener45= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Galletitas Solar"))
                .findFirst().get();

        Producto productoObtener46= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Estufa"))
                .findFirst().get();

        Producto productoObtener47= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Pulpa de tomate"))
                .findFirst().get();

        Producto productoObtener48= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Cerveza Pilsen 1lt"))
                .findFirst().get();

        Producto productoObtener49= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Pulpa de tomate"))
                .findFirst().get();

        Producto productoObtener50= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Pomelo Paso de los Toros"))
                .findFirst().get();

        Producto productoObtener51= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Refresco 7up"))
                .findFirst().get();

        Producto productoObtener52= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Uno"))
                .findFirst().get();

        Producto productoObtener53= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Fanta"))
                .findFirst().get();

        Producto productoObtener54= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Schweppes Tonica"))
                .findFirst().get();

        Producto productoObtener55= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Yerba Baldo"))
                .findFirst().get();

        Producto productoObtener56= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Coche a bateria"))
                .findFirst().get();

        Producto productoObtener57= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Cerveza Artesanal"))
                .findFirst().get();

        Producto productoObtener59= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Microondas"))
                .findFirst().get();

        Producto productoObtener60= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Sprite Zero"))
                .findFirst().get();

        Producto productoObtener61= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Aceite 1Lt"))
                .findFirst().get();

        Producto productoObtener62= listaProductos.stream()
                .filter(producto -> producto.getNombre().equals("Vino Rosado"))
                .findFirst().get();



        venta1.getProductos().add(productoObtener1);
        venta1.getProductos().add(productoObtener1);
        venta1.getProductos().add(productoObtener1);
        venta1.getProductos().add(productoObtener2);
        venta1.getProductos().add(productoObtener3);

        venta2.getProductos().add(productoObtener10);
        venta2.getProductos().add(productoObtener9);
        venta2.getProductos().add(productoObtener7);

        venta3.getProductos().add(productoObtener6);
        venta3.getProductos().add(productoObtener4);

        venta4.getProductos().add(productoObtener10);

        venta5.getProductos().add(productoObtener4);
        venta5.getProductos().add(productoObtener2);
        venta5.getProductos().add(productoObtener6);
        venta5.getProductos().add(productoObtener2);
        venta5.getProductos().add(productoObtener3);

        venta6.getProductos().add(productoObtener8);
        venta6.getProductos().add(productoObtener8);

        venta7.getProductos().add(productoObtener6);
        venta7.getProductos().add(productoObtener7);
        venta7.getProductos().add(productoObtener5);

        venta8.getProductos().add(productoObtener9);

        venta9.getProductos().add(productoObtener3);
        venta9.getProductos().add(productoObtener3);
        venta9.getProductos().add(productoObtener5);
        venta9.getProductos().add(productoObtener1);
        venta9.getProductos().add(productoObtener3);

        venta10.getProductos().add(productoObtener10);
        venta10.getProductos().add(productoObtener9);
        venta10.getProductos().add(productoObtener4);

        venta11.getProductos().add(productoObtener10);
        venta11.getProductos().add(productoObtener2);

        venta12.getProductos().add(productoObtener4);
        venta12.getProductos().add(productoObtener4);

        venta13.getProductos().add(productoObtener16);
        venta13.getProductos().add(productoObtener18);
        venta13.getProductos().add(productoObtener19);

        venta14.getProductos().add(productoObtener20);
        venta14.getProductos().add(productoObtener30);
        venta14.getProductos().add(productoObtener27);

        venta15.getProductos().add(productoObtener21);
        venta15.getProductos().add(productoObtener22);
        venta15.getProductos().add(productoObtener23);

        venta16.getProductos().add(productoObtener30);
        venta16.getProductos().add(productoObtener30);
        venta16.getProductos().add(productoObtener30);

        venta17.getProductos().add(productoObtener18);
        venta17.getProductos().add(productoObtener18);
        venta17.getProductos().add(productoObtener20);

        venta18.getProductos().add(productoObtener25);
        venta18.getProductos().add(productoObtener25);
        venta18.getProductos().add(productoObtener28);

        venta19.getProductos().add(productoObtener21);
        venta19.getProductos().add(productoObtener22);
        venta19.getProductos().add(productoObtener15);
        venta19.getProductos().add(productoObtener15);
        venta19.getProductos().add(productoObtener23);

        venta20.getProductos().add(productoObtener1);
        venta20.getProductos().add(productoObtener5);

        venta21.getProductos().add(productoObtener6);
        venta21.getProductos().add(productoObtener7);
        venta21.getProductos().add(productoObtener8);

        venta22.getProductos().add(productoObtener10);
        venta22.getProductos().add(productoObtener18);
        venta22.getProductos().add(productoObtener15);

        venta23.getProductos().add(productoObtener30);
        venta23.getProductos().add(productoObtener19);

        venta24.getProductos().add(productoObtener6);

        venta25.getProductos().add(productoObtener15);
        venta25.getProductos().add(productoObtener17);
        venta25.getProductos().add(productoObtener19);

        venta26.getProductos().add(productoObtener25);
        venta26.getProductos().add(productoObtener20);
        venta26.getProductos().add(productoObtener19);

        venta27.getProductos().add(productoObtener1);
        venta27.getProductos().add(productoObtener11);
        venta27.getProductos().add(productoObtener21);
        venta27.getProductos().add(productoObtener2);
        venta27.getProductos().add(productoObtener22);

        venta28.getProductos().add(productoObtener3);
        venta28.getProductos().add(productoObtener13);

        venta29.getProductos().add(productoObtener23);
        venta29.getProductos().add(productoObtener28);
        venta29.getProductos().add(productoObtener27);

        venta30.getProductos().add(productoObtener28);
        venta30.getProductos().add(productoObtener1);

        venta31.getProductos().add(productoObtener2);
        venta31.getProductos().add(productoObtener3);
        venta31.getProductos().add(productoObtener4);
        venta31.getProductos().add(productoObtener5);
        venta31.getProductos().add(productoObtener6);
        venta31.getProductos().add(productoObtener7);

        venta32.getProductos().add(productoObtener18);
        venta32.getProductos().add(productoObtener28);

        venta33.getProductos().add(productoObtener9);

        venta34.getProductos().add(productoObtener15);
        venta34.getProductos().add(productoObtener16);

        venta35.getProductos().add(productoObtener28);
        venta35.getProductos().add(productoObtener3);
        venta35.getProductos().add(productoObtener28);
        venta35.getProductos().add(productoObtener1);

        venta36.getProductos().add(productoObtener12);
        venta36.getProductos().add(productoObtener12);
        venta36.getProductos().add(productoObtener22);
        venta36.getProductos().add(productoObtener12);
        venta36.getProductos().add(productoObtener22);
        venta36.getProductos().add(productoObtener12);

        List<Venta> ventas=new ArrayList<>();
        ventas.add(venta1);
        ventas.add(venta2);
        ventas.add(venta3);
        ventas.add(venta4);
        ventas.add(venta5);
        ventas.add(venta6);
        ventas.add(venta7);
        ventas.add(venta8);
        ventas.add(venta9);
        ventas.add(venta10);
        ventas.add(venta11);
        ventas.add(venta12);
        ventas.add(venta13);
        ventas.add(venta14);
        ventas.add(venta15);
        ventas.add(venta16);
        ventas.add(venta17);
        ventas.add(venta18);
        ventas.add(venta19);
        ventas.add(venta20);
        ventas.add(venta21);
        ventas.add(venta22);
        ventas.add(venta23);
        ventas.add(venta24);
        ventas.add(venta25);
        ventas.add(venta26);
        ventas.add(venta27);
        ventas.add(venta28);
        ventas.add(venta29);
        ventas.add(venta30);
        ventas.add(venta31);
        ventas.add(venta32);
        ventas.add(venta33);
        ventas.add(venta34);
        ventas.add(venta35);
        ventas.add(venta36);
        guardarVentas(ventas);

        String asunto1="Devolución";
        String asunto2="Producto no llego";
        String asunto3="Llegó otro producto";
        String asunto4="Sigo esperando la compra";

        String comentario1="Quiero devolver la compra";
        String comentario2="No me llegó el producto que había solicitado";
        String comentario3="Me llegó otro producto y no el que yo solicite";
        String comentario4="Aun sigo en espera de que me llegue la compra que hice";

        DtInicioReclamo dtIR1 = new DtInicioReclamo(asunto1, comentario1, 1L, 1L);
        DtInicioReclamo dtIR2 = new DtInicioReclamo(asunto2, comentario2, 1L, 2L);
        DtInicioReclamo dtIR3 = new DtInicioReclamo(asunto3, comentario3, 1L, 3L);
        DtInicioReclamo dtIR4 = new DtInicioReclamo(asunto4, comentario4, 1L, 4L);

        DtInicioReclamo dtIR5 = new DtInicioReclamo(asunto1, comentario1, 2L, 5L);
        DtInicioReclamo dtIR6 = new DtInicioReclamo(asunto2, comentario2, 2L, 6L);
        DtInicioReclamo dtIR7 = new DtInicioReclamo(asunto3, comentario3, 2L, 7L);
        DtInicioReclamo dtIR8 = new DtInicioReclamo(asunto4, comentario4, 2L, 8L);

        DtInicioReclamo dtIR9 = new DtInicioReclamo(asunto1, comentario1, 3L, 9L);
        DtInicioReclamo dtIR10 = new DtInicioReclamo(asunto2, comentario2, 3L, 10L);
        DtInicioReclamo dtIR11 = new DtInicioReclamo(asunto3, comentario3, 3L, 11L);
        DtInicioReclamo dtIR12 = new DtInicioReclamo(asunto4, comentario4, 3L, 12L);

        usuarioService.iniciarRec(dtIR1);
        usuarioService.iniciarRec(dtIR2);
        usuarioService.iniciarRec(dtIR3);
        usuarioService.iniciarRec(dtIR4);

        usuarioService.iniciarRec(dtIR5);
        usuarioService.iniciarRec(dtIR6);
        usuarioService.iniciarRec(dtIR7);
        usuarioService.iniciarRec(dtIR8);

        usuarioService.iniciarRec(dtIR9);
        usuarioService.iniciarRec(dtIR10);
        usuarioService.iniciarRec(dtIR11);
        usuarioService.iniciarRec(dtIR12);

        return "Se cargaron los datos";
    }


    private String getPasswordHash(String password) throws NoSuchAlgorithmException {
        passwordHasher=new PasswordHasher();
        return passwordHasher.hashPassword(password);
    }

    private void guardarVentas(List<Venta> ventas){
        for (Venta v:ventas){
            ventaGuardar(v);
        }
    }

    private void ventaGuardar(Venta venta){
        Comprador comprador=(Comprador)usuarioRepo.findById(venta.getComprador()).get();
        double total=0;
        for (Producto p:venta.getProductos()){
            total=total+p.getPrecio();
        }
        Sucursal sucursal=sucursalRepo.findById(venta.getSucursal()).get();
        venta.setTotal(total);
        ventaRepo.save(venta);
        comprador.getHistorial_venta().add(venta);
        sucursal.getVentas().add(venta);
        sucursalRepo.save(sucursal);
        usuarioRepo.save(comprador);
    }

}
