package com.jtech.JtechApp.orden.service;

import com.jtech.JtechApp.orden.entity.Orden;
import com.jtech.JtechApp.orden.enums.EstadoOrden;
import com.jtech.JtechApp.orden.exception.OrdenNoEncontradaException;
import com.jtech.JtechApp.orden.repository.OrdenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {

    private final OrdenRepository ordenRepository;

    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    public Orden findById(Long ordenId) {
        return ordenRepository.findById(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
    }

    public List<Orden> findByClienteId(Long clienteId) {
        return ordenRepository.findByClienteIdOrderByFechaDesc(clienteId); // mejor usar este, ordena por fecha
    }

    @Transactional
    public Orden crearOrden(Orden orden) {
        BigDecimal total = orden.getDetallesOrden().stream()
                .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orden.setTotal (total);

        return ordenRepository.save(orden);
    }
    @Transactional
    public Orden cambiarEstado(Long ordenId, EstadoOrden nuevoEstado) {
        Orden orden = findById(ordenId);
        orden.setEstado(nuevoEstado);
        return ordenRepository.save(orden);
    }

    @Transactional
    public void delete(Long ordenId) {
        findById(ordenId);
        ordenRepository.deleteById(ordenId);
    }

    public List<Orden> findByEstado(EstadoOrden estado) {
        return ordenRepository.findByEstado(estado);
    }
}