package com.jtech.JtechApp.producto.controller;

import com.jtech.JtechApp.dto.request.CreateMarcaRequestDTO;
import com.jtech.JtechApp.dto.request.UpdateMarcaRequestDTO;
import com.jtech.JtechApp.dto.response.MarcaResponseDTO;
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
    public ResponseEntity<List<MarcaResponseDTO>> findAll() { return ResponseEntity.ok(marcaService.findAll().stream().map(this::toResponse).toList()); }
    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(toResponse(marcaService.findById(id))); }
    @PostMapping
    public ResponseEntity<MarcaResponseDTO> save(@RequestBody CreateMarcaRequestDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(marcaService.save(toMarca(dto)))); }
    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> update(@PathVariable Long id, @RequestBody UpdateMarcaRequestDTO dto) { return ResponseEntity.ok(toResponse(marcaService.update(id, toMarca(dto)))); }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { marcaService.delete(id); return ResponseEntity.noContent().build(); }
    private Marca toMarca(CreateMarcaRequestDTO dto){ Marca m=new Marca(); m.setNombre(dto.nombre()); m.setLogo(dto.logo()); return m; }
    private Marca toMarca(UpdateMarcaRequestDTO dto){ Marca m=new Marca(); m.setNombre(dto.nombre()); m.setLogo(dto.logo()); return m; }
    private MarcaResponseDTO toResponse(Marca m){ return new MarcaResponseDTO(m.getId(), m.getNombre(), m.getLogo()); }
}
