# Aurum-java

> ⚠️ **ESTADO DO PROJETO: ARQUIVADO**
>
> Este projeto foi desenvolvido como um trabalho acadêmico em 2025 e **está arquivado**. Ele não receberá novas atualizações, correções ou suporte. O código é mantido publicamente apenas para fins de histórico e consulta acadêmica.

**Aurum** é um sistema desktop para gerenciamento de finanças pessoais desenvolvido em Java. O aplicativo permite o registro de receitas e despesas, visualização de saldo mensal e categorização de transações, focando em uma interface limpa e funcional.

Este software foi desenvolvido como Trabalho de Conclusão da disciplina de **Programação III** do Curso Técnico em Informática do **Instituto Federal Farroupilha - Campus Santo Augusto**.

## 📚 Documentação e Relatório Técnico

Para detalhes aprofundados sobre a arquitetura do sistema, modelagem do banco de dados e decisões de projeto (como a adaptação do padrão MVC), consulte o relatório completo incluído neste repositório:

📄 **[Ler Relatório Final de Desenvolvimento (PDF)](docs/relatorio.pdf)**

## 📋 Funcionalidades

O sistema atende aos seguintes Requisitos Funcionais definidos no projeto:

* **Dashboard Interativo:**
    * Seleção de Mês/Ano para filtragem de dados.
    * Cálculo automático de totais (Receitas e Despesas).
    * Exibição do Saldo com feedback visual (🟢 Verde / 🔴 Vermelho).
* **Gerenciamento Financeiro:**
    * Cadastro rápido de novas transações.
    * Classificação de gastos por categorias.
* **Gestão de Categorias:**
    * CRUD completo para categorias personalizadas.
    * Integridade referencial (transações não são perdidas se a categoria for excluída).

## 🛠️ Tecnologias

* **Linguagem:** Java (JDK 21+)
* **Interface:** Swing com *FlatLaf* (Look and Feel).
* **Banco de Dados:** MySQL.
* **Arquitetura:** MVC (Model-View-Controller).

## 👨‍💻 Autor

**Arthur Vinicius Willers**
* Instituto Federal Farroupilha - Campus Santo Augusto

## 📄 Licença

Este projeto está licenciado sob a licença MIT.
