package com.tienda.ropa.backend.service;

import com.tienda.ropa.backend.dto.pedido.*;

import java.util.List;

public interface PedidoService {

    PedidoResponse create(PedidoCreateRequest request);

    PedidoResponse getById(Long id);

    List<PedidoResponse> list();
}
