package com.jtech.JtechApp.dto.request;

import com.jtech.JtechApp.usuario.enums.NivelAdmin;

public record RegisterAdministradorRequestDTO(String nombre, String email, String password, String cargo, NivelAdmin nivel) {}
