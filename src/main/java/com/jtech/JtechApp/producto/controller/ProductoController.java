package com.jtech.JtechApp.producto.controller;

import com.jtech.JtechApp.producto.entity.Producto;
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
    public ResponseEntity<List<Producto>> findAllActivos() {
        return ResponseEntity.ok(productoService.findAllActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscar(nombre));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Producto>> filtrar(
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

    // Admin — todos incluso inactivos
    @GetMapping("/admin")
    public ResponseEntity<List<Producto>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @PostMapping
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.update(id, producto));
    }

    @PatchMapping("/{id}/toggle-activo")
    public ResponseEntity<Producto> toggleActivo(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.toggleActivo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}