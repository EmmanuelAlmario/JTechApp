package com.jtech.JtechApp.usuario.controller;

import com.jtech.JtechApp.usuario.entity.Administrador;
import com.jtech.JtechApp.usuario.entity.Cliente;
import com.jtech.JtechApp.usuario.entity.Usuario;
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

    @PostMapping("/auth/register")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.registrarCliente(cliente));
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }

    @PatchMapping("/usuarios/{id}/toggle-activo")
    public ResponseEntity<Void> toggleActivo(@PathVariable Long id) {
        usuarioService.toggleActivo(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/administradores")
    public ResponseEntity<Administrador> registrarAdministrador(@RequestBody Administrador administrador) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.registrarAdministrador(administrador));
    }

    @GetMapping("/administradores")
    public ResponseEntity<List<Administrador>> findAllAdministradores() {
        return ResponseEntity.ok(usuarioService.findAllAdministradores());
    }
}