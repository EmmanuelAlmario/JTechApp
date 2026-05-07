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
        List<Subcategoria> subcategorias = subcategoriaService.findAll();
        List<SubcategoriaResponseDTO> response = new ArrayList<>();

        for (Subcategoria subcategoria : subcategorias) {
            response.add(toResponse(subcategoria));
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoriaResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UpdateSubcategoriaRequestDTO dto) {

        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setNombre(dto.nombre());

        Subcategoria updatedSubcategoria = subcategoriaService.update(null, id, subcategoria);
        return ResponseEntity.ok(toResponse(updatedSubcategoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subcategoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private SubcategoriaResponseDTO toResponse(Subcategoria subcategoria) {
        Long categoriaId = null;

        if (subcategoria.getCategoria() != null) {
            categoriaId = subcategoria.getCategoria().getId();
        }

        return new SubcategoriaResponseDTO(
                subcategoria.getId(),
                subcategoria.getNombre(),
                categoriaId
        );
    }
}
