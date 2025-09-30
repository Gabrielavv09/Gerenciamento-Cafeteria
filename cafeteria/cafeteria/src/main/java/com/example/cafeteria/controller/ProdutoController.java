package com.example.cafeteria.controller;

import com.example.cafeteria.model.Produto;
import com.example.cafeteria.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Para mensagens de feedback
import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // LISTAGEM e PESQUISA

    // Requisito: Listagem e Pesquisa.
    @GetMapping("/listar")
    public String listarProdutos(@RequestParam(value = "termoBusca", required = false) String termoBusca,
                                 Model model) {

        List<Produto> produtos;

        // Lógica de Pesquisa: Se houver termo, pesquisa; senão, lista tudo.
        if (termoBusca != null && !termoBusca.isEmpty()) {
            produtos = produtoService.pesquisarPorNome(termoBusca);
            model.addAttribute("termoBusca", termoBusca);
        } else {
            produtos = produtoService.listarTodos();
        }

        model.addAttribute("produtos", produtos);
        return "listaProdutos";
    }

    // CADASTRO e EDIÇÃO

    // Requisito de Exibir formulário de Cadastro ou Edição, dependendo se o 'id' existe na URL.
    @GetMapping({"/novo", "/editar/{id}"})
    public String exibirFormulario(Model model, @PathVariable(required = false) Long id) {

        if (id != null) {
            // Modo Edição: Busca o produto no Service para pré-preencher o formulário
            try {
                Produto produto = produtoService.buscarPorId(id);
                model.addAttribute("produto", produto);
            } catch (IllegalArgumentException e) {
                // O Erro: Produto não encontrado para edição
                model.addAttribute("mensagemErro", "Erro: " + e.getMessage());
                return "redirect:/produtos/listar";
            }
        } else {
            // Modo Cadastro: Cria um Produto vazio
            model.addAttribute("produto", new Produto());
        }

        return "cadastroProduto"; // Retorna o formulário único para ambos os modos
    }

    //SALVAR

    /**
     * Rota ÚNICA para salvar novo produto E atualizar produto existente.
     * A lógica de UPDATE vs INSERT é feita pelo JPA no Service.
     */
    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute Produto produto, RedirectAttributes ra) {
        try {
            produtoService.salvarProduto(produto);
            ra.addFlashAttribute("mensagemSucesso", "Produto salvo com sucesso!");

            return "redirect:/produtos/listar";
        } catch (IllegalArgumentException e) {
            // Tratamento de Erro: Volta para o formulário de origem com a mensagem de erro
            ra.addFlashAttribute("mensagemErro", "Erro ao salvar: " + e.getMessage());

            if (produto.getId() != null) {
                return "redirect:/produtos/editar/" + produto.getId();
            } else {
                return "redirect:/produtos/novo";
            }
        }
    }

    // EXCLUSÃO (Delete)

    /**
     * Requisito: Exclusão - Remove um registro.
     */
    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id, RedirectAttributes ra) {
        try {
            produtoService.excluirProduto(id);
            ra.addFlashAttribute("mensagemSucesso", "Produto excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/produtos/listar";
    }
}