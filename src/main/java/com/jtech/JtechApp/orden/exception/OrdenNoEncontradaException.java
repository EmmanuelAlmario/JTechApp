package com.jtech.JtechApp.orden.exception;

public class OrdenNoEncontradaException extends RuntimeException {
    public OrdenNoEncontradaException(Long id) {
        super(String.format("La orden con ID %d no encontrada."));
    }
}
