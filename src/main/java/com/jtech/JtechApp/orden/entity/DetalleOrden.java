package com.jtech.JtechApp.orden.entity;

import com.jtech.JtechApp.producto.entity.VarianteProducto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "detalle_ordenes")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "variante_id", nullable = false)
    private VarianteProducto varianteProducto;

    @Positive
    @Column(nullable = false)
    private int cantidad;


    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
}
