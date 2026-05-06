package com.jtech.JtechApp.orden.entity;

import com.jtech.JtechApp.usuario.entity.Cliente;
import com.jtech.JtechApp.orden.enums.EstadoOrden;
import com.jtech.JtechApp.usuario.entity.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotBlank
    @Column(nullable = false, length = 300)
    private String direccion;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    @PositiveOrZero
    private BigDecimal total;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrden> detallesOrden = new ArrayList<>();
}
