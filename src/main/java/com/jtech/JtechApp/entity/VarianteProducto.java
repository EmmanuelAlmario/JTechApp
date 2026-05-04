package com.jtech.JtechApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "variantes_producto")
public class VarianteProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotBlank
    @Column(length = 150, nullable = false)
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    @Positive
    private BigDecimal precio;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer stock;

    @NotNull
    @Column(length = 100)
    private String sku;
}
