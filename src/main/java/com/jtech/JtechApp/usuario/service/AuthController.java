package com.jtech.JtechApp.usuario.controller;

import com.jtech.JtechApp.dto.request.LoginRequestDTO;
import com.jtech.JtechApp.dto.response.AuthResponseDTO;
import com.jtech.JtechApp.security.JwtUtil;
import com.jtech.JtechApp.usuario.entity.Usuario;
import com.jtech.JtechApp.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        Usuario usuario = usuarioRepository.findByEmail(request.email()).get();
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol());
        return ResponseEntity.ok(new AuthResponseDTO(token, usuario.getRol(), usuario.getNombre()));
    }
}
