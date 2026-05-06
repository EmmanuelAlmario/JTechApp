package com.jtech.JtechApp.producto.repository;

import com.jtech.JtechApp.producto.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Optional<Marca> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
