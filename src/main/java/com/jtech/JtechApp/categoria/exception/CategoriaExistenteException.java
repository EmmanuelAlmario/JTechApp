package com.jtech.JtechApp.categoria.exception;

public class CategoriaExistenteException extends RuntimeException {
    public CategoriaExistenteException() {
        super("Esta categoria ya existe.");
    }
}
