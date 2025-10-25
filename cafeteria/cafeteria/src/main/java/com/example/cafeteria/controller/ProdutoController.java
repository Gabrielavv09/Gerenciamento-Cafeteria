package com.example.cafeteria.controller;

import com.example.cafeteria.model.Produto;
import com.example.cafeteria.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession; // Essencial para gerenciar a sessão e o filtro
import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // --- 1. LISTAGEM e PESQUISA (Com Persistência de Filtro na Sessão) ---

    @GetMapping("/listar")
    public String listarProdutos(@RequestParam(value = "termoBusca", required = false) String termoBusca,
                                 Model model,
                                 HttpSession session) {

        // REQUISITO: PROTEGER PÁGINAS (Verificação de Login)
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        List<Produto> produtos;
        String termoFinal = termoBusca;

        // LÓGICA DE FILTRO PERSISTENTE: Restaura o filtro da sessão se o termo da URL for nulo.
        if (termoBusca != null) {
            // Se o termo veio da URL, atualiza a sessão ou limpa.
            if (termoBusca.isEmpty()) {
                session.removeAttribute("filtroBusca");
            } else {
                session.setAttribute("filtroBusca", termoBusca);
            }
        } else if (session.getAttribute("filtroBusca") != null) {
            // Se o termo NÃO veio da URL, mas existe um filtro SALVO na sessão, restaura-o.
            termoFinal = (String) session.getAttribute("filtroBusca");
        } else {
            // Não há termo em lugar nenhum.
            termoFinal = null;
        }

        // Processamento da Pesquisa
        if (termoFinal != null && !termoFinal.isEmpty()) {
            produtos = produtoService.pesquisarPorNome(termoFinal);
            model.addAttribute("termoBusca", termoFinal);
        } else {
            produtos = produtoService.listarTodos();
        }

        model.addAttribute("produtos", produtos);
        return "listaProdutos";
    }

    // --- 2. CADASTRO e EDIÇÃO - Exibir Formulário ---

    @GetMapping({"/novo", "/editar/{id}"})
    public String exibirFormulario(Model model,
                                   @PathVariable(required = false) Long id,
                                   HttpSession session) {

        // REQUISITO: PROTEGER PÁGINAS
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        if (id != null) {
            // Modo Edição: Busca o produto no Service
            try {
                Produto produto = produtoService.buscarPorId(id);
                model.addAttribute("produto", produto);
            } catch (IllegalArgumentException e) {
                // Erro: Produto não encontrado
                model.addAttribute("mensagemErro", "Erro: " + e.getMessage());
                return "redirect:/produtos/listar";
            }
        } else {
            // Modo Cadastro: Cria um Produto vazio
            model.addAttribute("produto", new Produto());
        }

        return "cadastroProduto"; // Retorna o formulário único
    }

    // --- 3. SALVAR (Create e Update) ---

    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute Produto produto,
                                RedirectAttributes ra,
                                HttpSession session) {

        // REQUISITO: PROTEGER PÁGINAS
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        try {
            // O Service fará a validação de estoque/preço e o JPA fará INSERT ou UPDATE
            produtoService.salvarProduto(produto);
            ra.addFlashAttribute("mensagemSucesso", "Produto salvo com sucesso!");

            return "redirect:/produtos/listar";
        } catch (IllegalArgumentException e) {
            // Tratamento de Erro (Validação): Volta para o formulário com a mensagem de erro
            ra.addFlashAttribute("mensagemErro", "Erro ao salvar: " + e.getMessage());

            if (produto.getId() != null) {
                return "redirect:/produtos/editar/" + produto.getId();
            } else {
                return "redirect:/produtos/novo";
            }
        }
    }

    // --- 4. EXCLUSÃO (Delete) ---

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id,
                                 RedirectAttributes ra,
                                 HttpSession session) {

        // REQUISITO: PROTEGER PÁGINAS
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        try {
            produtoService.excluirProduto(id);
            ra.addFlashAttribute("mensagemSucesso", "Produto excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            // Tratamento de Erro: Caso o ID não exista para exclusão
            ra.addFlashAttribute("mensagemErro", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/produtos/listar";
    }
}