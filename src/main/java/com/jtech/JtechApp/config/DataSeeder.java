package com.jtech.JtechApp.config;

import com.jtech.JtechApp.usuario.entity.Administrador;
import com.jtech.JtechApp.usuario.enums.NivelAdmin;
import com.jtech.JtechApp.usuario.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.nombre}")
    private String adminNombre;

    @Value("${admin.cargo}")
    private String adminCargo;

    @Override
    public void run(ApplicationArguments args) {
        if (!administradorRepository.existsByEmail(adminEmail)) {
            Administrador admin = new Administrador();
            admin.setNombre(adminNombre);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setCargo(adminCargo);
            admin.setNivel(NivelAdmin.SUPER_ADMIN);
            administradorRepository.save(admin);
            System.out.println("SuperAdmin para jtech, creado exitosamente.");
        }
    }
}
