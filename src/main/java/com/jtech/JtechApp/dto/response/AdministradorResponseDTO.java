package com.jtech.JtechApp.dto.response;

import com.jtech.JtechApp.usuario.enums.NivelAdmin;

public record AdministradorResponseDTO(Long id, String nombre, String email, Boolean activo, String rol, String cargo, NivelAdmin nivel) {}
