package com.jtech.JtechApp.producto.service;

import com.jtech.JtechApp.producto.entity.Marca;
import com.jtech.JtechApp.producto.exception.MarcaNoEncontradaException;
import com.jtech.JtechApp.producto.exception.NombreMarcaExistenteException;
import com.jtech.JtechApp.producto.repository.MarcaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    public Marca findById(Long marcaId) {
        return marcaRepository.findById(marcaId)
                .orElseThrow(() -> new MarcaNoEncontradaException());
    }

    @Transactional
    public Marca save(Marca marca) {
        if (marcaRepository.existsByNombre(marca.getNombre())) {
            throw new NombreMarcaExistenteException(marca.getNombre());
        }
        return marcaRepository.save(marca);
    }

    @Transactional
    public Marca update(Long marcaId, Marca marcaActualizada) {
        Marca marca = findById(marcaId);
        marca.setNombre(marcaActualizada.getNombre());
        marca.setLogo(marcaActualizada.getLogo());
        return marcaRepository.save(marca);
    }

    @Transactional
    public void delete(Long marcaId) {
        findById(marcaId);
        marcaRepository.deleteById(marcaId);
    }
}