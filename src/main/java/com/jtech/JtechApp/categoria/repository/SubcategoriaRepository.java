package com.jtech.JtechApp.categoria.repository;

import com.jtech.JtechApp.categoria.entity.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
    List<Subcategoria> findByCategoriaId(Long categoriaId);
    boolean existsByNombreAndCategoriaId(String nombre, Long categoriaId);
}
