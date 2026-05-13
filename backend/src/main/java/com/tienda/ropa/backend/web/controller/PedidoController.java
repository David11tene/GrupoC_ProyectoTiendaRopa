package com.tienda.ropa.backend.web.controller;

import com.tienda.ropa.backend.dto.pedido.*;

import com.tienda.ropa.backend.service.PedidoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
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
}
