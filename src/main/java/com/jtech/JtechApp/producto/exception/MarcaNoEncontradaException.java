package com.jtech.JtechApp.producto.exception;

public class MarcaNoEncontradaException extends RuntimeException {
    public MarcaNoEncontradaException() {
        super("Marca no fue encontrada.");
    }
}
