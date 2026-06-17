# Documentação de Testes Funcionais

## 1. Técnica: Particionamento em Classes de Equivalência
**Conceito:** Dividir os dados de entrada em grupos (Válidos e Inválidos). Se o teste passa para um valor do grupo, assume-se que passa para todos.

### Caso de Teste 01: Cadastro de Farmácia (Classe Válida)
Este teste valida a classe de "Dados Completos e Corretos".

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-EQ-01 |
| **Nome do Cenário** | Cadastro de Farmácia com Sucesso (Fluxo Feliz) |
| **Origem no Código** | `frontend.FarmaciaCadastroTest -> deveCadastrarFarmaciaComSucessoEAbirHome` |
| **Pré-condições** | O arquivo `RegistroFarmacias.txt` deve estar limpo ou inexistente. A aplicação deve estar na tela de Login de Farmácia. |
| **Dados de Entrada** | - Email: "nova.farmacia@teste.com"<br>- Senha: "senhaSegura123"<br>- Nome: "Farmacia Nova Vida"<br>- CNPJ: "12345678000100"<br>- Endereço: "Avenida Paulista", "1578" |
| **Passos de Execução** | 1. Preencher todos os campos de texto com os dados de entrada.<br>2. Clicar no botão "Prox." (`botaoCadastrar`). |
| **Resultado Esperado** | A janela atual deve fechar e a janela `HomeDaFarmacia` deve se tornar visível. |

### Caso de Teste 02: Validação de Campos Obrigatórios (Classe Inválida)
Este teste valida a classe de "Dados Faltantes ou Vazios".

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-EQ-02 |
| **Nome do Cenário** | Tentativa de cadastro com campos vazios |
| **Origem no Código** | `frontend.FarmaciaCadastroTest -> deveExibirErroComCamposObrigatoriosVazios` |
| **Pré-condições** | Aplicação na tela de Login de Farmácia. |
| **Dados de Entrada** | - Email: "incompleto@teste.com"<br>- Senha: (Vazio)<br>- Nome: (Vazio)<br>- CNPJ: (Vazio) |
| **Passos de Execução** | 1. Preencher apenas o campo Email.<br>2. Deixar os demais campos em branco.<br>3. Clicar no botão "Prox." (`botaoCadastrar`). |
| **Resultado Esperado** | O sistema deve exibir um `JOptionPane` com a mensagem de erro: "Precisa preencher todas as opções corretamente!". |

## 2. Técnica: Análise do Valor Limite (Boundary Value Analysis)
**Conceito:** Testar os extremos (limites) das variáveis, onde erros são mais frequentes (ex: 0, -1, valor máximo).

### Caso de Teste 03: Duração do Tratamento Zerada (Limite Inferior)
Este teste verifica o comportamento do sistema quando um contador atinge seu limite inferior (0).

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-AVL-01 |
| **Nome do Cenário** | Finalização de tratamento (Duração chega a 0) |
| **Origem no Código** | `backend.gerenciamento.GerenciadorTest -> naoAtualizaArquivoSeDuracaoZeroTest` |
| **Pré-condições** | Um objeto Uso (medicamento) deve existir. |
| **Dados de Entrada** | - Duração do Tratamento: 0 (Limite inferior) |
| **Passos de Execução** | 1. O Gerenciador executa a verificação de rotina (`atualizarDuracaoDeUso`).<br>2. O sistema verifica o valor da duração. |
| **Resultado Esperado** | O sistema NÃO deve decrementar o valor (para -1) e NÃO deve chamar a função de escrita no arquivo, pois o tratamento já acabou. |

### Caso de Teste 04: Validação de Dose Negativa (Limite Inválido)
Este teste verifica a rejeição de valores logo abaixo do limite permitido.

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-AVL-02 |
| **Nome do Cenário** | Definição de dose negativa |
| **Origem no Código** | `backend.usuario.UsoTest -> testSetDoseInvalida` |
| **Pré-condições** | Instância da classe Uso. |
| **Dados de Entrada** | - Dose: -1 (Valor imediatamente abaixo do limite 0) |
| **Passos de Execução** | 1. Tentar definir a dose do medicamento como -1. |
| **Resultado Esperado** | O sistema deve lançar uma `IllegalArgumentException` com a mensagem: "Não é possível setar número negativo para dose.". |

