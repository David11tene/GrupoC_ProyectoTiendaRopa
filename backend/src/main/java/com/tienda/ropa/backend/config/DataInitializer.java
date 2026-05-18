package com.tienda.ropa.backend.config;

import com.tienda.ropa.backend.domain.Usuario;
import com.tienda.ropa.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.findByNombre("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("admin");
                admin.setCorreo("admin@gmail.com");
                admin.setContrasena(passwordEncoder.encode("admin"));
                admin.setRol("ADMIN");
                admin.setActive(true);
                repository.save(admin);
                System.out.println(">>> Usuario administrador creado por defecto (admin/admin)");
            } else {
                // Opcional: Asegurar que la contraseña sea 'admin' aunque el usuario ya exista
                Usuario admin = repository.findByNombre("admin").get();
                admin.setContrasena(passwordEncoder.encode("admin"));
                repository.save(admin);
                System.out.println(">>> Credenciales de administrador actualizadas");
            }
        };
    }
}
