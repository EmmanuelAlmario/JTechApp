package com.jtech.JtechApp.pago.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DiscriminatorValue(value = "EFECTIVO")
@Table(name = "pagos_efectivo")
public class PagoEfectivo extends MetodoPago {
    @Override
    public void procesarPago() {
        System.out.println("Pago en efectivo procesado correctamente.");
    }

    @Override
    public String getTipo() {
        return "EFECTIVO";
    }
}
