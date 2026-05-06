package com.jtech.JtechApp.categoria.exception;

public class CategoriaExistenteException extends RuntimeException {
    public CategoriaExistenteException(Long id) {
        super(String.format("La categoria con el ID %d no existe.", id));
    }
}
