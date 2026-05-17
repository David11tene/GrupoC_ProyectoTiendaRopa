package com.tienda.ropa.backend.web.controller;

import com.tienda.ropa.backend.domain.Pedido;
import com.tienda.ropa.backend.dto.pedido.*;

import com.tienda.ropa.backend.repository.PedidoRepository;
import com.tienda.ropa.backend.service.PedidoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoService service, PedidoRepository pedidoRepository) {
        this.service = service;
        this.pedidoRepository = pedidoRepository;
    }

    // CREAR PEDIDO
    @PostMapping
    public ResponseEntity<PedidoResponse> create(
            @Valid @RequestBody PedidoCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> getAll() {

        return ResponseEntity.ok(service.list());
    }

    // ACTUALIZAR ESTADO DEL PEDIDO (Aprobar / Rechazar)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> updateEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String nuevoEstado = body.get("estado");

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);

        return ResponseEntity.ok(service.getById(id));
    }
}
