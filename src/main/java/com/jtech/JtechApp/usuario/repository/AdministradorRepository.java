package com.jtech.JtechApp.usuario.repository;

import com.jtech.JtechApp.usuario.enums.NivelAdmin;
import com.jtech.JtechApp.usuario.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmail(String email);
    List<Administrador> findByNivel(NivelAdmin nivel);
    boolean existsByEmail(String email);
}
