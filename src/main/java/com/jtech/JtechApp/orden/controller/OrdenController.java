package com.jtech.JtechApp.orden.controller;

import com.jtech.JtechApp.orden.dto.request.CreateDetalleOrdenRequestDTO;
import com.jtech.JtechApp.orden.dto.request.CreateOrdenRequestDTO;
import com.jtech.JtechApp.orden.dto.response.DetalleOrdenResponseDTO;
import com.jtech.JtechApp.orden.dto.response.OrdenResponseDTO;
import com.jtech.JtechApp.orden.entity.DetalleOrden;
import com.jtech.JtechApp.orden.entity.Orden;
import com.jtech.JtechApp.orden.enums.EstadoOrden;
import com.jtech.JtechApp.orden.service.OrdenService;
import com.jtech.JtechApp.producto.entity.VarianteProducto;
import com.jtech.JtechApp.usuario.entity.Cliente;
import com.jtech.JtechApp.usuario.entity.Usuario;
import com.jtech.JtechApp.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/ordenes")
    public ResponseEntity<List<OrdenResponseDTO>> findAll() {
        return ResponseEntity.ok(ordenService.findAll());
    }

    @GetMapping("/ordenes/filtrar")
    public ResponseEntity<List<OrdenResponseDTO>> findByEstado(@RequestParam EstadoOrden estado) {
        return ResponseEntity.ok(ordenService.findByEstado(estado));
    }

    @PutMapping("/ordenes/{id}/estado")
    public ResponseEntity<OrdenResponseDTO> cambiarEstado(@PathVariable Long id,
                                                          @RequestParam EstadoOrden estado) {
        return ResponseEntity.ok(ordenService.cambiarEstado(id, estado));
    }

    @GetMapping("/mis-ordenes")
    public ResponseEntity<List<OrdenResponseDTO>> misOrdenes(Authentication authentication) {
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).get();
        return ResponseEntity.ok(ordenService.findByClienteId(usuario.getId()));
    }

    @PostMapping("/ordenes")
    public ResponseEntity<OrdenResponseDTO> crearOrden(@RequestBody CreateOrdenRequestDTO dto,
                                                       Authentication authentication) {
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).get();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordenService.crearOrden(dto, usuario.getId()));
    }
}