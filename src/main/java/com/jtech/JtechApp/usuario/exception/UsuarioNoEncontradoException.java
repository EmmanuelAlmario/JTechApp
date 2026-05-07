package com.jtech.JtechApp.usuario.exception;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException() {
        super("El usuario no fue encontrado.");
    }
}
