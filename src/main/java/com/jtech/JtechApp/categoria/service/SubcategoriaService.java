package com.jtech.JtechApp.categoria.service;

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

    public List<Subcategoria> findAll() {
        return subcategoriaRepository.findAll();
    }

    public List<Subcategoria> findByCategoria(Long categoriaId) {
        categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
        return subcategoriaRepository.findByCategoriaId(categoriaId);
    }

    public Subcategoria findById(Long categoriaId, Long subcategoriaId) {
        categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
        return subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new SubcategoriaNoEncontradaException());
    }

    @Transactional
    public Subcategoria save(Long categoriaId, Subcategoria subcategoria) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
        subcategoria.setCategoria(categoria);
        return subcategoriaRepository.save(subcategoria);
    }

    @Transactional
    public Subcategoria update(Long categoriaId, Long subcategoriaId, Subcategoria subcategoriaActualizada) {
        Subcategoria subcategoria = findById(categoriaId, subcategoriaId);
        subcategoria.setNombre(subcategoriaActualizada.getNombre());
        return subcategoriaRepository.save(subcategoria);
    }

    @Transactional
    public void delete(Long subcategoriaId) {
        subcategoriaRepository.findById(subcategoriaId)
                .orElseThrow(() -> new SubcategoriaNoEncontradaException());
        subcategoriaRepository.deleteById(subcategoriaId);
    }
}