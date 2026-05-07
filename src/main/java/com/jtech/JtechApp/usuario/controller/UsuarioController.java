package com.jtech.JtechApp.usuario.controller;

import com.jtech.JtechApp.usuario.dto.request.RegisterAdministradorRequestDTO;
import com.jtech.JtechApp.usuario.dto.request.UpdateUsuarioRequestDTO;
import com.jtech.JtechApp.usuario.dto.response.AdministradorResponseDTO;
import com.jtech.JtechApp.usuario.dto.response.UsuarioResponseDTO;
import com.jtech.JtechApp.usuario.enums.NivelAdmin;
import com.jtech.JtechApp.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id,
                                                     @RequestBody UpdateUsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.update(id, dto));
    }

    @PatchMapping("/usuarios/{id}/toggle-activo")
    public ResponseEntity<Void> toggleActivo(@PathVariable Long id) {
        usuarioService.toggleActivo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/administradores")
    public ResponseEntity<List<AdministradorResponseDTO>> findAllAdministradores() {
        return ResponseEntity.ok(usuarioService.findAllAdministradores());
    }

    @GetMapping("/administradores/nivel")
    public ResponseEntity<List<AdministradorResponseDTO>> findByNivel(@RequestParam NivelAdmin nivel) {
        return ResponseEntity.ok(usuarioService.findByNivel(nivel));
    }

    @PostMapping("/administradores")
    public ResponseEntity<AdministradorResponseDTO> registrarAdministrador(
            @RequestBody RegisterAdministradorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.registrarAdministrador(dto));
    }

    @PatchMapping("/administradores/{id}/toggle-activo")
    public ResponseEntity<Void> toggleActivoAdmin(@PathVariable Long id) {
        usuarioService.toggleActivo(id);
        return ResponseEntity.noContent().build();
    }
}
