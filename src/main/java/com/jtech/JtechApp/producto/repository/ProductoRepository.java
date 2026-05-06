package com.jtech.JtechApp.producto.repository;

import com.jtech.JtechApp.producto.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
    List<Producto> findByCategoriaIdAndActivoTrue(Long categoriaId);
    List<Producto> findByMarcaIdAndActivoTrue(Long marcaId);
    List<Producto> findByCategoriaIdAndMarcaIdAndActivoTrue(Long categoriaId, Long marcaId);
    boolean existsByNombreAndMarcaId(String nombre, Long marcaId);
}
