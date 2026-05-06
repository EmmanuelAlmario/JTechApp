package com.jtech.JtechApp.usuario.repository;

import com.jtech.JtechApp.usuario.entity.Cliente;
import com.jtech.JtechApp.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Usuario, Long> {
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByActivoTrue();
}
