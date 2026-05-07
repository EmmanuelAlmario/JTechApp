package com.jtech.JtechApp.producto.repository;

import com.jtech.JtechApp.producto.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
    List<Producto> findByCategoriaIdAndActivoTrue(Long categoriaId);
    List<Producto> findByMarcaIdAndActivoTrue(Long marcaId);
    List<Producto> findByCategoriaIdAndMarcaIdAndActivoTrue(Long categoriaId, Long marcaId);
    boolean existsByNombreAndMarcaId(String nombre, Long marcaId);
}
