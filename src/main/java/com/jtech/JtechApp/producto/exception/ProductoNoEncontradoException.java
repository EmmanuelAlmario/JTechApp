package com.jtech.JtechApp.producto.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException() {
        super(String.format("El producto no fue encontrado."));
    }
}
