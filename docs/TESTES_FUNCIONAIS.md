# Documentação de Testes Funcionais

## 1. Técnica: Particionamento em Classes de Equivalência
**Conceito:** Dividir os dados de entrada em grupos (Válidos e Inválidos). Se o teste passa para um valor do grupo, assume-se que passa para todos.

### Caso de Teste 01: Cadastro de Farmácia (Classe Válida)
Este teste valida a classe de "Dados Completos e Corretos".

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-EQ-01 |
| **Responsável** | Sandro Henrique |
| **Nome do Cenário** | Cadastro de Farmácia com Sucesso (Fluxo Feliz) |
| **Origem no Código** | `frontend.CadastroFarmaciaE2ETest -> fluxoCadastroFarmacia_comDadosValidos_deveCriarEEntrarNaHome` |
| **Pré-condições** | Arquivo de farmácias limpo ou inexistente. Menu inicial aberto. |
| **Dados de Entrada** | - Email: "farmacia@e2e.com"<br>- Senha: "senha123"<br>- Nome: "Farmácia E2E"<br>- CNPJ: "12345678000199"<br>- Telefone: "11988887777"<br>- Endereço: "Rua das Flores", "123", complemento "Loja 1" |
| **Passos de Execução** | 1. Menu → cadastro de farmácia.<br>2. Preencher todos os campos.<br>3. Clicar em "Prox." |
| **Resultado Esperado** | A janela `HomeDaFarmacia` deve ficar visível. |

### Caso de Teste 02: Cadastro sem CNPJ (Classe Inválida)
Este teste valida a classe de "Campo obrigatório ausente (CNPJ)".

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-EQ-02 |
| **Responsável** | Sandro Henrique |
| **Nome do Cenário** | Cadastro de farmácia sem CNPJ |
| **Origem no Código** | `frontend.CadastroFarmaciaE2ETest -> fluxoCadastroFarmacia_semCNPJ_deveExibirErro` |
| **Pré-condições** | Menu inicial aberto. |
| **Dados de Entrada** | - Email: "farmaciaerro@e2e.com"<br>- Senha: "senha123"<br>- Nome: "Farmácia Erro"<br>- CNPJ: *(vazio)*<br>- Demais campos preenchidos |
| **Passos de Execução** | 1. Preencher todos os campos **exceto** CNPJ.<br>2. Clicar em "Prox." |
| **Resultado Esperado** | `JOptionPane` com: "Precisa preencher todas as opções corretamente!". |

## 2. Técnica: Análise do Valor Limite (Boundary Value Analysis)
**Conceito:** Testar os extremos (limites) das variáveis, onde erros são mais frequentes (ex: 0, -1, valor máximo).

### Caso de Teste 03: Dose Zerada (Limite Inferior Válido)

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-AVL-01 |
| **Responsável** | Alexandre Colmenero |
| **Nome do Cenário** | Definição de dose zero (limite inferior válido) |
| **Origem no Código** | `backend.usuario.UsoTest -> setDose_zero_deveSerAceita` |
| **Pré-condições** | Instância da classe `Uso`. |
| **Dados de Entrada** | - Dose: 0 |
| **Passos de Execução** | 1. Definir a dose como 0. |
| **Resultado Esperado** | Valor aceito sem exceção. |

### Caso de Teste 04: Validação de Dose Negativa (Limite Inválido)

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-AVL-02 |
| **Responsável** | Alexandre Colmenero |
| **Nome do Cenário** | Definição de dose negativa |
| **Origem no Código** | `backend.usuario.UsoTest -> setDose_negativa_deveLancarExcecao` |
| **Pré-condições** | Instância da classe `Uso`. |
| **Dados de Entrada** | - Dose: -1 |
| **Passos de Execução** | 1. Tentar definir dose -1. |
| **Resultado Esperado** | `IllegalArgumentException`: "Não é possível setar número negativo para dose.". |

## 3. Técnica: Grafo Causa-Efeito (Lógica Booleana)
**Conceito:** Verificar combinações lógicas de entradas (Causas) que levam a um resultado (Efeito).

