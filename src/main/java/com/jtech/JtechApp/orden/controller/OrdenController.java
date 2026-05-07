package com.jtech.JtechApp.orden.controller;

import com.jtech.JtechApp.dto.request.CreateDetalleOrdenRequestDTO;
import com.jtech.JtechApp.dto.request.CreateOrdenRequestDTO;
import com.jtech.JtechApp.dto.response.DetalleOrdenResponseDTO;
import com.jtech.JtechApp.dto.response.OrdenResponseDTO;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrdenController {
    private final OrdenService ordenService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/ordenes")
    public ResponseEntity<List<OrdenResponseDTO>> findAll() { return ResponseEntity.ok(ordenService.findAll().stream().map(this::toResponse).toList()); }
    @GetMapping("/ordenes/filtrar")
    public ResponseEntity<List<OrdenResponseDTO>> findByEstado(@RequestParam EstadoOrden estado) { return ResponseEntity.ok(ordenService.findByEstado(estado).stream().map(this::toResponse).toList()); }
    @PutMapping("/ordenes/{id}/estado")
    public ResponseEntity<OrdenResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam EstadoOrden estado) { return ResponseEntity.ok(toResponse(ordenService.cambiarEstado(id, estado))); }
    @GetMapping("/mis-ordenes")
    public ResponseEntity<List<OrdenResponseDTO>> misOrdenes(Authentication authentication) { Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).get(); return ResponseEntity.ok(ordenService.findByClienteId(usuario.getId()).stream().map(this::toResponse).toList()); }
    @PostMapping("/ordenes")
    public ResponseEntity<OrdenResponseDTO> crearOrden(@RequestBody CreateOrdenRequestDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(ordenService.crearOrden(toEntity(dto)))); }

    private Orden toEntity(CreateOrdenRequestDTO dto){ Orden o=new Orden(); Cliente c=new Cliente(); c.setId(dto.clienteId()); o.setCliente(c); o.setDireccion(dto.direccion()); if(dto.detallesOrden()!=null){ o.setDetallesOrden(dto.detallesOrden().stream().map(this::toDetalle).peek(d->d.setOrden(o)).toList()); } return o; }
    private DetalleOrden toDetalle(CreateDetalleOrdenRequestDTO d){ DetalleOrden det = new DetalleOrden(); VarianteProducto v=new VarianteProducto(); v.setId(d.varianteProductoId()); det.setVarianteProducto(v); det.setCantidad(d.cantidad()); det.setPrecioUnitario(d.precioUnitario()); return det; }
    private OrdenResponseDTO toResponse(Orden o){ return new OrdenResponseDTO(o.getId(), o.getCliente()!=null?o.getCliente().getId():null, o.getDireccion(), o.getTotal(), o.getEstado(), o.getFecha(), o.getDetallesOrden()==null?List.of():o.getDetallesOrden().stream().map(d->new DetalleOrdenResponseDTO(d.getId(), d.getVarianteProducto()!=null?d.getVarianteProducto().getId():null, d.getCantidad(), d.getPrecioUnitario())).toList()); }
}
