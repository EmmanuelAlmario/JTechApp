package com.jtech.JtechApp.categoria.controller;

import com.jtech.JtechApp.categoria.entity.Subcategoria;
import com.jtech.JtechApp.categoria.service.SubcategoriaService;
import com.jtech.JtechApp.dto.request.UpdateSubcategoriaRequestDTO;
import com.jtech.JtechApp.dto.response.SubcategoriaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subcategorias")
@RequiredArgsConstructor
public class SubcategoriaController {
    private final SubcategoriaService subcategoriaService;
    @GetMapping
    public ResponseEntity<List<SubcategoriaResponseDTO>> findAll() { return ResponseEntity.ok(subcategoriaService.findAll().stream().map(this::toResponse).toList()); }
    @PutMapping("/{id}")
    public ResponseEntity<SubcategoriaResponseDTO> update(@PathVariable Long id, @RequestBody UpdateSubcategoriaRequestDTO dto) { Subcategoria s = new Subcategoria(); s.setNombre(dto.nombre()); return ResponseEntity.ok(toResponse(subcategoriaService.update(null, id, s))); }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { subcategoriaService.delete(id); return ResponseEntity.noContent().build(); }
    private SubcategoriaResponseDTO toResponse(Subcategoria s){ return new SubcategoriaResponseDTO(s.getId(), s.getNombre(), s.getCategoria()!=null?s.getCategoria().getId():null); }
}
