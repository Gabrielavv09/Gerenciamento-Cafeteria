package com.example.cafeteria.service;

import com.example.cafeteria.model.Produto;
import com.example.cafeteria.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Executar as operações do Crud e a validação
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository; // Conexão com a interface ProdutoRepository

    // MANIPULAÇÃO DE DADOS/ CRUD

    // Requisito de Cadastro e Edição (Salvar/Criar um novo produto)
    public Produto salvarProduto(Produto produto) {
        // Validação centralizada de estoque para Cadastro e Edição
        if (produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo.");
        }
        // Adicionando a validação de Preço
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
        return produtoRepository.save(produto); // O .save() do JPA lida tanto com o INSERT quanto com o UPDATE
    }

    // Requisito de Listagem de registros/ Buscar todos os produtos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }


    // Requisito de Exclusão/ Remover um produto
    public void excluirProduto(Long id) {
        // Verifica se o produto existe antes de tentar excluir (Tratamento de Erros)
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado para exclusão com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    // Requisito de Pesquisa buscando pelo ID
    // Este método é essencial para carregar o formulário de edição
    public Produto buscarPorId(Long id) {
        // Garante que, se o produto não for encontrado, uma exceção (erro) será lançada.
        return produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + id));
    }

    // Requisito de Pesquisa buscar por nome
    public List<Produto> pesquisarPorNome(String nomeProduto) {
        // Usa o novo método do Repository, que retorna uma lista
        // Verifica se o termo está vazio e retorna todos os produtos
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            return listarTodos();
        }
        return produtoRepository.findByNomeProdutoContainingIgnoreCase(nomeProduto);
    }
}