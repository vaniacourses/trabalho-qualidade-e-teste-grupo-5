# Divisão sugerida — Entrega 2 (contribuição individual)

Este documento sugere **uma classe por integrante** para cumprir os itens da Entrega 2 em que **cada membro** deve entregar:

1. **Teste estrutural** — ≥ 80% cobertura de **branches** (critério todas-arestas)
2. **Teste baseado em defeitos** — ≥ 80% **mutation score** (PIT) na **mesma classe**
3. **Inspeção Sonar** — corrigir problemas em ≥ 1 classe não-CRUD com complexidade razoável (pode ser a mesma ou outra)

> Fonte dos requisitos: [`TRABALHO.md`](./TRABALHO.md)  
> Grupo: 5 integrantes | Classe sob teste: complexidade ciclomática **≥ 10**, **não-CRUD** (entidade simples)

---

## Resumo da divisão

| Integrante | GitHub | Classe sugerida | Pacote | Arquivo de teste principal |
|------------|--------|-----------------|--------|----------------------------|
| Alexandre Colmenero | [@alexandrelimaxs](https://github.com/alexandrelimaxs) | `Uso` | `backend.usuario` | `src/test/java/backend/usuario/UsoTest.java` |
| Gabriel Soares | [@Gabriel-HS](https://github.com/Gabriel-HS) | `Data` | `backend.gerenciamento` | `src/test/java/backend/gerenciamento/DataTest.java` |
| Leonardo Carvalho | [@leonardocfonseca](https://github.com/leonardocfonseca) | `Agenda` | `backend` | `src/test/java/backend/AgendaTest.java` |
| Mateus Magalhães | [@MGLHS](https://github.com/MGLHS) | `PessoaFisica` | `backend.usuario` | `src/test/java/backend/usuario/PessoaFisicaTest.java` |
| Sandro Henrique | [@sandrohmt](https://github.com/sandrohmt) | `FuncoesArquivos` | `backend` | `src/test/java/backend/integracao/FuncoesArquivosIntegracaoTest.java` *(criar também testes unitários)* |

---

## Detalhamento por integrante

### 1. Alexandre Colmenero — `Uso`

**Por que esta classe?**
- Lógica de negócio com **alta complexidade**: cálculo de horários, validações, serialização CSV.
- Não é entidade CRUD pura — combina regras de dose, intervalo e horários.
- Já é alvo do PIT no `pom.xml` (`backend.usuario.Uso` / `UsoTest`).

**Estado atual (aproximado):**
- Pacote `backend.usuario`: ~47% branches no JaCoCo.
- Vários testes em `UsoTest` ainda falham (código ou teste precisam alinhar).

**Entregas do integrante:**
- [ ] Corrigir/alinhar testes estruturais até **≥ 80% branches** em `Uso` (JaCoCo).
- [ ] Atingir **≥ 80% mutation score** no PIT para `Uso`.
- [ ] Print Sonar antes/depois (classe `Uso` ou outra à escolha).
- [ ] Documentar responsabilidade no README / Plano de Teste.

**Ramos prioritários a cobrir:** `calcularHorariosDeUso` (`intervalo == 0` vs loop), setters com validação, `stringToUso` / `toString`.

---

### 2. Gabriel Soares — `Data`

**Por que esta classe?**
- Centraliza **verificação de hora/dia** e formatação — vários `if`, `switch` e métodos estáticos.
- Complexidade ciclomática adequada (`formatarDia`, `verificarHora`, `verificarDia`, `ehMeiaNoite`).
- Já é alvo do PIT no `pom.xml` (`backend.gerenciamento.Data` / `DataTest`).

**Estado atual (aproximado):**
- Pacote `backend.gerenciamento`: ~33% branches no JaCoCo.
- 3 testes falhando em `DataTest` (bloqueiam o PIT).

**Entregas do integrante:**
- [x] Testes estruturais cobrindo todos os `case` de `formatarDia` e ramos de `verificarHora` / `verificarDia`.
- [x] **≥ 80% branches** em `Data`.
- [x] **≥ 80% mutation score** no PIT.
- [x] Print Sonar antes/depois.

**Ramos prioritários:** segunda chamada de `verificarHora`, entrada inválida em `formatarDia`, `verificarDia(null)`.

---

### 3. Leonardo Carvalho — `Agenda`

**Por que esta classe?**
- Gerencia contatos com **múltiplos fluxos** (adicionar, alterar, remover, `stringToAgenda` por tipo).
- Não é entidade — é serviço de domínio com busca, ordenação e persistência indireta.
- Já possui suíte grande (`AgendaTest`, ~24 testes).

**Estado atual (aproximado):**
- Pacote `backend` agregado em ~84% branches (várias classes); `Agenda` individual precisa ser confirmada no relatório HTML.
- Alguns testes de `AgendaTest` ainda falham.

**Entregas do integrante:**
- [ ] Confirmar no JaCoCo a cobertura **da classe `Agenda`** (não só do pacote).
- [ ] Levar `Agenda` a **≥ 80% branches**.
- [ ] Adicionar `Agenda` ao `pitest-maven-plugin` no `pom.xml` e atingir **≥ 80% mutação**.
- [ ] Print Sonar antes/depois.

**Ramos prioritários:** contato não encontrado vs encontrado, `stringToAgenda` (`usuario` / `farmacia` / `medico`), agenda vazia em `toString`.

---

### 4. Mateus Magalhães — `PessoaFisica`

**Por que esta classe?**
- Uma das classes **mais complexas** do sistema (~400 linhas): login, persistência em arquivo, lista de usos, atualização de quantidades.
- Muitos desvios condicionais e integração com `Uso` e `FuncoesArquivos`.
- Já existe `PessoaFisicaTest` (~22 testes) e testes de integração.

**Estado atual (aproximado):**
- Vários testes falhando em `PessoaFisicaTest` e integração.
- PIT ainda **não** inclui esta classe no `pom.xml`.

**Entregas do integrante:**
- [ ] Expandir/refinar testes unitários com **Mockito** para isolar I/O de arquivo.
- [ ] **≥ 80% branches** em `PessoaFisica`.
- [ ] Configurar PIT para `PessoaFisica` / `PessoaFisicaTest` e atingir **≥ 80% mutação**.
- [ ] Print Sonar antes/depois.

**Ramos prioritários:** resgate de arquivo, atualização/remoção de usos, validações de lista nula, caminhos de autenticação.

---

### 5. Sandro Henrique — `FuncoesArquivos`

**Por que esta classe?**
- Utilitário de **I/O com alta complexidade**: muitos métodos com `try/catch`, loops, `if/else` (criar, ler, alterar, checar existência).
- Não é entidade CRUD — é camada de persistência.
- Hoje só há testes de **integração**; faltam testes unitários dedicados (com arquivos temporários).

**Estado atual (aproximado):**
- Cobertura baixa em métodos não exercitados pelos testes de integração.
- PIT ainda **não** inclui esta classe.

**Entregas do integrante:**
- [ ] Criar `FuncoesArquivosTest.java` em `src/test/java/backend/` (unitário).
- [ ] Manter/ajustar `FuncoesArquivosIntegracaoTest` se necessário.
- [ ] **≥ 80% branches** em `FuncoesArquivos`.
- [ ] Configurar PIT e atingir **≥ 80% mutação**.
- [ ] Print Sonar antes/depois (classe com muitos code smells de `printStackTrace` — boa para inspeção).

**Ramos prioritários:** arquivo existe vs não existe, linha encontrada em `alterarLinhaArquivo`, exceções de I/O, `checarExistenciaNomeArquivo`.

---

## Classes reserva (se houver conflito ou troca)

| Classe | Pacote | Observação |
|--------|--------|------------|
| `Gerenciador` | `backend.gerenciamento` | Muito complexa (threads, loop infinito), mas **difícil de testar** sem refatoração. Reserva para quem aceitar extrair lógica testável. |
| `PessoaJuridica` | `backend.farmacia` | Estoque + persistência; menos testes hoje (~3 em `PessoaJuridicaTest`). |
| `Medicamento` | `backend` | Muitos testes (~30), mas pode ser vista como **entidade** — confirmar com a professora. |
| `Autenticacao` | `backend` | Menor, porém com regras de senha; pode servir se outra classe for trocada. |

---

## Checklist técnico comum (todos os 5)

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home

# Cobertura (JaCoCo)
mvn clean test -Dmaven.test.failure.ignore=true
open target/site/jacoco/index.html
# → abrir SUA classe → coluna Branches ≥ 80%

# Mutação (PIT) — após suíte verde na sua classe
mvn clean compile test-compile org.pitest:pitest-maven:mutationCoverage
open target/pit-reports/*/index.html

# Sonar
mvn clean verify sonar:sonar
```

**Atenção:** o `pom.xml` hoje só configura PIT para `Uso` e `Data`. Cada integrante das classes `Agenda`, `PessoaFisica` e `FuncoesArquivos` precisará **incluir sua classe** em `<targetClasses>` e `<targetTests>` do `pitest-maven-plugin`.

---

## Como registrar no repositório

1. Atualizar a seção **Papéis e Responsabilidades** do [`PLANO_DE_TESTE.md`](./PLANO_DE_TESTE.md) com a tabela acima.
2. Adicionar link a este documento no [`README.md`](../README.md) (Segunda Entrega).
3. No Google Docs (colaborativo), repetir a divisão para a professora ver contribuição individual.

---

## Critérios de aceite (por membro)

| Critério | Ferramenta | Meta |
|----------|------------|------|
| Todas-arestas | JaCoCo (Branches) | ≥ 80% na **sua** classe |
| Baseada em defeitos | PIT (Mutation Score) | ≥ 80% na **mesma** classe |
| Inspeção | SonarQube | Print antes + depois das correções |
| Complexidade | Análise estática / manual | CC ≥ 10 na classe sob teste |
| Tipo de classe | — | Não-CRUD (não só getters/setters) |

---

*Documento sugerido pela equipe — ajuste a divisão em conjunto e valide com a professora se alguma classe for considerada entidade simples.*
