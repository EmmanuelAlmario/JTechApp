package com.jtech.JtechApp.dto.response;

import com.jtech.JtechApp.orden.enums.EstadoOrden;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrdenResponseDTO(Long id, Long clienteId, String direccion, BigDecimal total, EstadoOrden estado,
                               LocalDateTime fecha, List<DetalleOrdenResponseDTO> detallesOrden) {}
