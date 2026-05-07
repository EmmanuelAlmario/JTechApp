package com.jtech.JtechApp.categoria.controller;

import com.jtech.JtechApp.categoria.entity.Categoria;
import com.jtech.JtechApp.categoria.entity.Subcategoria;
import com.jtech.JtechApp.categoria.service.CategoriaService;
import com.jtech.JtechApp.categoria.service.SubcategoriaService;
import com.jtech.JtechApp.dto.request.CreateCategoriaRequestDTO;
import com.jtech.JtechApp.dto.request.UpdateCategoriaRequestDTO;
import com.jtech.JtechApp.dto.response.CategoriaResponseDTO;
import com.jtech.JtechApp.dto.response.SubcategoriaResponseDTO;
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
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() { return ResponseEntity.ok(categoriaService.findAll().stream().map(this::toCategoriaResponse).toList()); }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(toCategoriaResponse(categoriaService.findById(id))); }
    @GetMapping("/{id}/subcategorias")
    public ResponseEntity<List<SubcategoriaResponseDTO>> findSubcategorias(@PathVariable Long id) { return ResponseEntity.ok(subcategoriaService.findByCategoria(id).stream().map(this::toSubcategoriaResponse).toList()); }
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> save(@RequestBody CreateCategoriaRequestDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(toCategoriaResponse(categoriaService.save(toCategoria(dto)))); }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id, @RequestBody UpdateCategoriaRequestDTO dto) { return ResponseEntity.ok(toCategoriaResponse(categoriaService.update(id, toCategoria(dto)))); }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { categoriaService.delete(id); return ResponseEntity.noContent().build(); }

    private Categoria toCategoria(CreateCategoriaRequestDTO dto){ Categoria c=new Categoria(); c.setNombre(dto.nombre()); c.setDescripcion(dto.descripcion()); return c; }
    private Categoria toCategoria(UpdateCategoriaRequestDTO dto){ Categoria c=new Categoria(); c.setNombre(dto.nombre()); c.setDescripcion(dto.descripcion()); return c; }
    private CategoriaResponseDTO toCategoriaResponse(Categoria c){ return new CategoriaResponseDTO(c.getId(), c.getNombre(), c.getDescripcion(), c.getSubcategorias()==null?List.of():c.getSubcategorias().stream().map(this::toSubcategoriaResponse).toList()); }
    private SubcategoriaResponseDTO toSubcategoriaResponse(Subcategoria s){ return new SubcategoriaResponseDTO(s.getId(), s.getNombre(), s.getCategoria()!=null?s.getCategoria().getId():null); }
}
