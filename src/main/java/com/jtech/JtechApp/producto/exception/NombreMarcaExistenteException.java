package com.jtech.JtechApp.producto.exception;

public class NombreMarcaExistenteException extends RuntimeException {
    public NombreMarcaExistenteException(String message) {
        super("La marca %s ya existe.");
    }
}
