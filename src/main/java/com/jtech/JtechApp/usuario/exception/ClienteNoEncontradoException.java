package com.jtech.JtechApp.usuario.exception;

public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException() {
        super("Cliente no encontrado.");
    }
}
