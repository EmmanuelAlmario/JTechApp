package com.jtech.JtechApp.categoria.controller;

import com.jtech.JtechApp.categoria.entity.Subcategoria;
import com.jtech.JtechApp.categoria.service.SubcategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subcategorias")
@RequiredArgsConstructor
public class SubcategoriaController {

    private final SubcategoriaService subcategoriaService;

    @GetMapping
    public ResponseEntity<List<Subcategoria>> findAll() {
        return ResponseEntity.ok(subcategoriaService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subcategoria> update(@PathVariable Long id, @RequestBody Subcategoria subcategoria) {
        return ResponseEntity.ok(subcategoriaService.update(null, id, subcategoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subcategoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}