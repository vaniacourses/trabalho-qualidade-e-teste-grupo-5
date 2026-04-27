# 📌 Plano de Teste MedAlerta

> 📢 **Instruções Gerais**
>
> - Cada integrante deve preencher as seções sob sua responsabilidade.
> - Substitua os campos em branco com informações do projeto.
> - Apague estas instruções após o preenchimento, se desejar.
> - Use linguagem clara e objetiva.
> - Sempre que possível, seja específico (evite respostas genéricas).

---

## 📝 Registro de Mudanças

> 📢 **Instrução:** Registre qualquer alteração feita neste documento.

| Versão | Data | Autor    | Descrição |
|------|--------|----------|--------   |
| 1.0  | 18/04/2026 | Leonardo Carvalho | Criação do documento de acordo com o template |
| 1.1  | 25/05/2026 | Leonardo Carvalho | Preenchimento do documento com algumas informações dos testes realizados até o momento |

---

## 📖 1. Introdução

> 📢 **Instrução:** Descreva o objetivo deste plano de teste e o sistema que será testado.

O presente Plano de Teste tem como finalidade descrever as estratégias, processos e métodos que serão utilizados para assegurar a qualidade do sistema MedAlerta.

O principal objetivo é verificar se as funcionalidades implementadas atendem aos requisitos definidos, além de garantir uma experiência de uso confiável e adequada ao público alvo.

Para isso, serão adotadas abordagens de testes unitários e testes manuais, contemplando funcionalidades como cadastro de usuários, gerenciamento de medicamentos, geração de alertas, controle de estoque e etc. O processo de verificação e validação será conduzido de forma iterativa, possibilitando a identificação antecipada de falhas, a evolução contínua do sistema e entrega progressiva de qualidade ao longo do desenvolvimento.


---

## 🎯 2. Escopo


### ✅ 2.1 No Escopo

> 📢 **Instrução:** Liste as funcionalidades que SERÃO testadas.

| Nome do Módulo | Papéis Aplicáveis | Descrição |
| :--- | :---: | :--- |
| Login de Usuário | Cliente | O Cliente pode se autenticar no sistema, caso já tenha uma conta criada no sistema |
| Adicionar Contato Médico | Cliente | O Cliente pode adicionar várias contatos médicos em uma agenda no sistema |
| Cadastro de Cliente | Cliente | O Cliente pode realizar um cadastro no sistema preenchendo suas informações

**OBS: Caso tenha mais, por favor adicione!**


---

### ❌ 2.2 Fora do Escopo

> 📢 **Instrução:** Liste o que NÃO será testado.

Esses recursos não serão testados porque não estão incluídos nas especificações de requisitos do software

- Interfaces de hardware
- Interfaces de software
- Lógica de banco de dados
- Interfaces de comunicação
- Desempenho do sistema 

---

## 🧪 3. Objetivos de Qualidade

> 📢 **Instrução:** Descreva o que os testes pretendem garantir (ex: funcionamento correto, segurança, etc).

Os testes têm como objetivo garantir que as principais funcionalidades do sistema operem corretamente, atendendo aos requisitos definidos e proporcionando uma experiência confiável ao usuário.
Nos testes funcionais (manuais), busca-se validar operações como **autenticação de usuários**, **cadastro de clientes** e **gerenciamento de contatos médicos**.

Nos testes unitários, o foco é verificar o funcionamento correto das classes do sistema, incluindo o **gerenciamento de uso de medicamentos**, **cálculo de horários**, **envio de notificações** e **controle de quantidade disponível**.

Além disso, os testes visam garantir a integridade dos dados, o tratamento de entradas inválidas e a estabilidade do sistema durante sua execução.

**OFF: Caso esteja faltando informações de funcionalidades ou testes unitários já testados, favor incluir!**

---

## 👥 4. Papéis e Responsabilidades

> 📢 **Instrução:** Defina quem faz o quê no projeto.

| Papel | Responsável | Responsabilidades |
|------|-------------|------------------|
|      |             |                  |
|      |             |                  |
|      |             |                  |

---

## 🔬 5. Metodologia de Teste

### 🔄 5.1 Fases de Teste

