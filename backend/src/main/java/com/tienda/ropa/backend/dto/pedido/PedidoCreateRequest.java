package com.tienda.ropa.backend.dto.pedido;

import jakarta.validation.constraints.*;

public class PedidoCreateRequest {

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    // GETTERS Y SETTERS

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
