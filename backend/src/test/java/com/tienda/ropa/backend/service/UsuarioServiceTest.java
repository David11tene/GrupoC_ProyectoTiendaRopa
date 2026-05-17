package com.tienda.ropa.backend.service;

import com.tienda.ropa.backend.domain.Usuario;
import com.tienda.ropa.backend.dto.usuario.UsuarioCreateRequest;
import com.tienda.ropa.backend.repository.UsuarioRepository;
import com.tienda.ropa.backend.service.impl.UsuarioServiceImpl;
import com.tienda.ropa.backend.web.advice.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({UsuarioServiceImpl.class, BCryptPasswordEncoder.class})
public class UsuarioServiceTest {

    @Autowired
    private UsuarioServiceImpl service;

    @Autowired
    private UsuarioRepository repository;

    @Test
    void shouldNotAllowDuplicatedEmail() {
        // 1. Registrar un usuario previo
        Usuario existing = new Usuario();
        existing.setNombre("Existing");
        existing.setCorreo("duplicado@test.com");
        existing.setContrasena("pass");
        existing.setRol("USER");
        existing.setActive(true);
        repository.save(existing);

        // 2. Crear solicitud con mismo correo
        UsuarioCreateRequest req = new UsuarioCreateRequest();
        req.setNombre("New User");
        req.setCorreo("duplicado@test.com");
        req.setContrasena("pass2");
        req.setRol("USER");

        // 3. Verificar excepcion
        assertThatThrownBy(() -> service.create(req))
                .isInstanceOf(ConflictException.class);
    }
}
