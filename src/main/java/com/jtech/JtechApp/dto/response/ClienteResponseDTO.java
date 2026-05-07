package com.jtech.JtechApp.dto.response;

public record ClienteResponseDTO(Long id, String nombre, String email, Boolean activo, String rol, String telefono, String direccion) {}
