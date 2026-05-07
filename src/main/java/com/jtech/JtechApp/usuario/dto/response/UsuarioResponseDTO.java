package com.jtech.JtechApp.usuario.dto.response;

public record UsuarioResponseDTO(Long id, String nombre, String email, Boolean activo, String rol) {}
