# Uso de agentes de IA no projeto MedAlerta

Este documento registra **onde e como** cada integrante utilizou ferramentas de IA (ex.: Cursor, ChatGPT, Copilot) no trabalho. O objetivo é deixar explícita a colaboração humano–máquina, conforme boas práticas acadêmicas.

> **Como preencher:** cada integrante deve adicionar ao menos um registro real. Inclua o prompt (ou um resumo fiel), o que foi gerado pela IA e **como você validou** a resposta antes de aceitar.

---

## Modelo de registro (copiar por uso)

| Campo | Descrição |
|-------|-----------|
| **Atividade** | O que você estava fazendo (ex.: testes unitários, documentação, Sonar) |
| **Ferramenta** | Cursor Agent, ChatGPT, etc. |
| **Prompt utilizado** | Texto enviado ao agente (ou trecho representativo) |
| **Resposta do modelo** | O que a IA sugeriu (resumo) |
| **Validação humana** | Como você conferiu se estava correto (execução de testes, leitura do código, comparação com enunciado, etc.) |
| **Resultado** | O que foi incorporado ao repositório (ou descartado) |

---

## 1. Gabriel Soares ([@Gabriel-HS](https://github.com/Gabriel-HS))

### Registro A — Auditoria e correção de documentação (Entrega 2)

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | Revisar inconsistências entre README, plano de testes, `pom.xml` e código; corrigir rastreabilidade documental. |
| **Ferramenta** | Cursor (modo Agent) |
| **Prompt utilizado** | *"eu quero que procure por inconsistencias documentais no repo e me aponte as principais por prioridade"* |
| **Resposta do modelo** | Listou divergências priorizadas: comandos Maven (`verify`/PIT/Sonar) vs `pom.xml`, nome do Sandro inconsistente, Leonardo ausente no README, referências a classes de teste inexistentes em `TESTES_FUNCIONAIS.md`, checklist desatualizado em `DIVISAO_CLASSES_ENTREGA2.md`. |
| **Validação humana** | Conferi manualmente: li o `pom.xml` (sem Failsafe, PIT fora do lifecycle, Sonar via CLI), busquei classes citadas com grep (`FarmaciaCadastroTest`, `GerenciadorTest`, etc.) e confirmei que não existem; comparei nomes nos integrantes do README e do plano. |
| **Resultado** | Aceitei o diagnóstico. Em seguida pedi correção documental (itens 1–5). |

**Prompt de correção (rodada 1):**

> *"corrija tudo até o ponto 5, sendo simples na solução..."*

**Prompt de correção (rodada 2):**

> *"revise novamente a documentação e procure por inconsistencias"*

> *"De forma simples, corrija todos os pontos elencados..."*

**Validação:** comparei tabelas de `TESTES_FUNCIONAIS.md` com os métodos reais (`grep` nos arquivos de teste) e alinhei README, plano e divisão de classes.

---

