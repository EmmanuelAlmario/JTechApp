package com.jtech.JtechApp.orden.exception;

public class VarianteProductoNoEncontradaException extends RuntimeException {
    public VarianteProductoNoEncontradaException() {
        super("Variante no encontrada.");
    }
}
