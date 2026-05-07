package com.jtech.JtechApp.categoria.service;

import com.jtech.JtechApp.categoria.entity.Categoria;
import com.jtech.JtechApp.categoria.exception.CategoriaNoEncontradaException;
import com.jtech.JtechApp.categoria.exception.NombreCategoriaExistenteException;
import com.jtech.JtechApp.categoria.repository.CategoriaRepository;
import com.jtech.JtechApp.categoria.repository.SubcategoriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
    }

    public Categoria save(Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new NombreCategoriaExistenteException(categoria.getNombre());
        }
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria update(Long categoriaId, Categoria categoriaActualizada) {
        Categoria categoria = findById(categoriaId);
        categoria.setNombre(categoriaActualizada.getNombre());
        categoria.setDescripcion(categoriaActualizada.getDescripcion());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Long categoriaId) {
        findById(categoriaId); // ya lanza la excepción si no existe
        categoriaRepository.deleteById(categoriaId);
    }
}