> 📢 **Instrução:** Descreva como cada tipo de teste será realizado no projeto.

No projeto serão conduzidas as seguintes fases de teste:

- **Teste de Unidade:** verificação individual das classes do sistema, garantindo o correto funcionamento de métodos e regras de negócio.

- **Teste de Integração:** validação da interação entre diferentes componentes do sistema, como o gerenciamento de usuários e o controle de medicamentos.

- **Teste de Sistema (Manual):** execução de testes do ponto de vista do usuário, validando funcionalidades como login, cadastro e gerenciamento de contatos.

---

### ⛔ 5.2 Critérios de Suspensão e Retomada

> 📢 **Instrução:** Defina quando os testes devem parar e quando devem voltar.

A execução dos testes poderá ser suspensa caso sejam identificadas falhas críticas que impeçam o funcionamento das principais funcionalidades do sistema, ou quando uma alta taxa de falhas comprometer a continuidade dos testes.

Os testes serão retomados após a correção dos problemas identificados, garantindo que o sistema esteja novamente em condições adequadas para execução dos testes.

---

### ✅ 5.3 Critérios de Conclusão

> 📢 **Instrução:** Defina quando os testes serão considerados finalizados.

Os testes serão considerados concluídos quando:

- 100% dos casos de teste planejados forem executados
- Os resultados dos testes forem devidamente registrados
- As falhas identificadas forem documentadas
- As principais funcionalidades do sistema tiverem sido avaliadas

---

## 📅 6. Cronograma

> 📢 **Instrução:** Planeje as datas das atividades de teste.

| Atividade | Início | Fim | Responsável |
|----------|--------|-----|--------------|
|          |        |     |              |
|          |        |     |              |
|          |        |     |              |

---

## 📦 7. Entregáveis de Teste

> 📢 **Instrução:** Liste os artefatos gerados antes, durante e depois dos testes.

Os entregáveis de teste são fornecidos conforme abaixo

#### Antes da fase de teste

- Documento de planos de teste.
- Documentos de casos de teste
- Especificações de projeto de teste.

#### Durante o teste

- Simuladores de ferramentas de teste.
- Dados de teste.
- Matriz de rastreabilidade de testes
- Logs de erros e logs de execução.

#### Após o término dos ciclos de teste

- Resultados de teste / relatórios
- Relatório de erros
- Diretrizes  para instalação e para procedimentos de teste
- Notas de lançamento


---

## 🖥️ 8. Recursos e Ambiente

### 🧰 8.1 Ferramentas de Teste

> 📢 **Instrução:** Liste as ferramentas que serão utilizadas.

| No. | Recursos | Descrição |
| :--- | :---: | :--- |
| 1 | JUnit 5 | Framework utilizado para a criação e execução dos testes unitários do sistema |
| 2 | Mockito | Biblioteca utilizada para simular dependências e facilitar a execução de testes unitários |
| 3 | Eclipse | Visual Studio Code | Ambiente utilizado para desenvolvimento e execução dos testes |
| 4 | Computador | Pelo menos 4 computadores rodam Windows 7, Ram 2GB, CPU 3.4GHZ |
---

### 💻 8.2 Ambiente de Teste

> 📢 **Instrução:** Descreva o ambiente onde os testes serão executados.

Para a execução do sistema e realização dos testes, é necessário dispor de um ambiente compatível com as tecnologias utilizadas no desenvolvimento do projeto.

**Requisitos de Software:** 

- Sistema operacional Windows 8 ou superior, ou distribuições Linux/macOS com suporte a ambiente Java
- Java Development Kit (JDK) versão 8 ou superior
- Framework de testes JUnit 5
- Biblioteca Mockito
- Ambiente de desenvolvimento integrado (IDE), como Eclipse IDE ou IntelliJ IDEA
  

---

## ⚠️ 9. Riscos

> 📢 **Instrução:** Identifique possíveis problemas e como lidar com eles.

| Risco | Impacto | Mitigação |
|------|---------|----------|
|      |         |          |
|      |         |          |

---

## 📚 10. Termos e Acrônimos

> 📢 **Instrução:** Defina termos técnicos utilizados no documento.

| Termo | Significado |
|------|-------------|
|      |             |
|      |             |

---