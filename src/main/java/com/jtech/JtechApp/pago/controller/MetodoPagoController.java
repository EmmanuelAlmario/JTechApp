package com.jtech.JtechApp.pago.controller;

import com.jtech.JtechApp.pago.entity.MetodoPago;
import com.jtech.JtechApp.pago.service.MetodoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/metodos-pago")
@RequiredArgsConstructor
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPago>> findAll() {
        return ResponseEntity.ok(metodoPagoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> findById(@PathVariable Long id) {
        return ResponseEntity.ok(metodoPagoService.findById(id));
    }
}