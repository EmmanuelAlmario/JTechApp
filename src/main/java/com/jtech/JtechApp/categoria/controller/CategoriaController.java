package com.jtech.JtechApp.categoria.controller;

import com.jtech.JtechApp.categoria.dto.request.CreateCategoriaRequestDTO;
import com.jtech.JtechApp.categoria.dto.request.UpdateCategoriaRequestDTO;
import com.jtech.JtechApp.categoria.dto.response.CategoriaResponseDTO;
import com.jtech.JtechApp.categoria.dto.response.SubcategoriaResponseDTO;
import com.jtech.JtechApp.categoria.entity.Categoria;
import com.jtech.JtechApp.categoria.entity.Subcategoria;
import com.jtech.JtechApp.categoria.service.CategoriaService;
import com.jtech.JtechApp.categoria.service.SubcategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
        List<Categoria> categorias = categoriaService.findAll();
        List<CategoriaResponseDTO> response = new ArrayList<>();

        for (Categoria categoria : categorias) {
            response.add(toCategoriaResponse(categoria));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(toCategoriaResponse(categoria));
    }

    @GetMapping("/{id}/subcategorias")
    public ResponseEntity<List<SubcategoriaResponseDTO>> findSubcategorias(@PathVariable Long id) {
        List<Subcategoria> subcategorias = subcategoriaService.findByCategoria(id);
        List<SubcategoriaResponseDTO> response = new ArrayList<>();

        for (Subcategoria subcategoria : subcategorias) {
            response.add(toSubcategoriaResponse(subcategoria));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> save(@RequestBody CreateCategoriaRequestDTO dto) {
        Categoria categoria = toCategoria(dto);
        Categoria savedCategoria = categoriaService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(toCategoriaResponse(savedCategoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id, @RequestBody UpdateCategoriaRequestDTO dto) {
        Categoria categoria = toCategoria(dto);
        Categoria updatedCategoria = categoriaService.update(id, categoria);
        return ResponseEntity.ok(toCategoriaResponse(updatedCategoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Categoria toCategoria(CreateCategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        return categoria;
    }

    private Categoria toCategoria(UpdateCategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        return categoria;
    }

    private CategoriaResponseDTO toCategoriaResponse(Categoria categoria) {
        List<SubcategoriaResponseDTO> subcategoriasResponse = new ArrayList<>();

        if (categoria.getSubcategorias() != null) {
            for (Subcategoria subcategoria : categoria.getSubcategorias()) {
                subcategoriasResponse.add(toSubcategoriaResponse(subcategoria));
            }
        }

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                subcategoriasResponse
        );
    }

    private SubcategoriaResponseDTO toSubcategoriaResponse(Subcategoria subcategoria) {
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
