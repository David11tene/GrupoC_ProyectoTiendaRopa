package com.tienda.ropa.backend.service.impl;

import com.tienda.ropa.backend.domain.Pedido;
import com.tienda.ropa.backend.domain.Usuario;

import com.tienda.ropa.backend.dto.pedido.*;

import com.tienda.ropa.backend.repository.PedidoRepository;
import com.tienda.ropa.backend.repository.UsuarioRepository;

import com.tienda.ropa.backend.service.PedidoService;

import com.tienda.ropa.backend.web.advice.NotFoundException;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repo;
    private final UsuarioRepository usuarioRepo;

    public PedidoServiceImpl(
            PedidoRepository repo,
            UsuarioRepository usuarioRepo) {

        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public PedidoResponse create(PedidoCreateRequest request) {

        Usuario usuario = usuarioRepo.findById(
                request.getUsuarioId()
        ).orElseThrow(() ->
                new NotFoundException("Usuario no encontrado"));

        Pedido p = new Pedido();

        p.setUsuario(usuario);
        p.setFecha(LocalDate.now());
        p.setEstado(request.getEstado());
        p.setTotal(0.0);

        return toResponse(repo.save(p));
    }

    @Override
    public PedidoResponse getById(Long id) {

        Pedido p = repo.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Pedido no encontrado"));

        return toResponse(p);
    }

    @Override
    public List<PedidoResponse> list() {

        return repo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private PedidoResponse toResponse(Pedido p) {

        PedidoResponse r = new PedidoResponse();

        r.setId(p.getId());
        r.setUsuario(p.getUsuario().getNombre());
        r.setFecha(p.getFecha());
        r.setEstado(p.getEstado());
        r.setTotal(p.getTotal());

        return r;
    }
}
