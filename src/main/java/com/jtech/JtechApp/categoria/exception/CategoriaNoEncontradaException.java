package com.jtech.JtechApp.categoria.exception;

public class CategoriaNoEncontradaException extends RuntimeException {
    public CategoriaNoEncontradaException(Long id) {
        super(String.format("La categoria con el id %d no fue encontrada.", id));
    }
}
