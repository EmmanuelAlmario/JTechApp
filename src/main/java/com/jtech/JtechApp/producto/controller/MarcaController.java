package com.jtech.JtechApp.producto.controller;

import com.jtech.JtechApp.producto.entity.Marca;
import com.jtech.JtechApp.producto.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> findAll() {
        return ResponseEntity.ok(marcaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> findById(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Marca> save(@RequestBody Marca marca) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(marcaService.save(marca));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> update(@PathVariable Long id, @RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.update(id, marca));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
