package com.jtech.JtechApp.producto.controller;

import com.jtech.JtechApp.categoria.entity.Categoria;
import com.jtech.JtechApp.categoria.entity.Subcategoria;
import com.jtech.JtechApp.producto.dto.request.*;
import com.jtech.JtechApp.producto.dto.response.*;
import com.jtech.JtechApp.producto.entity.*;
import com.jtech.JtechApp.producto.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAllActivos() {
        List<Producto> productos = productoService.findAllActivos();
        return ResponseEntity.ok(toProductoResponseList(productos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        return ResponseEntity.ok(toResponse(producto));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponseDTO>> buscar(@RequestParam String nombre) {
        List<Producto> productos = productoService.buscar(nombre);
        return ResponseEntity.ok(toProductoResponseList(productos));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<ProductoResponseDTO>> filtrar(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Long marcaId) {

        List<Producto> productos;

        if (categoriaId != null && marcaId != null) {
            productos = productoService.findByCategoriaYMarca(categoriaId, marcaId);
        } else if (categoriaId != null) {
            productos = productoService.findByCategoria(categoriaId);
        } else if (marcaId != null) {
            productos = productoService.findByMarca(marcaId);
        } else {
            productos = productoService.findAllActivos();
        }

        return ResponseEntity.ok(toProductoResponseList(productos));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ProductoResponseDTO>> findAll() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(toProductoResponseList(productos));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> save(@RequestBody CreateProductoRequestDTO dto) {
        Producto producto = toEntity(dto);
        Producto createdProducto = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(createdProducto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id, @RequestBody UpdateProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());

        Producto updatedProducto = productoService.update(id, producto);
        return ResponseEntity.ok(toResponse(updatedProducto));
    }

    @PatchMapping("/{id}/toggle-activo")
    public ResponseEntity<ProductoResponseDTO> toggleActivo(@PathVariable Long id) {
        Producto producto = productoService.toggleActivo(id);
        return ResponseEntity.ok(toResponse(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private List<ProductoResponseDTO> toProductoResponseList(List<Producto> productos) {
        List<ProductoResponseDTO> response = new ArrayList<>();
        for (Producto producto : productos) {
            response.add(toResponse(producto));
        }
        return response;
    }

    private Producto toEntity(CreateProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());

        Categoria categoria = new Categoria();
        categoria.setId(dto.categoriaId());
        producto.setCategoria(categoria);

        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setId(dto.subcategoriaId());
        producto.setSubcategoria(subcategoria);

        Marca marca = new Marca();
        marca.setId(dto.marcaId());
        producto.setMarca(marca);

        if (dto.activo() == null) {
            producto.setActivo(Boolean.TRUE);
        } else {
            producto.setActivo(dto.activo());
        }

        if (dto.variantes() != null) {
            List<VarianteProducto> variantes = new ArrayList<>();
            for (CreateVarianteProductoRequestDTO varianteDto : dto.variantes()) {
                VarianteProducto variante = new VarianteProducto();
                variante.setProducto(producto);
                variante.setNombre(varianteDto.nombre());
                variante.setPrecio(varianteDto.precio());
                variante.setStock(varianteDto.stock());
                variante.setSku(varianteDto.sku());
                variantes.add(variante);
            }
            producto.setVariantes(variantes);
        }

        if (dto.imagenes() != null) {
            List<ImagenProducto> imagenes = new ArrayList<>();
            for (CreateImagenProductoRequestDTO imagenDto : dto.imagenes()) {
                ImagenProducto imagen = new ImagenProducto();
                imagen.setProducto(producto);
                imagen.setUrl(imagenDto.url());

                if (imagenDto.esPrincipal() == null) {
                    imagen.setEsPrincipal(Boolean.FALSE);
                } else {
                    imagen.setEsPrincipal(imagenDto.esPrincipal());
                }

                imagenes.add(imagen);
            }
            producto.setImagenes(imagenes);
        }

        return producto;
    }

    private ProductoResponseDTO toResponse(Producto producto) {
        Long categoriaId = null;
        if (producto.getCategoria() != null) {
            categoriaId = producto.getCategoria().getId();
        }

        Long subcategoriaId = null;
        if (producto.getSubcategoria() != null) {
            subcategoriaId = producto.getSubcategoria().getId();
        }

        Long marcaId = null;
        if (producto.getMarca() != null) {
            marcaId = producto.getMarca().getId();
        }

        List<VarianteProductoResponseDTO> variantesResponse = new ArrayList<>();
        if (producto.getVariantes() != null) {
            for (VarianteProducto variante : producto.getVariantes()) {
                variantesResponse.add(new VarianteProductoResponseDTO(
                        variante.getId(),
                        variante.getNombre(),
                        variante.getPrecio(),
                        variante.getStock(),
                        variante.getSku()
                ));
            }
        }

        List<ImagenProductoResponseDTO> imagenesResponse = new ArrayList<>();
        if (producto.getImagenes() != null) {
            for (ImagenProducto imagen : producto.getImagenes()) {
                imagenesResponse.add(new ImagenProductoResponseDTO(
                        imagen.getId(),
                        imagen.getUrl(),
                        imagen.getEsPrincipal()
                ));
            }
        }

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                categoriaId,
                subcategoriaId,
                marcaId,
                producto.getActivo(),
                variantesResponse,
                imagenesResponse
        );
    }
}
