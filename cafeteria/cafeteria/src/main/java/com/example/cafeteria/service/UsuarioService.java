package com.example.cafeteria.service;

import com.example.cafeteria.model.Usuario;
import com.example.cafeteria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario autenticar(String username, String password) {
        // 1. Busca o usuário pelo nome
        Usuario usuario = usuarioRepository.findByUsername(username);

        // 2. Verifica se o usuário existe E se a senha confere
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario; // Sucesso na autenticação
        }

        return null; // Falha na autenticação
    }
}