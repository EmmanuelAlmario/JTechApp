package com.jtech.JtechApp.pago.exception;

public class MetodoPagoNoEncontradoException extends RuntimeException {
    public MetodoPagoNoEncontradoException(String nombre) {
        super(String.format("El metodo de pago %s no existe", nombre));
    }
}
