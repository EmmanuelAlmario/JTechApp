package com.jtech.JtechApp.categoria.controller;

import com.jtech.JtechApp.categoria.dto.request.CreateCategoriaRequestDTO;
import com.jtech.JtechApp.categoria.dto.request.UpdateCategoriaRequestDTO;
import com.jtech.JtechApp.categoria.dto.request.UpdateSubcategoriaRequestDTO;
import com.jtech.JtechApp.categoria.dto.response.CategoriaResponseDTO;
import com.jtech.JtechApp.categoria.dto.response.SubcategoriaResponseDTO;
import com.jtech.JtechApp.categoria.service.CategoriaService;
import com.jtech.JtechApp.categoria.service.SubcategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @GetMapping("/{id}/subcategorias")
    public ResponseEntity<List<SubcategoriaResponseDTO>> findSubcategorias(@PathVariable Long id) {
        return ResponseEntity.ok(subcategoriaService.findByCategoria(id));
    }

    @PostMapping("/{id}/subcategorias")
    public ResponseEntity<SubcategoriaResponseDTO> saveSubcategoria(@PathVariable Long id,
                                                                    @RequestBody UpdateSubcategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subcategoriaService.save(id, dto));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> save(@RequestBody CreateCategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id,
                                                       @RequestBody UpdateCategoriaRequestDTO dto) {
        return ResponseEntity.ok(categoriaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
