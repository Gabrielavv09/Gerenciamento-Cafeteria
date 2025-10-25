package com.example.cafeteria.repository;

import com.example.cafeteria.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método customizado para buscar o usuário pelo nome de usuário
    Usuario findByUsername(String username);
}