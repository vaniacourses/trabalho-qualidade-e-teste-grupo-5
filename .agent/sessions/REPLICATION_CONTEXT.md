# Contexto para Replicação de Issues e Milestones - MedAlertaV2

Este documento contém os detalhes das Milestones e Issues do projeto, integrados com os requisitos definidos no `TRABALHO.md`.

---

## 🎯 Milestones

### 1. Entrega 1
- **Título**: Entrega 1
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

#### Elaborar o Plano de Teste
- **Milestone**: Entrega 1
- **Labels**: `documentation`
- **Descrição**: Preencher o documento `PlanoDeTeste.md` com as informações do projeto: Definir o escopo de testes, papéis e responsabilidades, cronograma, recursos e ambientes.

#### Estruturar repositório e documentações iniciais do projeto
- **Milestone**: Entrega 1
- **Assignee**: alexandrelimaxs
- **Labels**: `documentation`
- **Descrição**: Configurar e revisar a documentação inicial, incluindo README.md, estruturação do TRABALHO.md e criação do guia AGENTS.md.

#### Criar arquivo .md para o Plano de Testes
- **Milestone**: Entrega 1
- **Assignee**: leonardocfonseca
- **Labels**: `documentation`
- **Descrição**: Criar o documento de Plano de Testes em formato Markdown (.md) definindo módulos, artefatos e ferramentas.

---

### Testes Unitários (Entrega 1)

#### Criar Teste Unitário para a classe backend.Autenticacao
- **Milestone**: Entrega 1
- **Assignee**: Gabriel-HS
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.Autenticacao`, focando em isolar dependências.

#### Criar Teste Unitário para a classe backend.usuario.Uso
- **Milestone**: Entrega 1
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.usuario.Uso`.

#### Criar Teste Unitário para a classe backend.gerenciamento.Data
- **Milestone**: Entrega 1
- **Assignee**: sandrohmt
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.gerenciamento.Data`.

#### Criar Teste Unitário para a classe backend.Agenda
- **Milestone**: Entrega 1
- **Assignee**: leonardocfonseca
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.Agenda`.

#### Criar Teste Unitário para a classe backend.usuario.PessoaFisica
- **Milestone**: Entrega 1
- **Assignee**: Gabriel-HS
- **Labels**: `tests`
- **Descrição**: Desenvolver casos de teste unitários para a classe `backend.usuario.PessoaFisica`.

---

### Testes Manuais (Entrega 1)

#### Teste Manual: Funcionalidade 5 (Sandro)
- **Milestone**: Entrega 1
- **Assignee**: sandrohmt
- **Labels**: `tests`

#### Teste Manual: Funcionalidade 4 (Mateus)
- **Milestone**: Entrega 1
- **Assignee**: MGLHS
- **Labels**: `tests`

#### Teste Manual: Funcionalidade 3 (Leonardo)
- **Milestone**: Entrega 1
- **Assignee**: leonardocfonseca
- **Labels**: `tests`

#### Teste Manual: Funcionalidade 2 (Gabriel)
- **Milestone**: Entrega 1
- **Assignee**: Gabriel-HS
- **Labels**: `tests`

#### Teste Manual: Funcionalidade 1 (Alexandre)
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
