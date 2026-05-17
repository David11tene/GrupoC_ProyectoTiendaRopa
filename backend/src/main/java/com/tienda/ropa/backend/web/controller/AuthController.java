package com.tienda.ropa.backend.web.controller;

import com.tienda.ropa.backend.domain.Usuario;
import com.tienda.ropa.backend.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String nombre = credentials.get("nombre");
        String contrasena = credentials.get("contrasena");

        Optional<Usuario> optionalUsuario = usuarioRepository.findByNombre(nombre);

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }

        Usuario usuario = optionalUsuario.get();

        if (!usuario.getContrasena().equals(contrasena)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }

        if (!Boolean.TRUE.equals(usuario.getActive())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Usuario desactivado"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", usuario.getId());
        response.put("nombre", usuario.getNombre());
        response.put("correo", usuario.getCorreo());
        response.put("rol", usuario.getRol());
        response.put("active", usuario.getActive());

        return ResponseEntity.ok(response);
    }
}
