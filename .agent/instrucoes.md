# Instruções para o Agente OpenCode

## Estado do Repositório & Aviso de Migração
- **CRÍTICO**: O `README.md` descreve o estado *alvo* do repositório (Maven, Java 17, estrutura `src/main/java`), e o repositório já foi movido para esta estrutura.
- Sempre verifique se o `pom.xml` existe. Se não existir, você está trabalhando em uma base de código que possui a estrutura Maven (`src/main/java`), mas ainda não possui a configuração do Maven. Não execute comandos `mvn` até que o `pom.xml` seja criado.

## Escopo do Projeto & Atividades
- **CRÍTICO**: Sempre consulte o arquivo `docs/trabalho.md` como a fonte primária da verdade para as atividades, entregas e requisitos que devem ser cumpridos ao longo deste projeto. Ele contém o escopo detalhado para testes, inspeção de código e prazos.

## Arquitetura & Ponto de Entrada
- Esta é uma aplicação Java Swing OOP legada que está sendo refatorada para uma aula de Qualidade e Teste de Software.
- O ponto de entrada principal da aplicação é `inicio.MedAlerta` localizado em `src/main/java/inicio/MedAlerta.java`.
- A GUI é construída usando Java Swing (encontrada no pacote `src/main/java/frontend/`).

## Convenções de Teste (Alvo)
- Os testes devem ser escritos em `src/test/java`.
- **Testes Unitários**: Use JUnit (4/5) e Mockito para lógica de negócio isolada (`backend`).
- **Testes de Integração**: Coloque-os em `src/test/java/backend/integracao` para fluxos entre módulos e persistência de arquivos.
- **Testes de GUI / Sistema**: Use `AssertJ Swing` para os componentes Swing (`src/test/java/frontend`).
- **Qualidade de Código & Testes de Mutação**: O projeto utiliza JaCoCo para cobertura e PIT (Pitest) para testes de mutação. Sempre garanta que os novos testes sejam robustos o suficiente para passar na cobertura de mutação.

## Modificando o Código
- Respeite os padrões Java Swing existentes. Não introduza bibliotecas de UI que não sejam Swing.
- Ao escrever testes, concentre-se em identificar dívidas técnicas e refatorar a lógica legada para torná-la mais testável.

## Workflow Git & GitHub
- **Idioma**: Toda a comunicação e documentação no GitHub (PRs, issues, comentários, commits) DEVE ser em português. Isso inclui obrigatoriamente as mensagens de commit.
- **Branch Protection**: NUNCA realize commits diretamente na branch `main`. Sempre utilize branches de funcionalidades (feature branches) e abra um Pull Request para integrar suas alterações. Antes de criar uma nova branch, você DEVE sempre perguntar ao usuário.
- **Pull Requests**: Sempre abra PRs contra o repositório principal (`vaniacourses/trabalho-qualidade-e-teste-grupo-5`). Se estiver usando `gh pr create`, especifique explicitamente o repositório com `--repo vaniacourses/trabalho-qualidade-e-teste-grupo-5`.
