# Divisão sugerida — Entrega 2 (contribuição individual)

Este documento sugere **uma classe por integrante** para cumprir os itens da Entrega 2 em que **cada membro** deve entregar:

1. **Teste estrutural** — ≥ 80% cobertura de **branches** (critério todas-arestas)
2. **Teste baseado em defeitos** — ≥ 80% **mutation score** (PIT) na **mesma classe**
3. **Inspeção Sonar** — cada integrante corrige issues em ≥ 1 classe; a **evidência** (prints e resultados) é **compartilhada** e consta na [Apresentação Entrega 2](https://canva.link/aop9a2ac2q3hgo9)
4. **Teste E2E (sistema)** — **pelo menos um** teste ponta a ponta por integrante (Selenium ou similar)

> Fonte dos requisitos: [`TRABALHO.md`](./TRABALHO.md)  
> Grupo: 5 integrantes | Classe sob teste: complexidade ciclomática **≥ 10**, **não-CRUD** (entidade simples)

### Ferramenta E2E neste projeto

O MedAlerta é uma aplicação **Swing (desktop)**, não web. Por isso, o equivalente a *“Selenium ou similar”* adotado no repositório é o **AssertJ Swing**, com suíte em `src/test/java/frontend/`. Cada integrante deve implementar ou manter **ao menos uma classe de teste E2E** nesse pacote, exercitando um requisito funcional completo pela interface (menu → telas → resultado esperado).

### Inspeção SonarQube (equipe)

A análise com SonarQube e as evidências de inspeção (antes/depois das correções) são **responsabilidade compartilhada do repositório**, não de pastas ou prints individuais no GitHub. Os registros visuais estão na [**Apresentação Entrega 2**](https://canva.link/aop9a2ac2q3hgo9). Cada integrante continua responsável por corrigir issues na **sua** classe sob teste.

---

## Resumo da divisão

| Integrante | GitHub | Classe (backend) | Teste unitário/estrutural | Cenário E2E sugerido | Classe E2E (`src/test/java/frontend/`) |
|------------|--------|------------------|----------------------------|----------------------|---------------------------------------|
| Alexandre Colmenero | [@alexandrelimaxs](https://github.com/alexandrelimaxs) | `Uso` | `backend/usuario/UsoTest.java` | Cadastro de medicamento após login ([TM-04](./testes_manuais/TM-04-cadastro-de-medicamentos.md)) | `CadastroMedicamentoE2ETest.java` |
| Gabriel Soares | [@Gabriel-HS](https://github.com/Gabriel-HS) | `Data` | `backend/gerenciamento/DataTest.java` | Login de pessoa com credenciais válidas ([TM-02](./testes_manuais/TM-02-autentica-o-de-usu-rio-pessoa-.md)) | `InicioE2ETest.java` |
| Leonardo Carvalho | [@leonardocfonseca](https://github.com/leonardocfonseca) | `Agenda` | `backend/AgendaTest.java` | Adicionar contato médico na agenda ([TM-03](./testes_manuais/TM-03-adicionar-contato-m-dico.md)) | `AgendaContatoMedicoE2ETest.java` |
| Mateus Magalhães | [@MGLHS](https://github.com/MGLHS) | `PessoaFisica` | `backend/usuario/PessoaFisicaTest.java` | Cadastro completo + login com as mesmas credenciais ([TM-05](./testes_manuais/TM-05-cadastro-de-usu-rio-como-pesso.md) / [TM-07](./testes_manuais/TM-07-cadastro-de-usu-rio-como-pesso.md)) | `FluxoCadastroLoginE2ETest.java` |
| Sandro Henrique | [@sandrohmt](https://github.com/sandrohmt) | `FuncoesArquivos` | `backend/FuncoesArquivosTest.java` + integração | Cadastro de farmácia pela interface ([TM-08](./testes_manuais/TM-08-cadastro-de-farmacia.md) / [TM-09](./testes_manuais/TM-09-cadastro-de-farmacia-sem-cnpj.md)) | `CadastroFarmaciaE2ETest.java` |

---

## Detalhamento por integrante

### 1. Alexandre Colmenero — `Uso`

**Por que esta classe?**
- Lógica de negócio com **alta complexidade**: cálculo de horários, validações, serialização CSV.
- Não é entidade CRUD pura — combina regras de dose, intervalo e horários.
- Já é alvo do PIT no `pom.xml` (`backend.usuario.Uso` / `UsoTest`).

**Estado atual:**
- Classe alvo do PIT no `pom.xml` (`backend.usuario.Uso` / `UsoTest`).
- Confirmar cobertura de branches ≥ 80% no relatório JaCoCo.

**Entregas do integrante:**
- [ ] Confirmar **≥ 80% branches** em `Uso` (JaCoCo).
- [ ] Confirmar **≥ 80% mutation score** no PIT para `Uso`.
- [ ] Corrigir issues Sonar na classe `Uso` (evidências na apresentação do grupo).
- [x] **E2E:** `CadastroMedicamentoE2ETest.java` — fluxo login → cadastro de medicamento → confirmação na interface.
- [x] Documentar responsabilidade no README / Plano de Teste.

**Ramos prioritários a cobrir:** `calcularHorariosDeUso` (`intervalo == 0` vs loop), setters com validação, `stringToUso` / `toString`.

**E2E:** reutilizar `FrontendTestSupport` para preparar usuário logado; validar que o medicamento aparece na home ou em mensagem de sucesso.

---

### 2. Gabriel Soares — `Data`

**Por que esta classe?**
- Centraliza **verificação de hora/dia** e formatação — vários `if`, `switch` e métodos estáticos.
- Complexidade ciclomática adequada (`formatarDia`, `verificarHora`, `verificarDia`, `ehMeiaNoite`).
- Já é alvo do PIT no `pom.xml` (`backend.gerenciamento.Data` / `DataTest`).

**Estado atual:**
- Classe alvo do PIT no `pom.xml` (`backend.gerenciamento.Data` / `DataTest`).

**Entregas do integrante:**
- [x] Testes estruturais cobrindo todos os `case` de `formatarDia` e ramos de `verificarHora` / `verificarDia`.
- [x] **≥ 80% branches** em `Data`.
- [x] **≥ 80% mutation score** no PIT.
- [x] Correções Sonar na classe `Data` (evidências na apresentação do grupo).
- [x] **E2E:** implementar `InicioE2ETest.java` — menu → login pessoa → home com usuário autenticado.

**Ramos prioritários:** segunda chamada de `verificarHora`, entrada inválida em `formatarDia`, `verificarDia(null)`.

**E2E:** cenário mínimo de autenticação funcional; usar dados isolados via `FrontendTestSupport.prepararUsuarioDeTeste(...)`.

---

### 3. Leonardo Carvalho — `Agenda`

**Por que esta classe?**
- Gerencia contatos com **múltiplos fluxos** (adicionar, alterar, remover, `stringToAgenda` por tipo).
- Não é entidade — é serviço de domínio com busca, ordenação e persistência indireta.
- Já possui suíte grande (`AgendaTest`, ~24 testes).

**Estado atual:**
- Classe alvo do PIT no `pom.xml` (`backend.Agenda` / `AgendaTest`).
- Suíte em `AgendaTest` (~24 testes) e E2E em `AgendaContatoMedicoE2ETest`.

**Entregas do integrante:**
- [ ] Confirmar **≥ 80% branches** em `Agenda` (JaCoCo).
- [ ] Confirmar **≥ 80% mutation score** no PIT para `Agenda`.
- [ ] Corrigir issues Sonar na classe `Agenda` (evidências na apresentação do grupo).
- [x] **E2E:** `AgendaContatoMedicoE2ETest.java` — login → adicionar médico → contato visível na agenda.

**Ramos prioritários:** contato não encontrado vs encontrado, `stringToAgenda` (`usuario` / `farmacia` / `medico`), agenda vazia em `toString`.

**E2E:** alinhado ao teste manual [TM-03](./testes_manuais/TM-03-adicionar-contato-m-dico.md); cobrir caminho feliz e, se possível, validação de campos vazios.

---

### 4. Mateus Magalhães — `PessoaFisica`

**Por que esta classe?**
- Uma das classes **mais complexas** do sistema (~400 linhas): login, persistência em arquivo, lista de usos, atualização de quantidades.
- Muitos desvios condicionais e integração com `Uso` e `FuncoesArquivos`.
- Já existe `PessoaFisicaTest` (~22 testes) e testes de integração.

**Estado atual:**
- Classe alvo do PIT no `pom.xml` (`backend.usuario.PessoaFisica` / `PessoaFisicaTest`).
- E2E em `FluxoCadastroLoginE2ETest` (caso **CT-CE-03**).

**Entregas do integrante:**
- [ ] Confirmar **≥ 80% branches** em `PessoaFisica` (JaCoCo).
- [ ] Confirmar **≥ 80% mutation score** no PIT para `PessoaFisica`.
- [ ] Corrigir issues Sonar na classe `PessoaFisica` (evidências na apresentação do grupo).
- [x] **E2E:** `FluxoCadastroLoginE2ETest.java` — cadastro → login → home autenticada.

**Ramos prioritários:** resgate de arquivo, atualização/remoção de usos, validações de lista nula, caminhos de autenticação.

**E2E:** fluxo completo que exercita persistência de `PessoaFisica` via GUI; reutilizar `FrontendTestSupport` para preencher formulário e isolar dados de teste.

---

### 5. Sandro Henrique — `FuncoesArquivos`

**Por que esta classe?**
- Utilitário de **I/O com alta complexidade**: muitos métodos com `try/catch`, loops, `if/else` (criar, ler, alterar, checar existência).
- Não é entidade CRUD — é camada de persistência.
- Possui `FuncoesArquivosTest` (unitário) e `FuncoesArquivosIntegracaoTest`.

**Estado atual:**
- `FuncoesArquivosTest.java` e `FuncoesArquivosIntegracaoTest` existem.
- **Pendente:** incluir `FuncoesArquivos` no `pitest-maven-plugin` do `pom.xml`.
- E2E em `CadastroFarmaciaE2ETest`.

**Entregas do integrante:**
- [ ] Confirmar **≥ 80% branches** em `FuncoesArquivos` (JaCoCo).
- [ ] Incluir classe no PIT e atingir **≥ 80% mutação**.
- [ ] Corrigir issues Sonar em `FuncoesArquivos` (evidências na apresentação do grupo).
- [x] **E2E:** `CadastroFarmaciaE2ETest.java` — cadastro de farmácia (caminho feliz e validação sem CNPJ).

**Ramos prioritários:** arquivo existe vs não existe, linha encontrada em `alterarLinhaArquivo`, exceções de I/O, `checarExistenciaNomeArquivo`.

**E2E:** exercita persistência via `FuncoesArquivos` ao cadastrar farmácia; cobrir TM-08 (cadastro válido) e TM-09 (erro sem CNPJ) quando aplicável.

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

# E2E / testes de sistema (AssertJ Swing)
mvn test -Dtest=frontend.<SuaClasseE2E>Test
# ou toda a suíte de frontend:
mvn test -Dtest=frontend.*Test
```

**Atenção:** o `pom.xml` configura PIT para `Uso`, `Data`, `Agenda` e `PessoaFisica`. A classe `FuncoesArquivos` ainda precisa ser incluída em `<targetClasses>` e `<targetTests>` do `pitest-maven-plugin`.

### Testes complementares (fora da classe principal)

| Integrante | Testes adicionais |
|------------|-------------------|
| Gabriel | `EntrarPessoaSegurancaTest`, `EstresseMultiUsuarioTest`, `InicioUsabilidadeTest` |
| Mateus | `CadastroPessoaE2ETest`, `CadastroPessoaValidacaoE2ETest` |
| Sandro | `EntrarFarmaciaE2ETest` |
| Leonardo | CT-CE-02 em `AgendaTest` (unitário) |

### Critérios mínimos do teste E2E (por integrante)

| Item | Esperado |
|------|----------|
| Ferramenta | AssertJ Swing (`org.assertj.swing`) — equivalente local a Selenium |
| Quantidade | **≥ 1** classe de teste E2E **por integrante** |
| Escopo | Fluxo funcional completo pela GUI (não mockar a interface) |
| Isolamento | Dados de teste únicos (e-mail/CPF/CNPJ) para não colidir entre execuções |
| Suporte | Reutilizar `FrontendTestSupport.java` quando possível |
| Documentação | Referenciar o teste manual correspondente em `docs/testes_manuais/` |

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
| Inspeção | SonarQube | Correções por integrante na sua classe; evidências na [Apresentação Entrega 2](https://canva.link/aop9a2ac2q3hgo9) |
| Teste E2E | AssertJ Swing (similar ao Selenium) | **≥ 1** teste de sistema por integrante |
| Complexidade | Análise estática / manual | CC ≥ 10 na classe sob teste |
| Tipo de classe | — | Não-CRUD (não só getters/setters) |
