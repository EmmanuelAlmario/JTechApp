package com.jtech.JtechApp.categoria.exception;

public class NombreCategoriaNoEncontradaException extends RuntimeException {
    public NombreCategoriaNoEncontradaException(String nombre) {
        super(String.format("El nombre %s no existe.", nombre));
    }
}
