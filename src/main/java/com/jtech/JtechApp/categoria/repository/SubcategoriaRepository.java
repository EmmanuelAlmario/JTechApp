package com.jtech.JtechApp.categoria.repository;

import com.jtech.JtechApp.categoria.entity.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
    List<Subcategoria> findByCategoriaId(Long categoriaId);
    boolean existsByNombreAndCategoriaId(String nombre, Long categoriaId);
}
