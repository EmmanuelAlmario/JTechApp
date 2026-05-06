package com.jtech.JtechApp.orden.repository;

import com.jtech.JtechApp.orden.entity.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    List<DetalleOrden> findByOrdenId(Long ordenId);
}
