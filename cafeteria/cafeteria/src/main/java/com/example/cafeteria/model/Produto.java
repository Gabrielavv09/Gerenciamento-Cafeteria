package com.example.cafeteria.model;

import jakarta.persistence.*;

// Mapeamento da Tabela do BD e Estrutura do Produto
@Entity // Tradução das instruções
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Chave primária da tabela/ valor gerado automaticamente pelo bd
    private Long id;

    @Column(nullable = false) // Campo não nulo
    private String nomeProduto;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private String categoria;

    private String descricao;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    public Produto() {
    }

    // Métodos Getters e Setters p acesso aos atributos:

    // Getters (ler)
    public Long getId() {
        return id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    // Setters (modificar)
    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

}