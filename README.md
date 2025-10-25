# ☕ Sistema de Gerenciamento de Cafeteria

Projeto CRUD (Create, Read, Update, Delete) para gestão do catálogo de produtos de uma cafeteria, desenvolvido como requisito técnico para avaliação da disciplina de Programação Web.

---

## 📋 Requisitos Funcionais Implementados

O sistema oferece um gerenciamento robusto com segurança de acesso:

* **Autenticação Completa:** Implementação de **Login** e **Logout** utilizando `HttpSession` para gerenciar a sessão do usuário.
* **Segurança de Páginas:** Proteção total das rotas de gerenciamento; o acesso só é liberado para usuários autenticados.
* **CRUD Completo:** Cadastro, Listagem, Edição e Exclusão de produtos.
* **Pesquisa Persistente:** O filtro de busca (por nome) é **armazenado na sessão** e persiste mesmo após a navegação do usuário.

---

## 🛠️ Stack Tecnológica & Arquitetura

O projeto segue a arquitetura de **5 Camadas (Model, Repository, Service, Controller, View)** e cumpre todos os requisitos técnicos:

### Back-end
* **Linguagem:** Java (JDK 17)
* **Framework:** Spring Boot 3
* **Persistência:** JPA/Hibernate e MySQL.
* **Validação:** Tratamento de erros de validação (preço, estoque) na camada Service.

### Front-end & Interface
* **Template Engine:** Thymeleaf (para views dinâmicas e mensagens de sucesso/erro).
* **Design:** Bootstrap 5 (utilizado em formulários, tabelas e alertas).
* **Controle de Versão:** GitHub.
