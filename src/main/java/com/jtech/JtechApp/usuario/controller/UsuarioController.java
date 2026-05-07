package com.jtech.JtechApp.usuario.controller;

import com.jtech.JtechApp.dto.request.RegisterAdministradorRequestDTO;
import com.jtech.JtechApp.dto.request.RegisterClienteRequestDTO;
import com.jtech.JtechApp.dto.request.UpdateUsuarioRequestDTO;
import com.jtech.JtechApp.dto.response.AdministradorResponseDTO;
import com.jtech.JtechApp.dto.response.ClienteResponseDTO;
import com.jtech.JtechApp.dto.response.UsuarioResponseDTO;
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
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@RequestBody RegisterClienteRequestDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(toClienteResponse(usuarioService.registrarCliente(toCliente(dto)))); }
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() { return ResponseEntity.ok(usuarioService.findAll().stream().map(this::toUsuarioResponse).toList()); }
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) { return ResponseEntity.ok(toUsuarioResponse(usuarioService.findById(id))); }
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @RequestBody UpdateUsuarioRequestDTO dto) { Usuario u = new Cliente(); u.setNombre(dto.nombre()); u.setEmail(dto.email()); return ResponseEntity.ok(toUsuarioResponse(usuarioService.update(id, u))); }
    @PatchMapping("/usuarios/{id}/toggle-activo")
    public ResponseEntity<Void> toggleActivo(@PathVariable Long id) { usuarioService.toggleActivo(id); return ResponseEntity.noContent().build(); }
    @PostMapping("/administradores")
    public ResponseEntity<AdministradorResponseDTO> registrarAdministrador(@RequestBody RegisterAdministradorRequestDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(toAdminResponse(usuarioService.registrarAdministrador(toAdmin(dto)))); }
    @GetMapping("/administradores")
    public ResponseEntity<List<AdministradorResponseDTO>> findAllAdministradores() { return ResponseEntity.ok(usuarioService.findAllAdministradores().stream().map(this::toAdminResponse).toList()); }

    private Cliente toCliente(RegisterClienteRequestDTO dto){ Cliente c=new Cliente(); c.setNombre(dto.nombre()); c.setEmail(dto.email()); c.setPassword(dto.password()); c.setTelefono(dto.telefono()); c.setDireccion(dto.direccion()); return c; }
    private Administrador toAdmin(RegisterAdministradorRequestDTO dto){ Administrador a=new Administrador(); a.setNombre(dto.nombre()); a.setEmail(dto.email()); a.setPassword(dto.password()); a.setCargo(dto.cargo()); a.setNivel(dto.nivel()); return a; }
    private UsuarioResponseDTO toUsuarioResponse(Usuario u){ return new UsuarioResponseDTO(u.getId(),u.getNombre(),u.getEmail(),u.getActivo(),u.getRol()); }
    private ClienteResponseDTO toClienteResponse(Cliente c){ return new ClienteResponseDTO(c.getId(),c.getNombre(),c.getEmail(),c.getActivo(),c.getRol(),c.getTelefono(),c.getDireccion()); }
    private AdministradorResponseDTO toAdminResponse(Administrador a){ return new AdministradorResponseDTO(a.getId(),a.getNombre(),a.getEmail(),a.getActivo(),a.getRol(),a.getCargo(),a.getNivel()); }
}
