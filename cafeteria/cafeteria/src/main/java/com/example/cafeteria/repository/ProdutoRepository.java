package com.example.cafeteria.repository;

import com.example.cafeteria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Conexão da model com o bd, lidando com o Crud e pesquisa p service
// Todos os métodos estão inclusos: salvar, buscar todos, buscar por ID e excluir.
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
        /**
         * Requisito: Pesquisa de um registro.
         * * O Spring Data JPA traduz este nome para uma consulta SQL:
         * * 'Containing': Usa LIKE (busca por parte do nome).
         * 'IgnoreCase': Ignora letras maiúsculas/minúsculas.
         */
        List<Produto> findByNomeProdutoContainingIgnoreCase(String nomeProduto);
    }