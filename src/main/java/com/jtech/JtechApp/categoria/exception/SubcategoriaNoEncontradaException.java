package com.jtech.JtechApp.categoria.exception;

public class SubcategoriaNoEncontradaException extends RuntimeException {
    public SubcategoriaNoEncontradaException(String nombre) {
        super(String.format("La categoria %s no fue encontrada.", nombre));
    }
}
