package com.jtech.JtechApp.orden.controller;

import com.jtech.JtechApp.orden.entity.Orden;
import com.jtech.JtechApp.orden.enums.EstadoOrden;
import com.jtech.JtechApp.orden.service.OrdenService;
import com.jtech.JtechApp.usuario.entity.Usuario;
import com.jtech.JtechApp.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/ordenes")
    public ResponseEntity<List<Orden>> findAll() {
        return ResponseEntity.ok(ordenService.findAll());
    }

    @GetMapping("/ordenes/filtrar")
    public ResponseEntity<List<Orden>> findByEstado(@RequestParam EstadoOrden estado) {
        return ResponseEntity.ok(ordenService.findByEstado(estado));
    }

    @PutMapping("/ordenes/{id}/estado")
    public ResponseEntity<Orden> cambiarEstado(@PathVariable Long id, @RequestParam EstadoOrden estado) {
        return ResponseEntity.ok(ordenService.cambiarEstado(id, estado));
    }

    @GetMapping("/mis-ordenes")
    public ResponseEntity<List<Orden>> misOrdenes(Authentication authentication) {
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).get();
        return ResponseEntity.ok(ordenService.findByClienteId(usuario.getId()));
    }

    @PostMapping("/ordenes")
    public ResponseEntity<Orden> crearOrden(@RequestBody Orden orden) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordenService.crearOrden(orden));
    }
}