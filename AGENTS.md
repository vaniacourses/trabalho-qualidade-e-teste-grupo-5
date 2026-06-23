# Instruções para o Agente OpenCode

## Estado do Repositório
- Projeto **Maven** com **Java 17**; `pom.xml` na raiz.
- Estrutura: `src/main/java` (`backend`, `frontend`, `inicio`).

## Escopo do Projeto & Atividades
- **CRÍTICO**: Consulte `docs/TRABALHO.md` como fonte primária de entregas e requisitos.

## Arquitetura & Ponto de Entrada
- Aplicação Java Swing legada refatorada para Qualidade e Teste.
- Entrada: `inicio.MedAlerta` em `src/main/java/inicio/MedAlerta.java`.
- GUI: pacote `src/main/java/frontend/`.

## Convenções de Teste
- Testes em `src/test/java`.
- **Unitários**: JUnit 5 + Mockito em `backend/` (exceção: `AutenticacaoTest` na raiz de `test/java`).
- **Integração**: `src/test/java/backend/integracao`.
- **GUI / Sistema**: AssertJ Swing em `src/test/java/frontend`.
- **Qualidade**: JaCoCo (cobertura), PIT (mutação) e SonarQube (inspeção estática; evidências na apresentação Entrega 2).

## Modificando o Código
- Manter Swing; não introduzir outras bibliotecas de UI.
- Priorizar testabilidade ao refatorar lógica legada.

## Workflow Git & GitHub
- Comunicação e commits em **português**.
- Não commitar direto na `main`; usar feature branches e PRs para `vaniacourses/trabalho-qualidade-e-teste-grupo-5`.
