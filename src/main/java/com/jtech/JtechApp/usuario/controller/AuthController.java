package com.jtech.JtechApp.usuario.controller;

import com.jtech.JtechApp.usuario.dto.request.LoginRequestDTO;
import com.jtech.JtechApp.usuario.dto.request.RegisterClienteRequestDTO;
import com.jtech.JtechApp.usuario.dto.response.AuthResponseDTO;
import com.jtech.JtechApp.security.JwtUtil;
import com.jtech.JtechApp.usuario.dto.response.ClienteResponseDTO;
import com.jtech.JtechApp.usuario.entity.Usuario;
import com.jtech.JtechApp.usuario.repository.UsuarioRepository;
import com.jtech.JtechApp.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        Usuario usuario = usuarioRepository.findByEmail(dto.email()).get();
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol());

        return ResponseEntity.ok(new AuthResponseDTO(token, usuario.getRol(), usuario.getNombre()));
    }

    @PostMapping("/register")
    public ResponseEntity<ClienteResponseDTO> register(@RequestBody RegisterClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.registrarCliente(dto));
    }
}
