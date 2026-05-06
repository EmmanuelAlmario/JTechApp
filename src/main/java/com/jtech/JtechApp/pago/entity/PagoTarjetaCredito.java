package com.jtech.JtechApp.pago.entity;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue(value = "CREDITO")
@Table(name = "pagos_tarjeta_credito")
public class PagoTarjetaCredito extends MetodoPago {

    @NotNull
    @Column(length = 4, nullable = false)
    private String ultimosCuatroDigitos;

    @NotNull
    @Column(length = 100, nullable = false)
    private String nombreTitular;

    @Override
    public void procesarPago() {
        System.out.println("Pago con tarjeta de credito terminada en [XXXX-XXXX-XXXX-" + ultimosCuatroDigitos + "] realizado exitosamente.");
    }

    @Override
    public String getTipo() {
        return "TARJETA DE CREDITO";
    }
}
