package com.tienda.ropa.backend.repository;

import com.tienda.ropa.backend.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void shouldSaveAndFindUsuarioByCorreo() {
        // 1. Preparar datos
        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");
        usuario.setCorreo("test@tienda.com");
        usuario.setContrasena("password123");
        usuario.setRol("USER");
        usuario.setActive(true);

        // 2. Guardar
        usuarioRepository.save(usuario);

        // 3. Buscar
        var result = usuarioRepository.findByCorreo("test@tienda.com");

        // 4. Verificar
        assertTrue(result.isPresent());
        assertEquals("Test User", result.get().getNombre());
    }
}
