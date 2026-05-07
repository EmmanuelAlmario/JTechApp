package com.jtech.JtechApp.categoria.service;

import com.jtech.JtechApp.categoria.dto.request.UpdateSubcategoriaRequestDTO;
import com.jtech.JtechApp.categoria.dto.response.SubcategoriaResponseDTO;
import com.jtech.JtechApp.categoria.entity.Categoria;
import com.jtech.JtechApp.categoria.entity.Subcategoria;
import com.jtech.JtechApp.categoria.exception.CategoriaNoEncontradaException;
import com.jtech.JtechApp.categoria.exception.SubcategoriaNoEncontradaException;
import com.jtech.JtechApp.categoria.repository.CategoriaRepository;
import com.jtech.JtechApp.categoria.repository.SubcategoriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoriaService {

    private final SubcategoriaRepository subcategoriaRepository;
    private final CategoriaRepository categoriaRepository;

    public List<SubcategoriaResponseDTO> findAll() {
        return subcategoriaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SubcategoriaResponseDTO> findByCategoria(Long categoriaId) {
        categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
        return subcategoriaRepository.findByCategoriaId(categoriaId).stream()
                .map(this::toResponse)
                .toList();
    }

    public SubcategoriaResponseDTO findById(Long subcategoriaId) {
        return toResponse(subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new SubcategoriaNoEncontradaException()));
    }

    @Transactional
    public SubcategoriaResponseDTO save(Long categoriaId, UpdateSubcategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setNombre(dto.nombre());
        subcategoria.setCategoria(categoria);
        return toResponse(subcategoriaRepository.save(subcategoria));
    }

    @Transactional
    public SubcategoriaResponseDTO update(Long subcategoriaId, UpdateSubcategoriaRequestDTO dto) {
        Subcategoria subcategoria = subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new SubcategoriaNoEncontradaException());
        subcategoria.setNombre(dto.nombre());
        return toResponse(subcategoriaRepository.save(subcategoria));
    }

    @Transactional
    public void delete(Long subcategoriaId) {
        subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new SubcategoriaNoEncontradaException());
        subcategoriaRepository.deleteById(subcategoriaId);
    }

    private SubcategoriaResponseDTO toResponse(Subcategoria subcategoria) {
        return new SubcategoriaResponseDTO(
                subcategoria.getId(),
                subcategoria.getNombre(),
                subcategoria.getCategoria() != null ? subcategoria.getCategoria().getId() : null
        );
    }
}