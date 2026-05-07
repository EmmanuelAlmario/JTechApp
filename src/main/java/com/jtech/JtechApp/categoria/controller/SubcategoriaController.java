package com.jtech.JtechApp.categoria.controller;

import com.jtech.JtechApp.categoria.dto.request.UpdateSubcategoriaRequestDTO;
import com.jtech.JtechApp.categoria.dto.response.SubcategoriaResponseDTO;
import com.jtech.JtechApp.categoria.entity.Subcategoria;
import com.jtech.JtechApp.categoria.service.SubcategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/subcategorias")
@RequiredArgsConstructor
public class SubcategoriaController {

    private final SubcategoriaService subcategoriaService;

    @GetMapping
    public ResponseEntity<List<SubcategoriaResponseDTO>> findAll() {
        return ResponseEntity.ok(subcategoriaService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoriaResponseDTO> update(@PathVariable Long id,
                                                          @RequestBody UpdateSubcategoriaRequestDTO dto) {
        return ResponseEntity.ok(subcategoriaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subcategoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}