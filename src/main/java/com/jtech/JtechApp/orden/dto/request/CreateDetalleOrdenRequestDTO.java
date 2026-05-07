package com.jtech.JtechApp.orden.dto.request;

import java.math.BigDecimal;

public record CreateDetalleOrdenRequestDTO(Long varianteProductoId, int cantidad, BigDecimal precioUnitario) {}
