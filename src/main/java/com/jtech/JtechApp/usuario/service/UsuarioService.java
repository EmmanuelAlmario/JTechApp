package com.jtech.JtechApp.usuario.service;

import com.jtech.JtechApp.usuario.dto.request.RegisterAdministradorRequestDTO;
import com.jtech.JtechApp.usuario.dto.request.RegisterClienteRequestDTO;
import com.jtech.JtechApp.usuario.dto.request.UpdateUsuarioRequestDTO;
import com.jtech.JtechApp.usuario.dto.response.AdministradorResponseDTO;
import com.jtech.JtechApp.usuario.dto.response.ClienteResponseDTO;
import com.jtech.JtechApp.usuario.dto.response.UsuarioResponseDTO;
import com.jtech.JtechApp.usuario.entity.Administrador;
import com.jtech.JtechApp.usuario.entity.Cliente;
import com.jtech.JtechApp.usuario.entity.Usuario;
import com.jtech.JtechApp.usuario.enums.NivelAdmin;
import com.jtech.JtechApp.usuario.exception.EmailExistenteException;
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

    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toUsuarioResponse)
                .toList();
    }

    public UsuarioResponseDTO findById(Long usuarioId) {
        return toUsuarioResponse(usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException()));
    }

    public List<AdministradorResponseDTO> findAllAdministradores() {
        return administradorRepository.findAll().stream()
                .map(this::toAdministradorResponse)
                .toList();
    }

    public List<AdministradorResponseDTO> findByNivel(NivelAdmin nivel) {
        return administradorRepository.findByNivel(nivel).stream()
                .map(this::toAdministradorResponse)
                .toList();
    }

    @Transactional
    public ClienteResponseDTO registrarCliente(RegisterClienteRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailExistenteException();
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.nombre());
        cliente.setEmail(dto.email());
        cliente.setPassword(passwordEncoder.encode(dto.password()));
        cliente.setTelefono(dto.telefono());
        cliente.setDireccion(dto.direccion());
        return toClienteResponse(clienteRepository.save(cliente));
    }

    @Transactional
    public AdministradorResponseDTO registrarAdministrador(RegisterAdministradorRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailExistenteException();
        }
        Administrador administrador = new Administrador();
        administrador.setNombre(dto.nombre());
        administrador.setEmail(dto.email());
        administrador.setPassword(passwordEncoder.encode(dto.password()));
        administrador.setCargo(dto.cargo());
        administrador.setNivel(dto.nivel() != null ? dto.nivel() : NivelAdmin.ADMIN);
        return toAdministradorResponse(administradorRepository.save(administrador));
    }

    @Transactional
    public UsuarioResponseDTO update(Long usuarioId, UpdateUsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException());
        usuario.setNombre(dto.nombre());
        usuario.setEmail(dto.email());
        return toUsuarioResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void toggleActivo(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException());
        usuario.setActivo(!usuario.getActivo());
        usuarioRepository.save(usuario);
    }

    private UsuarioResponseDTO toUsuarioResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getActivo(),
                usuario.getRol()
        );
    }

    private ClienteResponseDTO toClienteResponse(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getActivo(),
                cliente.getTelefono(),
                cliente.getDireccion()
        );
    }

    private AdministradorResponseDTO toAdministradorResponse(Administrador administrador) {
        return new AdministradorResponseDTO(
                administrador.getId(),
                administrador.getNombre(),
                administrador.getEmail(),
                administrador.getActivo(),
                administrador.getRol(),
                administrador.getCargo(),
                administrador.getNivel()
        );
    }
}