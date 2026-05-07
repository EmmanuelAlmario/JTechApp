package com.jtech.JtechApp.orden.repository;

import com.jtech.JtechApp.orden.entity.Orden;
import com.jtech.JtechApp.orden.enums.EstadoOrden;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByClienteId(Long clienteId);
    List<Orden> findByEstado(EstadoOrden estado);
    List<Orden> findByClienteIdOrderByFechaDesc(Long clienteId);
    @Query("SELECT MONTH(o.fecha), SUM(o.total) FROM Orden o WHERE YEAR(o.fecha) = :anio GROUP BY MONTH(o.fecha)")
    List<Object[]> getIngresosPorMes(@Param("year") int anio);
}
