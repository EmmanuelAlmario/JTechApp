package com.jtech.JtechApp.categoria.exception;

public class CategoriaNoEncontradaException extends RuntimeException {
    public CategoriaNoEncontradaException(String message) {
        super("Esta categoria no existe.");
    }
}
