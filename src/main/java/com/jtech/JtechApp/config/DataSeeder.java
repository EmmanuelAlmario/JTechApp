package com.jtech.JtechApp.config;

import com.jtech.JtechApp.categoria.entity.Categoria;
import com.jtech.JtechApp.categoria.entity.Subcategoria;
import com.jtech.JtechApp.categoria.repository.CategoriaRepository;
import com.jtech.JtechApp.categoria.repository.SubcategoriaRepository;
import com.jtech.JtechApp.pago.entity.PagoEfectivo;
import com.jtech.JtechApp.pago.entity.PagoTarjetaCredito;
import com.jtech.JtechApp.pago.repository.MetodoPagoRepository;
import com.jtech.JtechApp.producto.entity.ImagenProducto;
import com.jtech.JtechApp.producto.entity.Marca;
import com.jtech.JtechApp.producto.entity.Producto;
import com.jtech.JtechApp.producto.entity.VarianteProducto;
import com.jtech.JtechApp.producto.repository.ImagenProductoRepository;
import com.jtech.JtechApp.producto.repository.MarcaRepository;
import com.jtech.JtechApp.producto.repository.ProductoRepository;
import com.jtech.JtechApp.producto.repository.VarianteProductoRepository;
import com.jtech.JtechApp.usuario.entity.Administrador;
import com.jtech.JtechApp.usuario.enums.NivelAdmin;
import com.jtech.JtechApp.usuario.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final AdministradorRepository administradorRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ProductoRepository productoRepository;
    private final VarianteProductoRepository varianteProductoRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.nombre}")
    private String adminNombre;
    @Value("${admin.cargo}")
    private String adminCargo;

    @Override
    public void run(ApplicationArguments args) {

        // SuperAdmin
        if (!administradorRepository.existsByEmail(adminEmail)) {
            Administrador admin = new Administrador();
            admin.setNombre(adminNombre);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setCargo(adminCargo);
            admin.setNivel(NivelAdmin.SUPER_ADMIN);
            administradorRepository.save(admin);
        }

        // Métodos de pago
        if (metodoPagoRepository.count() == 0) {
            metodoPagoRepository.save(new PagoEfectivo());
            PagoTarjetaCredito credito = new PagoTarjetaCredito();
            credito.setUltimosCuatroDigitos("4242");
            credito.setNombreTitular("Cliente Demo");
            metodoPagoRepository.save(credito);
        }

        // Datos del catálogo
        if (categoriaRepository.count() == 0) {

            // ── MARCAS ──────────────────────────────────────────
            Marca apple = new Marca();
            apple.setNombre("Apple");
            apple.setLogo("https://upload.wikimedia.org/wikipedia/commons/f/fa/Apple_logo_black.svg");
            marcaRepository.save(apple);

            Marca samsung = new Marca();
            samsung.setNombre("Samsung");
            samsung.setLogo("https://cdn.worldvectorlogo.com/logos/samsung-8.svg");
            marcaRepository.save(samsung);

            Marca jtech = new Marca();
            jtech.setNombre("JTech PC");
            jtech.setLogo("https://upload.wikimedia.org/wikipedia/commons/f/fa/Apple_logo_black.svg");
            marcaRepository.save(jtech);

            // ── CATEGORÍAS Y SUBCATEGORÍAS ───────────────────────
            Categoria portatiles = new Categoria();
            portatiles.setNombre("Portátiles");
            portatiles.setDescripcion("Laptops y computadoras portátiles");
            categoriaRepository.save(portatiles);

            Subcategoria portatilesTrabajo = crearSubcategoria("Portátiles de Trabajo", portatiles);
            Subcategoria portatilesGamer = crearSubcategoria("Portátiles Gamer", portatiles);
            Subcategoria portatilesUltrabook = crearSubcategoria("Ultrabooks", portatiles);

            Categoria pcs = new Categoria();
            pcs.setNombre("PC");
            pcs.setDescripcion("Computadoras de escritorio");
            categoriaRepository.save(pcs);

            Subcategoria pcEscritorio = crearSubcategoria("PC Escritorio", pcs);
            Subcategoria pcGamer = crearSubcategoria("PC Gamer", pcs);

            Categoria celulares = new Categoria();
            celulares.setNombre("Celulares");
            celulares.setDescripcion("Smartphones de alta gama");
            categoriaRepository.save(celulares);

            Subcategoria celularesAlta = crearSubcategoria("Alta Gama", celulares);
            Subcategoria celularesBaja = crearSubcategoria("Baja Gama", celulares);

            // ── PRODUCTOS ────────────────────────────────────────

            // MacBook Pro
            crearProducto("MacBook Pro", "El portátil más potente de Apple con chip M3",
                    portatiles, portatilesTrabajo, apple,
                    "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/mbp14-spacegray-select-202301?wid=904&hei=840&fmt=jpeg",
                    new String[]{"14\" M3 8GB/512GB", "14\" M3 Pro 18GB/1TB", "16\" M3 Max 36GB/1TB"},
                    new double[]{1999.99, 2499.99, 3499.99},
                    new int[]{10, 8, 5},
                    new String[]{"MBP14-M3-512", "MBP14-M3PRO-1TB", "MBP16-M3MAX-1TB"});

            // MacBook Air
            crearProducto("MacBook Air", "El portátil ultradelgado de Apple con chip M2",
                    portatiles, portatilesUltrabook, apple,
                    "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/macbook-air-midnight-select-20220606?wid=904&hei=840&fmt=jpeg",
                    new String[]{"13\" M2 8GB/256GB", "13\" M2 8GB/512GB", "15\" M2 8GB/512GB"},
                    new double[]{1099.99, 1299.99, 1299.99},
                    new int[]{15, 12, 10},
                    new String[]{"MBA13-M2-256", "MBA13-M2-512", "MBA15-M2-512"});

            // Galaxy Book4
            crearProducto("Samsung Galaxy Book4", "Portátil premium de Samsung con Intel Core Ultra",
                    portatiles, portatilesTrabajo, samsung,
                    "https://m.media-amazon.com/images/I/71eqKJ4kIDL._AC_SX300_SY300_QL70_FMwebp_.jpg",
                    new String[]{"15\" Intel i5 16GB/512GB", "15\" Intel i7 16GB/1TB", "15\" Intel i9 32GB/1TB"},
                    new double[]{1199.99, 1499.99, 1899.99},
                    new int[]{8, 6, 4},
                    new String[]{"GB4-I5-512", "GB4-I7-1TB", "GB4-I9-1TB"});

            // Galaxy Book4 Gamer
            crearProducto("Samsung Galaxy Book4 Edge", "Portátil gamer de Samsung con RTX",
                    portatiles, portatilesGamer, samsung,
                    "https://m.media-amazon.com/images/I/61mhmhHtIpL._AC_SX300_SY300_QL70_FMwebp_.jpg",
                    new String[]{"RTX 4060 16GB RAM", "RTX 4070 32GB RAM"},
                    new double[]{1699.99, 2199.99},
                    new int[]{6, 4},
                    new String[]{"GB4E-4060-16", "GB4E-4070-32"});

            // PC Escritorio JTech
            crearProducto("JTech Workstation", "PC de escritorio para trabajo profesional",
                    pcs, pcEscritorio, jtech,
                    "https://computerworking.com.co/uploads/productos/big/sml_3612.jpg",
                    new String[]{"RTX 4060 / 16GB / 512GB SSD", "RTX 4070 / 32GB / 1TB SSD", "RTX 4090 / 64GB / 2TB SSD"},
                    new double[]{899.99, 1499.99, 2999.99},
                    new int[]{10, 8, 4},
                    new String[]{"JTW-4060-16-512", "JTW-4070-32-1TB", "JTW-4090-64-2TB"});

            // PC Gamer JTech
            crearProducto("JTech Beast", "PC Gamer de alto rendimiento",
                    pcs, pcGamer, jtech,
                    "https://computerworking.com.co/uploads/productos/big/sml_3612.jpg",
                    new String[]{"RTX 4060 Ti / 16GB / 1TB SSD", "RTX 4070 Ti / 32GB / 2TB SSD", "RTX 4090 Ti / 64GB / 4TB SSD"},
                    new double[]{1299.99, 1999.99, 3999.99},
                    new int[]{8, 6, 3},
                    new String[]{"JTB-4060TI-16", "JTB-4070TI-32", "JTB-4090TI-64"});

            // iPhone 15
            crearProducto("iPhone 15", "El smartphone más popular de Apple",
                    celulares, celularesAlta, apple,
                    "https://m.media-amazon.com/images/I/51PtFHUPjBL._AC_SY679_.jpg",
                    new String[]{"128GB", "256GB", "512GB"},
                    new double[]{799.99, 899.99, 1099.99},
                    new int[]{20, 15, 10},
                    new String[]{"IP15-128", "IP15-256", "IP15-512"});

            // iPhone 15 Pro
            crearProducto("iPhone 15 Pro", "El iPhone más avanzado con chip A17 Pro",
                    celulares, celularesAlta, apple,
                    "https://m.media-amazon.com/images/I/41oxP1+COkL._AC_.jpg",
                    new String[]{"256GB", "512GB", "1TB"},
                    new double[]{999.99, 1199.99, 1399.99},
                    new int[]{15, 10, 5},
                    new String[]{"IP15P-256", "IP15P-512", "IP15P-1TB"});

            // Galaxy S24
            crearProducto("Samsung Galaxy S24", "El smartphone insignia de Samsung",
                    celulares, celularesAlta, samsung,
                    "https://m.media-amazon.com/images/I/514D7HDpR1L._AC_SY300_SX300_QL70_FMwebp_.jpg",
                    new String[]{"128GB", "256GB"},
                    new double[]{799.99, 899.99},
                    new int[]{20, 15},
                    new String[]{"SGS24-128", "SGS24-256"});

            // Galaxy S24 Ultra
            crearProducto("Samsung Galaxy S24 Ultra", "El smartphone más potente de Samsung con S Pen",
                    celulares, celularesAlta, samsung,
                    "https://m.media-amazon.com/images/I/71WcjsOVOmL._AC_SY300_SX300_QL70_FMwebp_.jpg",
                    new String[]{"256GB", "512GB", "1TB"},
                    new double[]{1299.99, 1499.99, 1699.99},
                    new int[]{10, 8, 4},
                    new String[]{"SGS24U-256", "SGS24U-512", "SGS24U-1TB"});
        }
    }

    private Subcategoria crearSubcategoria(String nombre, Categoria categoria) {
        Subcategoria sub = new Subcategoria();
        sub.setNombre(nombre);
        sub.setCategoria(categoria);
        return subcategoriaRepository.save(sub);
    }

    private void crearProducto(String nombre, String descripcion,
                               Categoria categoria, Subcategoria subcategoria, Marca marca,
                               String imagenUrl,
                               String[] variantesNombres, double[] precios, int[] stocks, String[] skus) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setCategoria(categoria);
        producto.setSubcategoria(subcategoria);
        producto.setMarca(marca);
        producto.setActivo(true);
        productoRepository.save(producto);

        ImagenProducto imagen = new ImagenProducto();
        imagen.setUrl(imagenUrl);
        imagen.setEsPrincipal(true);
        imagen.setProducto(producto);
        imagenProductoRepository.save(imagen);

        for (int i = 0; i < variantesNombres.length; i++) {
            VarianteProducto variante = new VarianteProducto();
            variante.setNombre(variantesNombres[i]);
            variante.setPrecio(BigDecimal.valueOf(precios[i]));
            variante.setStock(stocks[i]);
            variante.setSku(skus[i]);
            variante.setProducto(producto);
            varianteProductoRepository.save(variante);
        }
    }
}