## 3. Técnica: Grafo Causa-Efeito (Lógica Booleana)
**Conceito:** Verificar combinações lógicas de entradas (Causas) que levam a um resultado (Efeito).
**Regra de Negócio:** Login = (Email Existe E Senha Correta).

### Caso de Teste 05: Login com Credenciais Inválidas (Combinação Falsa)
Testa a combinação: [Email Correto] E [Senha Incorreta] -> Efeito: Falha.

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-CE-01 |
| **Nome do Cenário** | Falha de Login com senha incorreta |
| **Origem no Código** | `frontend.PessoaEntrarTest -> deveExibirErroComCredenciaisInvalidas` |
| **Pré-condições** | Aplicação na tela `EntrarPessoa`. |
| **Dados de Entrada** | - Email: "Testedefalha@teste.com" (Formato válido)<br>- Senha: "senhaerrada" (Incorreta) |
| **Passos de Execução** | 1. Inserir email.<br>2. Inserir senha incorreta.<br>3. Clicar no botão "Prox." (`botaoEntrar`). |
| **Resultado Esperado** | O sistema deve exibir mensagem de erro: "Erro, email ou senha incorretos!". |

### Caso de Teste 06: Recuperação de Arquivo (Combinação Verdadeira)
Testa a combinação: [Email Correto] E [Senha Correta] -> Efeito: Sucesso (Objeto Retornado).

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-CE-02 |
| **Nome do Cenário** | Recuperação de Médico com credenciais válidas |
| **Origem no Código** | `backend.usuario.UsuarioMedicoTest -> testResgatarMedicoComSucesso` |
| **Pré-condições** | Arquivo `RegistroMedicos.txt` contendo a linha simulada: "Dr. Bernardo,21...,bernardo@hospital.com,senha123,Cirurgiao". |
| **Dados de Entrada** | - Email: "bernardo@hospital.com"<br>- Senha: "senha123" |
| **Passos de Execução** | 1. O método `resgatarMedicoArquivo` é chamado com os dados de entrada. |
| **Resultado Esperado** | O sistema deve retornar um objeto Medico não nulo, com o nome "Dr. Bernardo" e especialidade "Cirurgiao". |

### Caso de Teste 07: Cadastro seguido de Login (Combinação Verdadeira — E2E)
Testa a combinação: [Cadastro Completo e Válido] E [Email Existe no Arquivo] E [Senha Correta] → Efeito: Acesso autenticado na Home.
Alinhado ao teste manual [TM-05](../testes_manuais/TM-05-cadastro-de-usu-rio-como-pesso.md).

| Campo | Descrição |
| :--- | :--- |
| **ID do Caso de Teste** | CT-CE-03 |
| **Técnica** | Grafo Causa-Efeito (Lógica Booleana) |
| **Nome do Cenário** | Cadastro de pessoa seguido de login com as credenciais recém-criadas |
| **Origem no Código** | `frontend.FluxoCadastroLoginE2ETest -> cadastroSeguidoDeLogin_deveAutenticarComCredenciaisCriadas` |
| **Pré-condições** | O arquivo de usuários (`RegistroUsuarios.txt`) deve estar vazio ou inexistente. A aplicação deve estar no menu inicial. |
| **Dados de Entrada** | - Nome: "Fluxo E2E"<br>- CPF: "55566677788"<br>- Email: "fluxo.e2e@test.com"<br>- Senha: "senhaFluxo123"<br>- Telefone: "21911112222"<br>- Endereço: "Rua Fluxo", "100", "Casa" |
| **Passos de Execução** | 1. Abrir o menu inicial e navegar para a tela de cadastro de pessoa (`LoginPessoa`).<br>2. Preencher todos os campos com os dados de entrada.<br>3. Clicar no botão "Prox." e verificar que o usuário foi persistido no arquivo.<br>4. Reabrir o menu inicial e navegar para a tela de login (`EntrarPessoa`).<br>5. Preencher email e senha com as credenciais criadas no cadastro.<br>6. Clicar no botão "Prox." e aguardar o fechamento da tela de login.<br>7. Abrir a tela `Home` com o usuário autenticado e verificar os dados exibidos. |
| **Resultado Esperado** | O usuário deve ser salvo no arquivo após o cadastro. O login deve fechar a tela `EntrarPessoa`. A `Home` deve exibir o nome "Fluxo E2E", o CPF "55566677788" e o botão "Meus remédios". |