package com.jtech.JtechApp.usuario.entity;

import com.jtech.JtechApp.usuario.enums.NivelAdmin;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "administradores")
public class Administrador extends Usuario {

    @Column(length = 100)
    private String cargo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private NivelAdmin nivel = NivelAdmin.ADMIN;

    @Override
    public String getRol() {
        return nivel.name();
    }
}
