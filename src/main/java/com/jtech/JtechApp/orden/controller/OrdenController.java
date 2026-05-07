package com.jtech.JtechApp.orden.controller;

import com.jtech.JtechApp.orden.dto.request.CreateDetalleOrdenRequestDTO;
import com.jtech.JtechApp.orden.dto.request.CreateOrdenRequestDTO;
import com.jtech.JtechApp.orden.dto.response.DetalleOrdenResponseDTO;
import com.jtech.JtechApp.orden.dto.response.OrdenResponseDTO;
import com.jtech.JtechApp.orden.entity.DetalleOrden;
import com.jtech.JtechApp.orden.entity.Orden;
import com.jtech.JtechApp.orden.enums.EstadoOrden;
import com.jtech.JtechApp.orden.service.OrdenService;
import com.jtech.JtechApp.producto.entity.VarianteProducto;
import com.jtech.JtechApp.usuario.entity.Cliente;
import com.jtech.JtechApp.usuario.entity.Usuario;
import com.jtech.JtechApp.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/ordenes")
    public ResponseEntity<List<OrdenResponseDTO>> findAll() {
        List<Orden> ordenes = ordenService.findAll();
        return ResponseEntity.ok(toOrdenResponseList(ordenes));
    }

    @GetMapping("/ordenes/filtrar")
    public ResponseEntity<List<OrdenResponseDTO>> findByEstado(@RequestParam EstadoOrden estado) {
        List<Orden> ordenes = ordenService.findByEstado(estado);
        return ResponseEntity.ok(toOrdenResponseList(ordenes));
    }

    @PutMapping("/ordenes/{id}/estado")
    public ResponseEntity<OrdenResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam EstadoOrden estado) {
        Orden orden = ordenService.cambiarEstado(id, estado);
        return ResponseEntity.ok(toResponse(orden));
    }

    @GetMapping("/mis-ordenes")
    public ResponseEntity<List<OrdenResponseDTO>> misOrdenes(Authentication authentication) {
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).get();
        List<Orden> ordenes = ordenService.findByClienteId(usuario.getId());
        return ResponseEntity.ok(toOrdenResponseList(ordenes));
    }

    @PostMapping("/ordenes")
    public ResponseEntity<OrdenResponseDTO> crearOrden(@RequestBody CreateOrdenRequestDTO dto) {
        Orden orden = toEntity(dto);
        Orden createdOrden = ordenService.crearOrden(orden);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(createdOrden));
    }

    private List<OrdenResponseDTO> toOrdenResponseList(List<Orden> ordenes) {
        List<OrdenResponseDTO> response = new ArrayList<>();
        for (Orden orden : ordenes) {
            response.add(toResponse(orden));
        }
        return response;
    }

    private Orden toEntity(CreateOrdenRequestDTO dto) {
        Orden orden = new Orden();

        Cliente cliente = new Cliente();
        cliente.setId(dto.clienteId());
        orden.setCliente(cliente);

        orden.setDireccion(dto.direccion());

        if (dto.detallesOrden() != null) {
            List<DetalleOrden> detalles = new ArrayList<>();
            for (CreateDetalleOrdenRequestDTO detalleDto : dto.detallesOrden()) {
                DetalleOrden detalle = toDetalle(detalleDto);
                detalle.setOrden(orden);
                detalles.add(detalle);
            }
            orden.setDetallesOrden(detalles);
        }

        return orden;
    }

    private DetalleOrden toDetalle(CreateDetalleOrdenRequestDTO dto) {
        DetalleOrden detalle = new DetalleOrden();

        VarianteProducto variante = new VarianteProducto();
        variante.setId(dto.varianteProductoId());

        detalle.setVarianteProducto(variante);
        detalle.setCantidad(dto.cantidad());
        detalle.setPrecioUnitario(dto.precioUnitario());

        return detalle;
    }

    private OrdenResponseDTO toResponse(Orden orden) {
        Long clienteId = null;
        if (orden.getCliente() != null) {
            clienteId = orden.getCliente().getId();
        }

        List<DetalleOrdenResponseDTO> detallesResponse = new ArrayList<>();
        if (orden.getDetallesOrden() != null) {
            for (DetalleOrden detalle : orden.getDetallesOrden()) {
                Long varianteId = null;
                if (detalle.getVarianteProducto() != null) {
                    varianteId = detalle.getVarianteProducto().getId();
                }

                detallesResponse.add(new DetalleOrdenResponseDTO(
                        detalle.getId(),
                        varianteId,
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario()
                ));
            }
        }

        return new OrdenResponseDTO(
                orden.getId(),
                clienteId,
                orden.getDireccion(),
                orden.getTotal(),
                orden.getEstado(),
                orden.getFecha(),
                detallesResponse
        );
    }
}
