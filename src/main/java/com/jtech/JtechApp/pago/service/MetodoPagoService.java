package com.jtech.JtechApp.pago.service;

import com.jtech.JtechApp.pago.entity.MetodoPago;
import com.jtech.JtechApp.pago.exception.MetodoPagoNoEncontradoException;
import com.jtech.JtechApp.pago.repository.MetodoPagoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPago> findAll() {
        return metodoPagoRepository.findAll();
    }

    public MetodoPago findById(Long metodoPagoId) {
        return metodoPagoRepository.findById(metodoPagoId)
                .orElseThrow(() -> new MetodoPagoNoEncontradoException("Método de pago no encontrado con id: " + metodoPagoId));
    }

    @Transactional
    public MetodoPago save(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    @Transactional
    public void delete(Long metodoPagoId) {
        findById(metodoPagoId);
        metodoPagoRepository.deleteById(metodoPagoId);
    }
}