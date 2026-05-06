package com.jtech.JtechApp.usuario.entity;

import com.jtech.JtechApp.orden.entity.Orden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @Column(length = 20)
    private String telefono;

    @Column(length = 300)
    private String direccion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Orden> ordenes = new ArrayList<>();

    @Override
    public String getRol() { return "CLIENTE"; }
}
