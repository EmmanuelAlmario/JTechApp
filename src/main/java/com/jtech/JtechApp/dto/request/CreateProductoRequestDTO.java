package com.jtech.JtechApp.dto.request;

import java.util.List;

public record CreateProductoRequestDTO(
        String nombre,
        String descripcion,
        Long categoriaId,
        Long subcategoriaId,
        Long marcaId,
        Boolean activo,
        List<CreateVarianteProductoRequestDTO> variantes,
        List<CreateImagenProductoRequestDTO> imagenes
) {}
