package com.jtech.JtechApp.usuario.service;

import com.jtech.JtechApp.usuario.entity.Administrador;
import com.jtech.JtechApp.usuario.entity.Cliente;
import com.jtech.JtechApp.usuario.entity.Usuario;
import com.jtech.JtechApp.usuario.enums.NivelAdmin;
import com.jtech.JtechApp.usuario.exception.EmailExistenteException;
import com.jtech.JtechApp.usuario.exception.EmailNoEncontradoException;
import com.jtech.JtechApp.usuario.exception.UsuarioNoEncontradoException;
import com.jtech.JtechApp.usuario.repository.AdministradorRepository;
import com.jtech.JtechApp.usuario.repository.ClienteRepository;
import com.jtech.JtechApp.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException());
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNoEncontradoException());
    }

    @Transactional
    public Cliente registrarCliente(Cliente cliente) {
        if (usuarioRepository.existsByEmail(cliente.getEmail())) {
            throw new EmailExistenteException();
        }
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Administrador registrarAdministrador(Administrador administrador) {
        if (usuarioRepository.existsByEmail(administrador.getEmail())) {
            throw new EmailExistenteException();
        }
        administrador.setPassword(passwordEncoder.encode(administrador.getPassword()));
        return administradorRepository.save(administrador);
    }

    public List<Administrador> findByNivel(NivelAdmin nivel) {
        return administradorRepository.findByNivel(nivel);
    }

    public List<Administrador> findAllAdministradores() {
        return administradorRepository.findAll();
    }

    @Transactional
    public Usuario update(Long usuarioId, Usuario usuarioActualizado) {
        Usuario usuario = findById(usuarioId);
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setEmail(usuarioActualizado.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void toggleActivo(Long usuarioId) {
        Usuario usuario = findById(usuarioId);
        usuario.setActivo(!usuario.getActivo());
        usuarioRepository.save(usuario);
    }
}