package com.jtech.JtechApp.orden.dto.request;

import java.util.List;

public record CreateOrdenRequestDTO(Long clienteId, String direccion, List<CreateDetalleOrdenRequestDTO> detallesOrden) {}
