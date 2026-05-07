package com.jtech.JtechApp.usuario.exception;

public class EmailExistenteException extends RuntimeException {
    public EmailExistenteException() {
        super("Este correo ya existe. Usa otro.");
    }
}
