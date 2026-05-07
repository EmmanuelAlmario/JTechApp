package com.jtech.JtechApp.producto.service;

import com.jtech.JtechApp.producto.dto.request.CreateMarcaRequestDTO;
import com.jtech.JtechApp.producto.dto.request.UpdateMarcaRequestDTO;
import com.jtech.JtechApp.producto.dto.response.MarcaResponseDTO;
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

    public List<MarcaResponseDTO> findAll() {
        return marcaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MarcaResponseDTO findById(Long marcaId) {
        return toResponse(marcaRepository.findById(marcaId)
                .orElseThrow(() -> new MarcaNoEncontradaException()));
    }

    @Transactional
    public MarcaResponseDTO save(CreateMarcaRequestDTO dto) {
        if (marcaRepository.existsByNombre(dto.nombre())) {
            throw new NombreMarcaExistenteException(dto.nombre());
        }
        Marca marca = new Marca();
        marca.setNombre(dto.nombre());
        marca.setLogo(dto.logo());
        return toResponse(marcaRepository.save(marca));
    }

    @Transactional
    public MarcaResponseDTO update(Long marcaId, UpdateMarcaRequestDTO dto) {
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new MarcaNoEncontradaException());
        marca.setNombre(dto.nombre());
        marca.setLogo(dto.logo());
        return toResponse(marcaRepository.save(marca));
    }

    @Transactional
    public void delete(Long marcaId) {
        marcaRepository.findById(marcaId)
                .orElseThrow(() -> new MarcaNoEncontradaException());
        marcaRepository.deleteById(marcaId);
    }

    private MarcaResponseDTO toResponse(Marca marca) {
        return new MarcaResponseDTO(
                marca.getId(),
                marca.getNombre(),
                marca.getLogo()
        );
    }
}