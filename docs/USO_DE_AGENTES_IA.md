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

### Registro A — Expansão de Testes Unitários (`AgendaTest.java`)

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | Aumentar a cobertura de testes unitários da classe `Agenda` para garantir qualidade e eliminar mutantes gerados pelo PITest. |
| **Ferramenta** | Antigravity (Gemini) |
| **Prompt utilizado** | *"Preciso aprimorar os testes unitários de `AgendaTest.java` para cobrir cenários de falha na classe `Agenda.java`, como inserção de contatos nulos, ordenação e comportamento de exceções na conversão de string para agenda. Sugira cenários de teste adicionais."* |
| **Resposta do modelo** | Sugeriu a criação de testes de tratamento de erro (como `testAdicionarContatoComException` e `testStringToAgendaComStringNula`) e o uso do Mockito (`MockedStatic`) para simular o resgate de contatos de arquivos. |
| **Validação humana** | Executei `mvn test` para validar se os novos cenários rodavam perfeitamente e conferi o relatório do JaCoCo para assegurar que todas as linhas e ramos da classe `Agenda` estivessem cobertos. |
| **Resultado** | Testes unitários expandidos e integrados à classe `AgendaTest.java` no branch `leonardo`. |

---

### Registro B — Criação de Teste de Interface E2E (`AgendaContatoMedicoE2ETest.java`)

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | Revisar e corrigir a implementação do teste automatizado ponta a ponta (E2E) para o fluxo de adição de contatos médicos na interface gráfica Swing. |
| **Ferramenta** | Antigravity (Gemini) |
| **Prompt utilizado** | *"Preciso que analise e ajude a corrigir o teste E2E AgendaContatoMedicoE2ETest.java com JUnit e AssertJ Swing. O teste deve simular o login, acessar a tela ContatosMedicos.java, inserir os dados de um novo contato médico e verificar se os componentes da GUI e a persistência em arquivo funcionam sem disparar erros de timeout ou concorrência de threads no Swing."* |
| **Resposta do modelo** | Analisou o código de teste, sugeriu correções na ordenação dos campos na interface do Swing, indicou a inclusão de métodos do GuiActionRunner para evitar problemas de concorrência com a Event Dispatch Thread (EDT) e propôs correções nas asserções sobre a tabela gráfica e no arquivo de registros de usuários. |
| **Validação humana** | Conduzi a execução do teste localmente para assegurar a corretude do fluxo do robô no Swing e conferi se a persistência final no arquivo de dados ocorreu de acordo com o esperado, sem disparar exceções de concorrência. |
| **Resultado** | Classe `AgendaContatoMedicoE2ETest.java` revisada, corrigida e integrada ao repositório. |


---

### Registro C — Eliminação de Mutante Sobrevivente (`Agenda.java`)

| Campo | Conteúdo |
|-------|----------|
| **Atividade** | Eliminação de mutante sobrevivente do PITest no método `removerContato` da classe `Agenda.java`. |
| **Ferramenta** | Antigravity (Gemini) |
| **Prompt utilizado** | *"No relatório do PITest, vejo que há um mutante sobrevivente no método `removerContato` da classe `Agenda.java`. A remoção da chamada `this.setContatos(novaLista)` não altera o resultado dos testes, pois `novaLista` compartilha a mesma referência de memória que `this.contatos`. Como posso corrigir a classe `Agenda.java` para que o teste falhe se a linha de atualização da lista for removida (matando o mutante)?"* |
| **Resposta do modelo** | Sugeriu instanciar uma nova lista (`new ArrayList<>(this.getContatos())`) para desacoplar as referências de memória. Assim, qualquer alteração na cópia não afetará a lista original a menos que `this.setContatos(novaLista)` seja efetivamente chamado, o que mata o mutante sobrevivente de remoção de chamada de método. |
| **Validação humana** | Executei os testes de mutação locais via PIT (`mvn pitest:mutationCoverage`) para a classe `Agenda` e confirmei que a taxa de mutantes mortos subiu para 100%, eliminando o mutante sobrevivente do método. |
| **Resultado** | Modificação na inicialização de `novaLista` no método `removerContato` de `Agenda.java` integrada com sucesso. |



