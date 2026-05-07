package com.jtech.JtechApp.dto.response;

import java.util.List;

public record ProductoResponseDTO(Long id, String nombre, String descripcion, Long categoriaId, Long subcategoriaId,
                                  Long marcaId, Boolean activo, List<VarianteProductoResponseDTO> variantes,
                                  List<ImagenProductoResponseDTO> imagenes) {}
