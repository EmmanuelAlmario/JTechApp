package com.jtech.JtechApp.entity;

import com.jtech.JtechApp.enums.EstadoOrden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank
    @Column(nullable = false, length = 300)
    private String direccion;

    @Column(nullable = false, precision = 10, scale = 2)
    @Positive
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

}
