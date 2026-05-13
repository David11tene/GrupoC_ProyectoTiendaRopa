package com.tienda.ropa.backend.service.impl;

import com.tienda.ropa.backend.domain.DetallePedido;
import com.tienda.ropa.backend.domain.Pedido;
import com.tienda.ropa.backend.domain.Producto;
import com.tienda.ropa.backend.domain.Usuario;
import com.tienda.ropa.backend.dto.pedido.PedidoCreateRequest;
import com.tienda.ropa.backend.dto.pedido.PedidoResponse;
import com.tienda.ropa.backend.repository.PedidoRepository;
import com.tienda.ropa.backend.repository.ProductoRepository;
import com.tienda.ropa.backend.repository.UsuarioRepository;
import com.tienda.ropa.backend.service.PedidoService;
import com.tienda.ropa.backend.web.advice.ConflictException;
import com.tienda.ropa.backend.web.advice.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repo;
    private final UsuarioRepository usuarioRepo;
    private final ProductoRepository productoRepo;

    public PedidoServiceImpl(
            PedidoRepository repo,
            UsuarioRepository usuarioRepo,
            ProductoRepository productoRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.productoRepo = productoRepo;
    }

    @Override
    @Transactional
    public PedidoResponse create(PedidoCreateRequest request) {
        // 1. Buscar Usuario
        Usuario usuario = usuarioRepo.findById(request.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + request.getIdUsuario()));

        // 2. Crear instancia de Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDate.now());
        pedido.setEstado("COMPLETADO");
        
        double totalPedido = 0.0;
        List<DetallePedido> detalles = new ArrayList<>();

        // 3. Procesar productos
        for (PedidoCreateRequest.ProductoItemRequest item : request.getProductos()) {
            Producto producto = productoRepo.findById(item.getIdProducto())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + item.getIdProducto()));

            // 4. Validar Stock
            if (producto.getStock() < item.getCantidad()) {
                throw new ConflictException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // 5. Calcular Subtotal
            double subtotal = producto.getPrecio() * item.getCantidad();
            totalPedido += subtotal;

            // 6. Descontar Stock
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepo.save(producto);

            // 7. Crear Detalle
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setSubtotal(subtotal);
            detalles.add(detalle);
        }

        pedido.setTotal(totalPedido);
        pedido.setDetalles(detalles);

        // 8. Guardar todo (Cascada activada en Pedido)
        return toResponse(repo.save(pedido));
    }

    @Override
    public PedidoResponse getById(Long id) {
        Pedido p = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado"));
        return toResponse(p);
    }

    @Override
    public List<PedidoResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
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
