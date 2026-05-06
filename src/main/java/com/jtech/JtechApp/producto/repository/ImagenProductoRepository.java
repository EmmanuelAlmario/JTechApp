package com.jtech.JtechApp.producto.repository;

import com.jtech.JtechApp.producto.entity.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Long> {
    List<ImagenProducto> findByProductoId(Long productoId);
    Optional<ImagenProducto> findByProductoIdAndEsPrincipalTrue(Long productoId);
}
