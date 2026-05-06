package com.jtech.JtechApp.orden.repository;

import com.jtech.JtechApp.orden.entity.Orden;
import com.jtech.JtechApp.orden.enums.EstadoOrden;
import com.jtech.JtechApp.usuario.entity.Administrador;
import com.jtech.JtechApp.usuario.enums.NivelAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByClienteId(Long clienteId);
    List<Orden> findByEstado(EstadoOrden estado);
    List<Orden> findByClienteIdOrderByFechaDesc(Long clienteId);;
}
