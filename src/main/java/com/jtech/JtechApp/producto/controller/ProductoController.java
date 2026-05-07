package com.jtech.JtechApp.producto.controller;

import com.jtech.JtechApp.categoria.entity.Categoria;
import com.jtech.JtechApp.categoria.entity.Subcategoria;
import com.jtech.JtechApp.dto.request.*;
import com.jtech.JtechApp.dto.response.*;
import com.jtech.JtechApp.producto.entity.*;
import com.jtech.JtechApp.producto.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;
    @GetMapping public ResponseEntity<List<ProductoResponseDTO>> findAllActivos(){ return ResponseEntity.ok(productoService.findAllActivos().stream().map(this::toResponse).toList());}
    @GetMapping("/{id}") public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id){ return ResponseEntity.ok(toResponse(productoService.findById(id)));}
    @GetMapping("/buscar") public ResponseEntity<List<ProductoResponseDTO>> buscar(@RequestParam String nombre){ return ResponseEntity.ok(productoService.buscar(nombre).stream().map(this::toResponse).toList());}
    @GetMapping("/filtrar") public ResponseEntity<List<ProductoResponseDTO>> filtrar(@RequestParam(required = false) Long categoriaId,@RequestParam(required = false) Long marcaId){ List<Producto> p = categoriaId != null && marcaId != null ? productoService.findByCategoriaYMarca(categoriaId, marcaId) : categoriaId != null ? productoService.findByCategoria(categoriaId) : marcaId != null ? productoService.findByMarca(marcaId) : productoService.findAllActivos(); return ResponseEntity.ok(p.stream().map(this::toResponse).toList()); }
    @GetMapping("/admin") public ResponseEntity<List<ProductoResponseDTO>> findAll(){ return ResponseEntity.ok(productoService.findAll().stream().map(this::toResponse).toList()); }
    @PostMapping public ResponseEntity<ProductoResponseDTO> save(@RequestBody CreateProductoRequestDTO dto){ return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(productoService.save(toEntity(dto)))); }
    @PutMapping("/{id}") public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id, @RequestBody UpdateProductoRequestDTO dto){ Producto p = new Producto(); p.setNombre(dto.nombre()); p.setDescripcion(dto.descripcion()); return ResponseEntity.ok(toResponse(productoService.update(id, p))); }
    @PatchMapping("/{id}/toggle-activo") public ResponseEntity<ProductoResponseDTO> toggleActivo(@PathVariable Long id){ return ResponseEntity.ok(toResponse(productoService.toggleActivo(id))); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){ productoService.delete(id); return ResponseEntity.noContent().build(); }

    private Producto toEntity(CreateProductoRequestDTO dto){ Producto p = new Producto(); p.setNombre(dto.nombre()); p.setDescripcion(dto.descripcion()); Categoria c=new Categoria(); c.setId(dto.categoriaId()); p.setCategoria(c); Subcategoria s=new Subcategoria(); s.setId(dto.subcategoriaId()); p.setSubcategoria(s); Marca m=new Marca(); m.setId(dto.marcaId()); p.setMarca(m); p.setActivo(dto.activo()==null?Boolean.TRUE:dto.activo()); if(dto.variantes()!=null){ p.setVariantes(dto.variantes().stream().map(v->{ VarianteProducto vp=new VarianteProducto(); vp.setProducto(p); vp.setNombre(v.nombre()); vp.setPrecio(v.precio()); vp.setStock(v.stock()); vp.setSku(v.sku()); return vp;}).toList()); } if(dto.imagenes()!=null){ p.setImagenes(dto.imagenes().stream().map(i->{ ImagenProducto ip=new ImagenProducto(); ip.setProducto(p); ip.setUrl(i.url()); ip.setEsPrincipal(i.esPrincipal()==null?Boolean.FALSE:i.esPrincipal()); return ip;}).toList()); } return p; }
    private ProductoResponseDTO toResponse(Producto p){ return new ProductoResponseDTO(p.getId(), p.getNombre(), p.getDescripcion(), p.getCategoria()!=null?p.getCategoria().getId():null, p.getSubcategoria()!=null?p.getSubcategoria().getId():null, p.getMarca()!=null?p.getMarca().getId():null, p.getActivo(), p.getVariantes()==null?List.of():p.getVariantes().stream().map(v->new VarianteProductoResponseDTO(v.getId(),v.getNombre(),v.getPrecio(),v.getStock(),v.getSku())).toList(), p.getImagenes()==null?List.of():p.getImagenes().stream().map(i->new ImagenProductoResponseDTO(i.getId(),i.getUrl(),i.getEsPrincipal())).toList()); }
}
