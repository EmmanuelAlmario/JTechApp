package com.jtech.JtechApp.dto.response;

import java.math.BigDecimal;

public record DetalleOrdenResponseDTO(Long id, Long varianteProductoId, int cantidad, BigDecimal precioUnitario) {}
