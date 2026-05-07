package com.jtech.JtechApp.dto.response;

import java.util.List;

public record CategoriaResponseDTO(Long id, String nombre, String descripcion, List<SubcategoriaResponseDTO> subcategorias) {}
