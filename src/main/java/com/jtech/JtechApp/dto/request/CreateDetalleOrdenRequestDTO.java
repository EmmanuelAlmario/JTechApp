package com.jtech.JtechApp.dto.request;

import java.math.BigDecimal;

public record CreateDetalleOrdenRequestDTO(Long varianteProductoId, int cantidad, BigDecimal precioUnitario) {}
