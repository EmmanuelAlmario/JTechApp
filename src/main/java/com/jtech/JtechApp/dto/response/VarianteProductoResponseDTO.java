package com.jtech.JtechApp.dto.response;

import java.math.BigDecimal;

public record VarianteProductoResponseDTO(Long id, String nombre, BigDecimal precio, Integer stock, String sku) {}