---

## 4. Mateus Magalhães ([@MGLHS](https://github.com/MGLHS))

### Registro A — Revisão e Correção de Testes Unitários e de Mutação (PessoaFisicaTest.java)

| Campo | Conteúdo |
|-------|----------|
| *Atividade* | Revisar e corrigir a cobertura de testes unitários (JaCoCo ≥ 80% de branches) e testes de mutação (PIT ≥ 80%) para a classe PessoaFisica.java. |
| *Ferramenta* | Antigravity (Gemini) |
| *Prompt utilizado* | "Preciso revisar e corrigir os testes da classe PessoaFisicaTest.java para garantir cobertura de branches superior a 80% no JaCoCo e cobertura de mutação superior a 80% no PIT. Sugira correções para cobrir cenários de cadastros de endereço nulo, formatação de CPF e fluxo de salvamento de arquivos de usos." |
| *Resposta do modelo* | Sugeriu correções nos métodos de teste de PessoaFisicaTest.java para validar comportamentos com dados nulos, formatação de CPF e persistência de arquivos, garantindo que mutantes sobreviventes fossem eliminados. |
| *Validação humana* | Executei os testes locais e os relatórios do JaCoCo e PITest para assegurar que todas as linhas, ramos e mutantes foram devidamente testados e superaram o limiar de 80%. |
| *Resultado* | Testes de PessoaFisicaTest.java revisados, corrigidos e validados no repositório. |

---

### Registro B — Revisão e Correção do Teste E2E (FluxoCadastroLoginE2ETest.java)

| Campo | Conteúdo |
|-------|----------|
| *Atividade* | Revisar e corrigir testes automatizados ponta a ponta (E2E) para o fluxo de cadastro seguido de login (Caso de Teste CT-CE-03 — técnica Grafo Causa-Efeito). |
| *Ferramenta* | Antigravity (Gemini) |
| *Prompt utilizado* | "Preciso revisar e corrigir o teste E2E FluxoCadastroLoginE2ETest.java que valida o fluxo de cadastro seguido de login (CT-CE-03) utilizando a técnica de Grafo Causa-Efeito. Corrija o fluxo de fechamento de janelas e a verificação dos botões da tela inicial." |
| *Resposta do modelo* | Apresentou sugestões de reestruturação do teste E2E usando AssertJ Swing para gerenciar de forma estável a abertura de janelas e a validação do estado dos componentes no fluxo de login. |
| *Validação humana* | Executei a suíte de testes funcionais localmente e verifiquei visualmente a execução do robô simulando o fluxo E2E de cadastro e login de pessoa física sem erros. |
| *Resultado* | Classe FluxoCadastroLoginE2ETest.java revisada e salva. |

---

### Registro C — Revisão e Correção da Classe de Produção (PessoaFisica.java)

| Campo | Conteúdo |
|-------|----------|
| *Atividade* | Revisar e corrigir falhas de execução, exceções de ponteiro nulo (NullPointerException) e problemas de concorrência na classe de produção PessoaFisica.java. |
| *Ferramenta* | Antigravity (Gemini) |
| *Prompt utilizado* | "Preciso revisar e corrigir a classe PessoaFisica.java para tratar possíveis NullPointerExceptions ao obter endereços ou salvar usos, ajustar a portabilidade dos caminhos de arquivos para usar barras normais e corrigir a lógica de remoção de medicamentos para evitar exceções de modificação concorrente." |
| *Resposta do modelo* | Recomendou incluir verificações de nulidade para o endereço no método toString, alterar o separador de caminho de arquivos para barras normais (/) e substituir a iteração for-each no método removerUsoNaListaUsoMedicamentos por um loop indexado convencional para garantir segurança de remoção. |
| *Validação humana* | Executei os testes unitários e de mutação após a aplicação dos ajustes de código e assegurei que todos os defeitos de runtime e mutantes foram eliminados. |
| *Resultado* | Ajustes na classe PessoaFisica.java revisados, corrigidos e integrados no repositório. |



