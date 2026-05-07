package com.jtech.JtechApp.producto.controller;

import com.jtech.JtechApp.producto.dto.request.CreateProductoRequestDTO;
import com.jtech.JtechApp.producto.dto.request.CreateVarianteProductoRequestDTO;
import com.jtech.JtechApp.producto.dto.request.UpdateProductoRequestDTO;
import com.jtech.JtechApp.producto.dto.response.ProductoResponseDTO;
import com.jtech.JtechApp.producto.dto.response.VarianteProductoResponseDTO;
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

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAllActivos() {
        return ResponseEntity.ok(productoService.findAllActivos());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ProductoResponseDTO>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponseDTO>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscar(nombre));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<ProductoResponseDTO>> filtrar(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Long marcaId) {
        if (categoriaId != null && marcaId != null) {
            return ResponseEntity.ok(productoService.findByCategoriaYMarca(categoriaId, marcaId));
        } else if (categoriaId != null) {
            return ResponseEntity.ok(productoService.findByCategoria(categoriaId));
        } else if (marcaId != null) {
            return ResponseEntity.ok(productoService.findByMarca(marcaId));
        }
        return ResponseEntity.ok(productoService.findAllActivos());
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> save(@RequestBody CreateProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody UpdateProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.update(id, dto));
    }

    @PatchMapping("/{id}/toggle-activo")
    public ResponseEntity<ProductoResponseDTO> toggleActivo(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.toggleActivo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/variantes")
    public ResponseEntity<List<VarianteProductoResponseDTO>> findVariantes(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findVariantes(id));
    }

    @PostMapping("/{id}/variantes")
    public ResponseEntity<VarianteProductoResponseDTO> addVariante(@PathVariable Long id,
                                                                   @RequestBody CreateVarianteProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.addVariante(id, dto));
    }

    @PutMapping("/variantes/{varianteId}")
    public ResponseEntity<VarianteProductoResponseDTO> updateVariante(@PathVariable Long varianteId,
                                                                      @RequestBody CreateVarianteProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.updateVariante(varianteId, dto));
    }

    @DeleteMapping("/variantes/{varianteId}")
    public ResponseEntity<Void> deleteVariante(@PathVariable Long varianteId) {
        productoService.deleteVariante(varianteId);
        return ResponseEntity.noContent().build();
    }
}