package com.jtech.JtechApp.categoria.service;

import com.jtech.JtechApp.categoria.dto.request.CreateCategoriaRequestDTO;
import com.jtech.JtechApp.categoria.dto.request.UpdateCategoriaRequestDTO;
import com.jtech.JtechApp.categoria.dto.response.CategoriaResponseDTO;
import com.jtech.JtechApp.categoria.dto.response.SubcategoriaResponseDTO;
import com.jtech.JtechApp.categoria.entity.Categoria;
import com.jtech.JtechApp.categoria.exception.CategoriaNoEncontradaException;
import com.jtech.JtechApp.categoria.exception.NombreCategoriaExistenteException;
import com.jtech.JtechApp.categoria.repository.CategoriaRepository;
import com.jtech.JtechApp.categoria.repository.SubcategoriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoriaResponseDTO findById(Long categoriaId) {
        return toResponse(categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId)));
    }

    @Transactional
    public CategoriaResponseDTO save(CreateCategoriaRequestDTO dto) {
        if (categoriaRepository.existsByNombre(dto.nombre())) {
            throw new NombreCategoriaExistenteException(dto.nombre());
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        return toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponseDTO update(Long categoriaId, UpdateCategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        return toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public void delete(Long categoriaId) {
        categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
        categoriaRepository.deleteById(categoriaId);
    }

    private CategoriaResponseDTO toResponse(Categoria categoria) {
        List<SubcategoriaResponseDTO> subcategorias = categoria.getSubcategorias() == null
                ? new ArrayList<>()
                : categoria.getSubcategorias().stream()
                  .map(s -> new SubcategoriaResponseDTO(s.getId(), s.getNombre(), categoria.getId()))
                  .toList();

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                subcategorias
        );
    }

}