---

## 5. Sandro Henrique ([@sandrohmt](https://github.com/sandrohmt))

*(Preencher — classe `FuncoesArquivos`, `FuncoesArquivosTest`, `CadastroFarmaciaE2ETest`)*

### Registro A — Desenvolvimento do teste E2E (`CadastroFarmaciaE2ETest.java`)

| Campo                  | Conteúdo                                                                                                                                                                                                                                                                                                       |
| ---------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Atividade**          | Desenvolver um teste automatizado ponta a ponta (E2E) para validar o fluxo de cadastro de farmácia na interface gráfica Swing utilizando AssertJ Swing.                                                                                                                                                        |
| **Ferramenta**         | ChatGPT                                                                                                                                                                                                                                                                                                        |
| **Prompt utilizado**   | *"Preciso implementar um teste E2E para o fluxo de cadastro de farmácia. Analise as telas, o fluxo da aplicação e as classes relacionadas para sugerir a estrutura do teste, a localização dos componentes e as verificações necessárias."*                                                                    |
| **Resposta do modelo** | A IA auxiliou na análise do fluxo do cadastro, sugeriu a estrutura do teste utilizando AssertJ Swing, indicou possíveis formas de localizar os componentes da interface e apresentou sugestões para validar a persistência dos dados após o cadastro.                                                          |
| **Validação humana**   | Analisei o código sugerido, conferi manualmente as classes da interface e da camada de negócio para verificar se o fluxo correspondia ao funcionamento da aplicação. Após os ajustes necessários, executei o teste localmente para confirmar que a automação reproduzia corretamente o comportamento esperado. |
| **Resultado**          | O teste E2E foi implementado e adaptado à estrutura do projeto, incorporando apenas as sugestões que se mostraram compatíveis com o código da aplicação após revisão e validação manual.                                                                                                                       |


---

### Registro B — Revisão e Correção de Testes Unitários e de Mutação

| Campo                  | Conteúdo                                                                                                                                                                                                                                                                                                |
| ---------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Atividade**          | Revisar os testes unitários existentes e corrigir falhas identificadas durante a execução dos testes de mutação, aprimorando a qualidade dos testes e aumentando a cobertura do projeto.                                                                                                                |
| **Ferramenta**         | ChatGPT                                                                                                                                                                                                                                                                                                 |
| **Prompt utilizado**   | *"Analise estes testes unitários e os resultados dos testes de mutação. Identifique possíveis falhas na cobertura, sugira novos casos de teste e explique como corrigir os mutantes sobreviventes sem comprometer o comportamento esperado da aplicação."*                                              |
| **Resposta do modelo** | A IA sugeriu novos cenários de teste, apontou possíveis lacunas na cobertura e explicou como determinados mutantes poderiam ser eliminados por meio de testes mais específicos. Também apresentou sugestões de refatoração para tornar os testes mais claros e organizados.                             |
| **Validação humana**   | Todas as sugestões foram analisadas e comparadas com os requisitos da aplicação antes de serem utilizadas. Revisei manualmente cada alteração proposta e executei novamente os testes unitários e de mutação para confirmar que as mudanças eram corretas e não introduziam comportamentos indesejados. |
| **Resultado**          | Os testes unitários foram aprimorados após revisão manual das sugestões fornecidas pela IA, reduzindo a quantidade de mutantes sobreviventes e aumentando a confiabilidade da suíte de testes do projeto.                                                                                               |


## Boas práticas adotadas pelo grupo

1. **Nunca commitar cegamente** — toda sugestão da IA passa por execução de testes (`mvn test`) ou leitura do código-fonte.
2. **Rastreabilidade** — casos de teste documentados em [`TESTES_FUNCIONAIS.md`](./TESTES_FUNCIONAIS.md) apontam para métodos reais no repositório.
3. **Transparência** — este arquivo deve ser atualizado sempre que a IA contribuir de forma relevante para código ou documentação entregue.
