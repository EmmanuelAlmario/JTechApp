package com.jtech.JtechApp.producto.service;

import com.jtech.JtechApp.categoria.exception.CategoriaNoEncontradaException;
import com.jtech.JtechApp.categoria.exception.SubcategoriaNoEncontradaException;
import com.jtech.JtechApp.categoria.repository.CategoriaRepository;
import com.jtech.JtechApp.categoria.repository.SubcategoriaRepository;
import com.jtech.JtechApp.producto.dto.request.CreateImagenProductoRequestDTO;
import com.jtech.JtechApp.producto.dto.request.CreateProductoRequestDTO;
import com.jtech.JtechApp.producto.dto.request.CreateVarianteProductoRequestDTO;
import com.jtech.JtechApp.producto.dto.request.UpdateProductoRequestDTO;
import com.jtech.JtechApp.producto.dto.response.ImagenProductoResponseDTO;
import com.jtech.JtechApp.producto.dto.response.ProductoResponseDTO;
import com.jtech.JtechApp.producto.dto.response.VarianteProductoResponseDTO;
import com.jtech.JtechApp.producto.entity.ImagenProducto;
import com.jtech.JtechApp.producto.entity.Producto;
import com.jtech.JtechApp.producto.entity.VarianteProducto;
import com.jtech.JtechApp.producto.exception.MarcaNoEncontradaException;
import com.jtech.JtechApp.producto.exception.ProductoNoEncontradoException;
import com.jtech.JtechApp.producto.repository.ImagenProductoRepository;
import com.jtech.JtechApp.producto.repository.MarcaRepository;
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
    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final MarcaRepository marcaRepository;

    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductoResponseDTO> findAllActivos() {
        return productoRepository.findByActivoTrue().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductoResponseDTO findById(Long id) {
        return toResponse(productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException()));
    }

    public List<ProductoResponseDTO> buscar(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCaseAndActivoTrue(nombre).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductoResponseDTO> findByCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductoResponseDTO> findByMarca(Long marcaId) {
        return productoRepository.findByMarcaIdAndActivoTrue(marcaId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductoResponseDTO> findByCategoriaYMarca(Long categoriaId, Long marcaId) {
        return productoRepository.findByCategoriaIdAndMarcaIdAndActivoTrue(categoriaId, marcaId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProductoResponseDTO save(CreateProductoRequestDTO dto) {
        if (productoRepository.existsByNombreAndMarcaId(dto.nombre(), dto.marcaId())) {
            throw new ProductoNoEncontradoException();
        }

        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setActivo(dto.activo() != null ? dto.activo() : true);
        producto.setCategoria(categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaNoEncontradaException(dto.categoriaId())));
        producto.setSubcategoria(subcategoriaRepository.findById(dto.subcategoriaId())
                .orElseThrow(() -> new SubcategoriaNoEncontradaException()));
        producto.setMarca(marcaRepository.findById(dto.marcaId())
                .orElseThrow(() -> new MarcaNoEncontradaException()));

        Producto guardado = productoRepository.save(producto);

        if (dto.variantes() != null) {
            for (CreateVarianteProductoRequestDTO v : dto.variantes()) {
                VarianteProducto variante = new VarianteProducto();
                variante.setNombre(v.nombre());
                variante.setPrecio(v.precio());
                variante.setStock(v.stock());
                variante.setSku(v.sku());
                variante.setProducto(guardado);
                varianteProductoRepository.save(variante);
            }
        }

        if (dto.imagenes() != null) {
            for (CreateImagenProductoRequestDTO i : dto.imagenes()) {
                ImagenProducto imagen = new ImagenProducto();
                imagen.setUrl(i.url());
                imagen.setEsPrincipal(i.esPrincipal());
                imagen.setProducto(guardado);
                imagenProductoRepository.save(imagen);
            }
        }

        return toResponse(productoRepository.findById(guardado.getId()).get());
    }

    @Transactional
    public ProductoResponseDTO update(Long productoId, UpdateProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException());
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        return toResponse(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponseDTO toggleActivo(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException());
        producto.setActivo(!producto.getActivo());
        return toResponse(productoRepository.save(producto));
    }

    @Transactional
    public void delete(Long productoId) {
        productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException());
        productoRepository.deleteById(productoId);
    }

    public List<VarianteProductoResponseDTO> findVariantes(Long productoId) {
        findById(productoId);
        return varianteProductoRepository.findByProductoId(productoId).stream()
                .map(v -> new VarianteProductoResponseDTO(v.getId(), v.getNombre(), v.getPrecio(), v.getStock(), v.getSku()))
                .toList();
    }

    @Transactional
    public VarianteProductoResponseDTO addVariante(Long productoId, CreateVarianteProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException());
        VarianteProducto variante = new VarianteProducto();
        variante.setNombre(dto.nombre());
        variante.setPrecio(dto.precio());
        variante.setStock(dto.stock());
        variante.setSku(dto.sku());
        variante.setProducto(producto);
        VarianteProducto guardada = varianteProductoRepository.save(variante);
        return new VarianteProductoResponseDTO(guardada.getId(), guardada.getNombre(), guardada.getPrecio(), guardada.getStock(), guardada.getSku());
    }

    @Transactional
    public VarianteProductoResponseDTO updateVariante(Long varianteId, CreateVarianteProductoRequestDTO dto) {
        VarianteProducto variante = varianteProductoRepository.findById(varianteId)
                .orElseThrow(() -> new ProductoNoEncontradoException());
        variante.setNombre(dto.nombre());
        variante.setPrecio(dto.precio());
        variante.setStock(dto.stock());
        variante.setSku(dto.sku());
        VarianteProducto guardada = varianteProductoRepository.save(variante);
        return new VarianteProductoResponseDTO(guardada.getId(), guardada.getNombre(), guardada.getPrecio(), guardada.getStock(), guardada.getSku());
    }

    @Transactional
    public void deleteVariante(Long varianteId) {
        varianteProductoRepository.findById(varianteId)
                .orElseThrow(() -> new ProductoNoEncontradoException());
        varianteProductoRepository.deleteById(varianteId);
    }

    private ProductoResponseDTO toResponse(Producto producto) {
        List<VarianteProductoResponseDTO> variantes = varianteProductoRepository
                .findByProductoId(producto.getId()).stream()
                .map(v -> new VarianteProductoResponseDTO(v.getId(), v.getNombre(), v.getPrecio(), v.getStock(), v.getSku()))
                .toList();

        List<ImagenProductoResponseDTO> imagenes = imagenProductoRepository
                .findByProductoId(producto.getId()).stream()
                .map(i -> new ImagenProductoResponseDTO(i.getId(), i.getUrl(), i.getEsPrincipal()))
                .toList();

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getCategoria() != null ? producto.getCategoria().getId() : null,
                producto.getSubcategoria() != null ? producto.getSubcategoria().getId() : null,
                producto.getMarca() != null ? producto.getMarca().getId() : null,
                producto.getActivo(),
                variantes,
                imagenes
        );
    }
}