### Caso de Teste 05: Login com Credenciais Inválidas (Combinação Falsa)
**Regra:** Login = (Email Existe **E** Senha Correta). Testa email válido **E** senha incorreta → falha.

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-CE-01 |
| **Responsável** | Gabriel Soares |
| **Nome do Cenário** | Falha de login com credenciais inválidas |
| **Origem no Código** | `frontend.EntrarPessoaSegurancaTest -> loginComCredenciaisInvalidas_deveNegarAcessoEMostrarErro` |
| **Pré-condições** | Menu inicial → tela `EntrarPessoa`. |
| **Dados de Entrada** | - Email: "invalido@teste.com"<br>- Senha: "senhaErrada" |
| **Passos de Execução** | 1. Preencher email e senha.<br>2. Clicar em "Prox." |
| **Resultado Esperado** | Diálogo "Erro, email ou senha incorretos!"; usuário permanece na tela de login. |

### Caso de Teste 06: Carregar médico na agenda (Combinação Verdadeira — unitário)
**Regra:** Contato médico = (Email Existe **E** Senha Correta **E** tipo `"medico"`) → médico na agenda.

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-CE-02 |
| **Responsável** | Leonardo Carvalho |
| **Tipo** | Teste **unitário** (não E2E) — classe `Agenda` |
| **Nome do Cenário** | `stringToAgenda` com credenciais de médico válidas |
| **Origem no Código** | `backend.AgendaTest -> testStringToAgenda` |
| **Pré-condições** | Mock de `Medico.resgatarMedicoArquivo` retornando médico de teste. |
| **Dados de Entrada** | - Email: "medicoandre@gmail.com"<br>- Senha: "321"<br>- Tipo: `"medico"` |
| **Passos de Execução** | 1. Chamar `Agenda.stringToAgenda(..., "medico", ...)`. |
| **Resultado Esperado** | Agenda contém o `Medico` resgatado. |

### Caso de Teste 07: Cadastro seguido de Login (Combinação Verdadeira — E2E)

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-CE-03 |
| **Responsável** | Mateus Magalhães |
| **Nome do Cenário** | Cadastro de pessoa seguido de login com credenciais recém-criadas |
| **Origem no Código** | `frontend.FluxoCadastroLoginE2ETest -> cadastroSeguidoDeLogin_deveAutenticarComCredenciaisCriadas` |
| **Teste manual** | [TM-05](../testes_manuais/TM-05-cadastro-de-usu-rio-como-pesso.md) |
| **Pré-condições** | `RegistroUsuarios.txt` vazio. Menu inicial. |
| **Dados de Entrada** | Nome "Fluxo E2E", CPF "55566677788", email "fluxo.e2e@test.com", senha "senhaFluxo123", telefone "21911112222", endereço "Rua Fluxo"/"100"/"Casa" |
| **Passos de Execução** | Cadastro → persistência → novo login → abrir `Home`. |
| **Resultado Esperado** | Usuário salvo; `Home` exibe nome, CPF e botão "Meus remédios". |

---

## 4. Testes E2E adicionais (rastreabilidade)

| Classe de teste | Responsável | Cenário |
|-----------------|-------------|---------|
| `InicioE2ETest` | Gabriel Soares | Login pessoa com credenciais válidas ([TM-02](../testes_manuais/TM-02-autentica-o-de-usu-rio-pessoa-.md)) |
| `CadastroMedicamentoE2ETest` | Alexandre Colmenero | Cadastro de medicamento após login ([TM-04](../testes_manuais/TM-04-cadastro-de-medicamentos.md)) |
| `AgendaContatoMedicoE2ETest` | Leonardo Carvalho | Adicionar contato médico ([TM-03](../testes_manuais/TM-03-adicionar-contato-m-dico.md)) |
| `CadastroPessoaE2ETest` | Mateus Magalhães | Cadastro completo de pessoa |
| `CadastroPessoaValidacaoE2ETest` | Mateus Magalhães | Validação de campos obrigatórios no cadastro |
| `EntrarFarmaciaE2ETest` | Sandro Henrique | Login de farmácia (válido e inválido) |

---

## 5. Testes de requisitos não funcionais

| Classe | RNF | Responsável | Origem |
|--------|-----|-------------|--------|
| `EstresseMultiUsuarioTest` | Estresse (múltiplos usuários concorrentes) | Gabriel Soares | `src/test/java/backend/EstresseMultiUsuarioTest.java` |
| `EntrarPessoaSegurancaTest` | Segurança (CT-CE-01) | Gabriel Soares | Caso 05 acima |
| `InicioUsabilidadeTest` | Usabilidade (opções visíveis no menu) | Gabriel Soares | `src/test/java/frontend/InicioUsabilidadeTest.java` |
