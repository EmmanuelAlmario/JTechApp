package com.jtech.JtechApp.producto.dto.request;

import java.math.BigDecimal;

public record CreateVarianteProductoRequestDTO(String nombre, BigDecimal precio, Integer stock, String sku) {}
