package com.jtech.JtechApp.orden.service;

import com.jtech.JtechApp.orden.dto.request.CreateDetalleOrdenRequestDTO;
import com.jtech.JtechApp.orden.dto.request.CreateOrdenRequestDTO;
import com.jtech.JtechApp.orden.dto.response.DetalleOrdenResponseDTO;
import com.jtech.JtechApp.orden.dto.response.OrdenResponseDTO;
import com.jtech.JtechApp.orden.entity.DetalleOrden;
import com.jtech.JtechApp.orden.entity.Orden;
import com.jtech.JtechApp.orden.enums.EstadoOrden;
import com.jtech.JtechApp.orden.exception.OrdenNoEncontradaException;
import com.jtech.JtechApp.orden.exception.VarianteProductoNoEncontradaException;
import com.jtech.JtechApp.orden.repository.OrdenRepository;
import com.jtech.JtechApp.producto.entity.VarianteProducto;
import com.jtech.JtechApp.producto.repository.VarianteProductoRepository;
import com.jtech.JtechApp.usuario.entity.Cliente;
import com.jtech.JtechApp.usuario.exception.ClienteNoEncontradoException;
import com.jtech.JtechApp.usuario.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final VarianteProductoRepository varianteProductoRepository;
    private final ClienteRepository clienteRepository;

    public List<OrdenResponseDTO> findAll() {
        return ordenRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public OrdenResponseDTO findById(Long ordenId) {
        return toResponse(ordenRepository.findById(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId)));
    }

    public List<OrdenResponseDTO> findByClienteId(Long clienteId) {
        return ordenRepository.findByClienteIdOrderByFechaDesc(clienteId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<OrdenResponseDTO> findByEstado(EstadoOrden estado) {
        return ordenRepository.findByEstado(estado).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public OrdenResponseDTO crearOrden(CreateOrdenRequestDTO dto, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNoEncontradoException());

        Orden orden = new Orden();
        orden.setCliente(cliente);
        orden.setDireccion(dto.direccion());

        List<DetalleOrden> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CreateDetalleOrdenRequestDTO d : dto.detallesOrden()) {
            VarianteProducto variante = varianteProductoRepository.findById(d.varianteProductoId())
                    .orElseThrow(() -> new VarianteProductoNoEncontradaException());

            DetalleOrden detalle = new DetalleOrden();
            detalle.setVarianteProducto(variante);
            detalle.setCantidad(d.cantidad());
            detalle.setPrecioUnitario(variante.getPrecio());
            detalle.setOrden(orden);
            detalles.add(detalle);

            total = total.add(variante.getPrecio().multiply(BigDecimal.valueOf(d.cantidad())));
        }

        orden.setDetallesOrden(detalles);
        orden.setTotal(total);

        return toResponse(ordenRepository.save(orden));
    }

    @Transactional
    public OrdenResponseDTO cambiarEstado(Long ordenId, EstadoOrden nuevoEstado) {
        Orden orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        orden.setEstado(nuevoEstado);
        return toResponse(ordenRepository.save(orden));
    }

    @Transactional
    public void delete(Long ordenId) {
        ordenRepository.findById(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));
        ordenRepository.deleteById(ordenId);
    }

    private OrdenResponseDTO toResponse(Orden orden) {
        List<DetalleOrdenResponseDTO> detalles = orden.getDetallesOrden().stream()
                .map(d -> new DetalleOrdenResponseDTO(
                        d.getId(),
                        d.getVarianteProducto().getId(),
                        d.getCantidad(),
                        d.getPrecioUnitario()))
                .toList();

        return new OrdenResponseDTO(
                orden.getId(),
                orden.getCliente().getId(),
                orden.getDireccion(),
                orden.getTotal(),
                orden.getEstado(),
                orden.getFecha(),
                detalles
        );
    }
}