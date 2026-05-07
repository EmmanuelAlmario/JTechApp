package com.jtech.JtechApp.categoria.exception;

public class SubcategoriaNoEncontradaException extends RuntimeException {
    public SubcategoriaNoEncontradaException() {
        super("Subcategoria no fue encontrada.");
    }
}
