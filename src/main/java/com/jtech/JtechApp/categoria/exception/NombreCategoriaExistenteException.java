package com.jtech.JtechApp.categoria.exception;

public class NombreCategoriaExistenteException extends RuntimeException {
    public NombreCategoriaExistenteException(String nombre) {
        super(String.format("La categoria %s ya existe.", nombre));
    }
}
