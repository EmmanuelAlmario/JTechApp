package com.jtech.JtechApp.usuario.exception;

public class EmailNoEncontradoException extends RuntimeException {
    public EmailNoEncontradoException() {
        super("Correo no encontrado.");
    }
}
