package com.jtech.JtechApp.producto.service;

import com.jtech.JtechApp.producto.entity.ImagenProducto;
import com.jtech.JtechApp.producto.entity.Producto;
import com.jtech.JtechApp.producto.entity.VarianteProducto;
import com.jtech.JtechApp.producto.exception.ProductoNoEncontradoException;
import com.jtech.JtechApp.producto.repository.ImagenProductoRepository;
import com.jtech.JtechApp.producto.repository.ProductoRepository;
import com.jtech.JtechApp.producto.repository.VarianteProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final VarianteProductoRepository varianteProductoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public List<Producto> findAllActivos() {
        return productoRepository.findByActivoTrue();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException());
    }

    public List<Producto> buscar(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre);
    }

    public List<Producto> findByCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId);
    }

    public List<Producto> findByMarca(Long marcaId) {
        return productoRepository.findByMarcaIdAndActivoTrue(marcaId);
    }

    public List<Producto> findByCategoriaYMarca(Long categoriaId, Long marcaId) {
        return productoRepository.findByCategoriaIdAndMarcaIdAndActivoTrue(categoriaId, marcaId);
    }

    public List<ImagenProducto> findImagenes(Long productoId) {
        findById(productoId);
        return imagenProductoRepository.findByProductoId(productoId);
    }

    public Optional<ImagenProducto> findImagenPrincipal(Long productoId) {
        findById(productoId);
        return imagenProductoRepository.findByProductoIdAndEsPrincipalTrue(productoId);
    }

    @Transactional
    public Producto save(Producto producto) {
        if (productoRepository.existsByNombreAndMarcaId(producto.getNombre(), producto.getMarca().getId())) {
            throw new ProductoNoEncontradoException();
        }
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto update(Long productoId, Producto productoActualizado) {
        Producto producto = findById(productoId);
        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto toggleActivo(Long productoId) {
        Producto producto = findById(productoId);
        producto.setActivo(!producto.getActivo());
        return productoRepository.save(producto);
    }

    @Transactional
    public void delete(Long productoId) {
        findById(productoId);
        productoRepository.deleteById(productoId);
    }

    public List<VarianteProducto> findVariantes(Long productoId) {
        findById(productoId);
        return varianteProductoRepository.findByProductoId(productoId);
    }

    @Transactional
    public VarianteProducto addVariante(Long productoId, VarianteProducto variante) {
        Producto producto = findById(productoId);
        variante.setProducto(producto);
        return varianteProductoRepository.save(variante);
    }

    @Transactional
    public VarianteProducto updateVariante(Long varianteId, VarianteProducto varianteActualizada) {
        VarianteProducto variante = varianteProductoRepository.findById(varianteId)
                .orElseThrow(() -> new ProductoNoEncontradoException());
        variante.setNombre(varianteActualizada.getNombre());
        variante.setPrecio(varianteActualizada.getPrecio());
        variante.setStock(varianteActualizada.getStock());
        variante.setSku(varianteActualizada.getSku());
        return varianteProductoRepository.save(variante);
    }

    @Transactional
    public void deleteVariante(Long varianteId) {
        varianteProductoRepository.findById(varianteId)
                .orElseThrow(() -> new ProductoNoEncontradoException());
        varianteProductoRepository.deleteById(varianteId);
    }
}