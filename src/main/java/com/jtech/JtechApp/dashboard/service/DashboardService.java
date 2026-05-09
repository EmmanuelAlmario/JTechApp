package com.jtech.JtechApp.dashboard.service;

import com.jtech.JtechApp.orden.enums.EstadoOrden;
import com.jtech.JtechApp.orden.repository.OrdenRepository;
import com.jtech.JtechApp.producto.repository.ProductoRepository;
import com.jtech.JtechApp.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrdenes", ordenRepository.count());
        stats.put("totalProductos", productoRepository.count());
        stats.put("totalClientes", usuarioRepository.count());
        stats.put("ordenesPendientes", ordenRepository.findByEstado(EstadoOrden.PENDIENTE).size());
        stats.put("productos", productoRepository.findAll().stream()
                .map(p -> Map.of("id", p.getId(), "nombre", p.getNombre(), "activo", p.getActivo()))
                .toList());
        stats.put("usuarios", usuarioRepository.findAll().stream()
                .map(u -> Map.of("id", u.getId(), "nombre", u.getNombre(), "rol", u.getRol(), "activo", u.getActivo()))
                .toList());
        return stats;
    }

    public List<Map<String, Object>> getIngresosPorMes(int year) {
        List<Object[]> resultados = ordenRepository.getIngresosPorMes(year);
        List<Map<String, Object>> ingresos = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> mes = new HashMap<>();
            mes.put("mes", fila[0]);
            mes.put("total", fila[1]);
            ingresos.add(mes);
        }
        return ingresos;
    }
}
