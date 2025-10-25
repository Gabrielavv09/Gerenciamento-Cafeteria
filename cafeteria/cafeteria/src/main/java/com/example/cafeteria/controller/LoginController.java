package com.example.cafeteria.controller;

import com.example.cafeteria.model.Usuario;
import com.example.cafeteria.service.UsuarioService;
import jakarta.servlet.http.HttpSession; // IMPORTANTE: Classe para gerenciar a sessão
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    // --- 1. LOGIN ---

    // Rota para exibir o formulário de login
    @GetMapping("/login")
    public String exibirFormularioLogin() {
        return "login"; // Nome do arquivo HTML que vamos criar
    }

    // Rota que processa a tentativa de login (POST)
    @PostMapping("/login")
    public String fazerLogin(@RequestParam String username, // Recebe o campo 'username' do formulário
                             @RequestParam String password, // Recebe o campo 'password' do formulário
                             HttpSession session, // Objeto HttpSession para armazenar o estado
                             RedirectAttributes ra) {

        Usuario usuario = usuarioService.autenticar(username, password);

        if (usuario != null) {
            // Sucesso na Autenticação (Requisito: Login com HttpSession)
            session.setAttribute("usuarioLogado", usuario); // Armazena o objeto na sessão

            // Redireciona para a página principal do gerenciamento
            return "redirect:/produtos/listar";
        } else {
            // Falha na Autenticação (Requisito: Tratamento de erro)
            ra.addFlashAttribute("mensagemErro", "Usuário ou senha inválidos.");
            return "redirect:/login"; // Volta para a página de login
        }
    }

    // --- 2. LOGOUT ---

    // Rota para finalizar a sessão
    @GetMapping("/logout")
    public String fazerLogout(HttpSession session, RedirectAttributes ra) {
        // Requisito: Logout com HttpSession
        session.invalidate(); // Invalida (destrói) a sessão do usuário
        ra.addFlashAttribute("mensagemSucesso", "Você foi desconectado com sucesso.");

        return "redirect:/login"; // Volta para a tela de login
    }
}