### Registro B — Análise estática com SonarQube

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | Inspeção de código e envio de métricas (Entrega 2). |
| **Ferramenta** | Cursor (orientação inicial) + execução manual |
| **Prompt utilizado** | *(consulta ao guia do repositório e documentação SonarQube para subir container Docker e rodar `sonar:sonar` na linha de comando)* |
| **Resposta do modelo** | Passos para `docker run` com imagem `sonarqube:community`, gerar token e executar `mvn clean test jacoco:report sonar:sonar` com `-Dsonar.coverage.jacoco.xmlReportPaths=...`. |
| **Validação humana** | Subi o Sonar localmente com Docker, acessei `http://localhost:9000`, gerei token e rodei o Maven na raiz do projeto. Verifiquei no dashboard se o projeto `medalerta` apareceu com issues e cobertura importada do JaCoCo. |
| **Resultado** | Análise executada localmente (Docker + `sonar:sonar`). Evidências registradas na [Apresentação Entrega 2](https://canva.link/aop9a2ac2q3hgo9), como entrega compartilhada do grupo. |

---

### Registro C — Revisão de rastreabilidade (Entrega 2)

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | Segunda passada: alinhar dados dos casos de teste ao código, ISO 25010 com escala, testes RNF e links de entrega. |
| **Ferramenta** | Cursor (modo Agent) |
| **Prompt utilizado** | *"revise novamente a documentação e procure por inconsistencias"* → *"corrija todos os pontos elencados"* |
| **Resposta do modelo** | Ajustes em `TESTES_FUNCIONAIS.md`, escala 1–5 em ISO 25010, tabela de testes complementares, metodologia do plano atualizada. |
| **Validação humana** | Conferi emails/senhas nos testes E2E, confirmei responsáveis com a divisão do grupo. |
| **Resultado** | Documentação consolidada; evolução do teste RNF de desempenho registrada no Registro D. |

---

### Registro D — Refinamento do teste de estresse (RNF desempenho)

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | Melhorar o teste de estresse com múltiplos usuários (`EstresseMultiUsuarioTest`) e alinhar referências na documentação. |
| **Ferramenta** | Cursor (modo Agent) |
| **Prompt utilizado** | *"Já implementei um teste de estresse simulando vários usuários no sistema. Revise o código, sugira melhorias na implementação e ajuste a documentação que ainda cite o teste antigo de performance."* |
| **Resposta do modelo** | Retornou sugestões de organização do código, pequenos ajustes nos asserts e indicações de onde atualizar a documentação do projeto. |
| **Validação humana** | O teste já havia sido escrito por mim, usei a IA apenas como apoio na revisão. Conferi as alterações e executei a suíte de testes antes de aceitar. |
| **Resultado** | Versão refinada de `EstresseMultiUsuarioTest` mantida no repositório, referências documentais atualizadas. |

---

### Registro E — Criação deste documento de uso de agentes de IA

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | Elaborar a documentação de transparência sobre o uso de IA no projeto (`docs/USO_DE_AGENTES_IA.md`). |
| **Ferramenta** | Cursor (modo Agent) |
| **Prompt utilizado** | *"Preciso documentar como o grupo usa agentes de IA no MedAlerta. Montei a estrutura do registro (atividade, ferramenta, prompt, resposta, validação e resultado). Gere o arquivo em Markdown com essa estrutura, seções por integrante e instruções de preenchimento."* |
| **Resposta do modelo** | Retornou o esqueleto do documento em Markdown: introdução, modelo de registro reutilizável, seções para cada integrante e boas práticas do grupo. |
| **Validação humana** | A estrutura e o objetivo do documento foram definidos por mim, a IA apenas formatou o conteúdo em Markdown. Revisei o texto, ajustei os registros reais e confirmei que o arquivo reflete o uso de IA no projeto. |
| **Resultado** | `docs/USO_DE_AGENTES_IA.md` adicionado ao repositório como registro compartilhado de transparência acadêmica. |

---

## 2. Alexandre Colmenero ([@alexandrelimaxs](https://github.com/alexandrelimaxs))

*(Preencher — classe `Uso`, `UsoTest`, `CadastroMedicamentoE2ETest`)*

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | |
| **Ferramenta** | |
| **Prompt utilizado** | |
| **Resposta do modelo** | |
| **Validação humana** | |
| **Resultado** | |

---

## 3. Leonardo Carvalho ([@leonardocfonseca](https://github.com/leonardocfonseca))

*(Preencher — classe `Agenda`, `AgendaTest`, `AgendaContatoMedicoE2ETest`)*

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | |
| **Ferramenta** | |
| **Prompt utilizado** | |
| **Resposta do modelo** | |
| **Validação humana** | |
| **Resultado** | |

---

## 4. Mateus Magalhães ([@MGLHS](https://github.com/MGLHS))

*(Preencher — classe `PessoaFisica`, `PessoaFisicaTest`, `FluxoCadastroLoginE2ETest`)*

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | |
| **Ferramenta** | |
| **Prompt utilizado** | |
| **Resposta do modelo** | |
| **Validação humana** | |
| **Resultado** | |

---

## 5. Sandro Henrique ([@sandrohmt](https://github.com/sandrohmt))

*(Preencher — classe `FuncoesArquivos`, `FuncoesArquivosTest`, `CadastroFarmaciaE2ETest`)*

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | |
| **Ferramenta** | |
| **Prompt utilizado** | |
| **Resposta do modelo** | |
| **Validação humana** | |
| **Resultado** | |

---

## Boas práticas adotadas pelo grupo

1. **Nunca commitar cegamente** — toda sugestão da IA passa por execução de testes (`mvn test`) ou leitura do código-fonte.
2. **Rastreabilidade** — casos de teste documentados em [`TESTES_FUNCIONAIS.md`](./TESTES_FUNCIONAIS.md) apontam para métodos reais no repositório.
3. **Transparência** — este arquivo deve ser atualizado sempre que a IA contribuir de forma relevante para código ou documentação entregue.
