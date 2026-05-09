package com.jtech.JtechApp.exception;

import com.jtech.JtechApp.categoria.exception.*;
import com.jtech.JtechApp.usuario.exception.*;
import com.jtech.JtechApp.producto.exception.*;
import com.jtech.JtechApp.orden.exception.*;
import com.jtech.JtechApp.pago.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaNoEncontradaException.class)
    public ResponseEntity<String> handleCategoriaNoEncontrada(CategoriaNoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NombreCategoriaNoEncontradaException.class)
    public ResponseEntity<String> handleNombreCategoriaNoEncontrada(NombreCategoriaNoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SubcategoriaNoEncontradaException.class)
    public ResponseEntity<String> handleSubcategoriaNoEncontrada(SubcategoriaNoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MarcaNoEncontradaException.class)
    public ResponseEntity<String> handleMarcaNoEncontrada(MarcaNoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<String> handleProductoNoEncontrado(ProductoNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(VarianteProductoNoEncontradaException.class)
    public ResponseEntity<String> handleVarianteProductoNoEncontrada(VarianteProductoNoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(OrdenNoEncontradaException.class)
    public ResponseEntity<String> handleOrdenNoEncontrada(OrdenNoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MetodoPagoNoEncontradoException.class)
    public ResponseEntity<String> handleMetodoPagoNoEncontrado(MetodoPagoNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(EmailNoEncontradoException.class)
    public ResponseEntity<String> handleEmailNoEncontrado(EmailNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<String> handleUsuarioNoEncontrado(UsuarioNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<String> handleClienteNoEncontrado(ClienteNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CategoriaExistenteException.class)
    public ResponseEntity<String> handleCategoriaExistente(CategoriaExistenteException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(NombreCategoriaExistenteException.class)
    public ResponseEntity<String> handleNombreCategoriaExistente(NombreCategoriaExistenteException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(NombreMarcaExistenteException.class)
    public ResponseEntity<String> handleNombreMarcaExistente(NombreMarcaExistenteException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(EmailExistenteException.class)
    public ResponseEntity<String> handleEmailExistente(EmailExistenteException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    //General
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor :/ : " + e.getMessage());
    }
}