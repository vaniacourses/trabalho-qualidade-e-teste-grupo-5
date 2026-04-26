# Contexto para Replicação de Issues e Milestones - MedAlertaV2

Este documento contém os detalhes das Milestones e Issues do projeto, integrados com os requisitos definidos no `TRABALHO.md`.

---

## 🎯 Milestones

### 1. Entrega 1
- **Título**: Entrega 1
- **Status**: Open
- **Data de Entrega**: 2026-04-27
- **Descrição**:
  **Escopo e Testes Iniciais (Peso 3)**
  Nesta entrega o foco será o planejamento e a introdução da estratégia de testes no sistema legado:
  - **Descrição do escopo**: Definir quais módulos serão testados no Plano de Teste e disponibilizar o código-fonte original.
  - **Plano de teste**: Elaborar o documento de Plano de Teste (usando o template em Markdown), incluindo os artefatos gerados e ferramentas.
  - **Testes Unitários**: Projetar casos de testes unitários para pelo menos 1 classe (com complexidade razoável, não CRUD) para cada membro do grupo.
  - **Testes Manuais**: Projetar e executar casos de teste manuais (pelo menos 1 funcionalidade por membro), com ao menos um cenário usando o Testlink (ou ferramenta similar).
  - **Relatório de Bugs**: Reportar as issues encontradas durante os testes manuais.

### 2. Entrega 2
- **Título**: Entrega 2
- **Status**: Open
- **Data de Entrega**: 2026-06-17
- **Descrição**:
  **Testes Avançados e Refatoração (Peso 5)**
  Nesta entrega o foco é expandir a cobertura, aplicar automação e utilizar ferramentas de análise:
  - **Testes Unitários**: Melhorar/aumentar os casos, isolando as dependências.
  - **Testes de Integração**: Implementar casos de teste de integração.
  - **Atributos ISO 25010**: Indicar as medidas de cada atributo de qualidade seguindo uma escala e justificá-las.
  - **Testes de Sistema**: Implementar testes via Selenium para requisitos funcionais e avaliar ao menos um requisito não funcional (desempenho/segurança).
  - **Técnicas de Cobertura**:
    - *Funcional e Estrutural*: Atingir ao menos 80% de cobertura no critério todas-arestas (1 classe de alta complexidade por membro).
    - *Mutação (Baseada em Defeitos)*: Atingir 80% de escore de mutação no PIT nas classes avaliadas no teste estrutural.
  - **Inspeção de Código**: Rodar ferramenta de análise estática (SonarQube) e resolver os problemas de pelo menos 1 classe por membro (enviar prints antes/depois).

---

## 📝 Issues (Filtro: Ignorando 'Not Planned')

### Agrupadores e Planejamento

#### [#14] Elaborar o Plano de Teste
- **Status**: OPEN
- **Milestone**: Entrega 1
- **Labels**: `documentation`
- **Descrição**: Preencher o documento `PlanoDeTeste.md` com as informações do projeto: Definir o escopo de testes, papéis e responsabilidades, cronograma, recursos e ambientes.

#### [#12] Estruturar repositório e documentações iniciais do projeto
- **Status**: CLOSED (COMPLETED)
- **Milestone**: Entrega 1
- **Assignee**: alexandrelimaxs
- **Labels**: `documentation`
- **Descrição**: Configurar e revisar a documentação inicial, incluindo README.md, estruturação do TRABALHO.md e criação do guia AGENTS.md.

#### [#2] Criar arquivo .md para o Plano de Testes
- **Status**: CLOSED (COMPLETED)
- **Milestone**: Entrega 1
- **Assignee**: leonardocfonseca
- **Labels**: `documentation`
- **Descrição**: Criar o documento de Plano de Testes em formato Markdown (.md) definindo módulos, artefatos e ferramentas.

---

### Testes Unitários (Entrega 1)

#### [#13] Criar testes unitários para as classes selecionadas
- **Status**: CLOSED (COMPLETED)
- **Milestone**: Entrega 1
- **Labels**: `tests`
- **Descrição**: Issue pai que agrupa as tarefas de criação de testes unitários.
- **Sub-tarefas**: #4, #5, #6, #7, #8. (Notas: #3, #9 e #10 foram descartadas).

#### [#8] Criar Teste Unitário para a classe backend.Autenticacao
- **Status**: CLOSED (COMPLETED)
- **Milestone**: Entrega 1
- **Assignee**: Gabriel-HS
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.Autenticacao`, focando em isolar dependências.

#### [#7] Criar Teste Unitário para a classe backend.usuario.Uso
- **Status**: CLOSED (COMPLETED)
- **Milestone**: Entrega 1
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.usuario.Uso`.

#### [#6] Criar Teste Unitário para a classe backend.gerenciamento.Data
- **Status**: CLOSED (COMPLETED)
- **Milestone**: Entrega 1
- **Assignee**: sandrohmt
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.gerenciamento.Data`.

#### [#5] Criar Teste Unitário para a classe backend.Agenda
- **Status**: CLOSED (COMPLETED)
- **Milestone**: Entrega 1
- **Assignee**: leonardocfonseca
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.Agenda`.

#### [#4] Criar Teste Unitário para a classe backend.usuario.PessoaFisica
- **Status**: CLOSED (COMPLETED)
- **Milestone**: Entrega 1
- **Assignee**: Gabriel-HS
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.usuario.PessoaFisica`.

---

### Testes Manuais (Entrega 1)

#### [#20] Projetar e executar casos de teste manuais
- **Status**: OPEN
- **Milestone**: Entrega 1
- **Labels**: `tests`
- **Descrição**: Issue pai para tarefas de testes manuais (1 funcionalidade por membro).
- **Sub-tarefas**: #15, #16, #17, #18, #19.

#### [#19] Teste Manual: Funcionalidade 5 (Sandro)
- **Status**: CLOSED (COMPLETED)
- **Milestone**: Entrega 1
- **Assignee**: sandrohmt
- **Labels**: `tests`

#### [#18] Teste Manual: Funcionalidade 4 (Mateus)
- **Status**: OPEN
- **Milestone**: Entrega 1
- **Assignee**: MGLHS
- **Labels**: `tests`

#### [#17] Teste Manual: Funcionalidade 3 (Leonardo)
- **Status**: OPEN
- **Milestone**: Entrega 1
- **Assignee**: leonardocfonseca
- **Labels**: `tests`

#### [#16] Teste Manual: Funcionalidade 2 (Gabriel)
- **Status**: OPEN
- **Milestone**: Entrega 1
- **Assignee**: Gabriel-HS
- **Labels**: `tests`

#### [#15] Teste Manual: Funcionalidade 1 (Alexandre)
- **Status**: OPEN
- **Milestone**: Entrega 1
- **Assignee**: alexandrelimaxs
- **Labels**: `tests`

---

## 📋 Resumo de Requisitos (Baseado no TRABALHO.md)

### Membros do Grupo (Identificados via Assignees):
1. **Alexandre Colmenero** (`alexandrelimaxs`)
2. **Gabriel** (`Gabriel-HS`)
3. **Leonardo Carvalho Fonseca** (`leonardocfonseca`)
4. **Sandro Henrique** (`sandrohmt`)
5. **Mateus** (`MGLHS`)

### Principais Entregas Pendentes (Para Entrega 2):
- Implementação de Testes de Integração.
- Medição de atributos ISO 25010.
- Testes de Sistema com Selenium (Funcionais e No-Funcionais).
- Cobertura Estrutural (80% todas-arestas em classes complexas - CC >= 10).
- Cobertura de Mutação (80% PIT).
- Inspeção de código e correção via SonarQube.
