package com.jtech.JtechApp.producto.controller;

import com.jtech.JtechApp.producto.dto.request.CreateMarcaRequestDTO;
import com.jtech.JtechApp.producto.dto.request.UpdateMarcaRequestDTO;
import com.jtech.JtechApp.producto.dto.response.MarcaResponseDTO;
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
    public ResponseEntity<List<MarcaResponseDTO>> findAll() {
        return ResponseEntity.ok(marcaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MarcaResponseDTO> save(@RequestBody CreateMarcaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> update(@PathVariable Long id,
                                                   @RequestBody UpdateMarcaRequestDTO dto) {
        return ResponseEntity.ok(marcaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
