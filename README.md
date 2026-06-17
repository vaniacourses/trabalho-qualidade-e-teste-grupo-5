# MedAlerta - Qualidade & Teste 26.1

[![Repositório](https://img.shields.io/badge/GitHub-Repositório-blue?logo=github)](https://github.com/vaniacourses/trabalho-qualidade-e-teste-grupo-5)

O projeto **MedAlerta** está sendo aprimorado e reestruturado pela nossa equipe no contexto da disciplina de Qualidade e Teste de Software. Nosso objetivo principal é analisar este sistema legado, realizar refatorações no código-fonte, mapear possíveis dívidas técnicas e aplicar rigorosamente boas práticas de Quality Assurance (QA). Com isso, buscamos elevar o nível de confiabilidade, otimizar o desempenho e facilitar a manutenção contínua da aplicação.

## • Nossos Objetivos

Nossa meta vai além da simples execução de testes; buscamos evoluir a qualidade geral do software por meio de:

- **Plano de Testes Completo**: Garantir uma ampla cobertura envolvendo testes unitários, de integração e ponta a ponta (E2E).
- **Automação de Testes**: Implementar frameworks modernos para assegurar que regressões sejam identificadas rapidamente.
- **Análise Estática e Dinâmica**: Utilizar ferramentas como SonarQube e JaCoCo para o monitoramento contínuo do código.
- **Testes de Mutação**: Avaliar a real eficácia das nossas suítes de teste através do PIT (Pitest).

Durante todo o ciclo de desenvolvimento, formulamos um planejamento de testes detalhado, elaboramos e executamos casos de teste de forma manual e automatizada, e validamos os componentes essenciais do sistema usando variadas abordagens de teste. Nosso intuito é entender a fundo a lógica do sistema, registrar os resultados obtidos e sugerir melhorias que resultem em uma versão muito mais estável do MedAlerta.

## • Tecnologias e Ferramentas Utilizadas

A aplicação foi migrada para o **Java 17** e adota o **Maven** como gerenciador de dependências. As ferramentas de qualidade configuradas no projeto incluem:

- **JUnit 4 & 5**: A base para a criação e execução dos testes unitários.
- **Mockito**: Essencial para a geração de mocks e stubs, garantindo o isolamento durante os testes.
- **AssertJ Swing**: Utilizado para a automação de testes de interface gráfica (GUI) e validação de sistema.
- **JaCoCo**: Responsável por medir e gerar relatórios sobre a cobertura de código.
- **PIT (Pitest)**: Ferramenta focada em testes de mutação para atestar a qualidade dos testes unitários.
- **SonarQube**: Plataforma adotada para a inspeção contínua e análise da saúde do código-fonte.

## • Arquitetura do Projeto

A estrutura de diretórios foi organizada seguindo as convenções do Maven, dividindo claramente as regras de negócio, a interface de usuário e as diferentes camadas de teste:

```text
src
├── main
│   └── java
│       ├── backend         # Regras de negócio (Entidades, Controladores, etc.)
│       │   ├── farmacia    # Lógica voltada para farmácias e controle de estoque
│       │   ├── gerenciamento # Gestão de agendamentos e envio de notificações
│       │   └── usuario     # Modelagem de usuários (Pessoa Física e Médicos)
│       └── frontend        # Interface Gráfica desenvolvida com Swing
└── test
    └── java
        ├── backend         # Testes Unitários (focados no isolamento da lógica)
        ├── backend/integracao # Testes de Integração (validam a comunicação entre os módulos)
        └── frontend        # Testes de Sistema/GUI (usando AssertJ Swing)
```

## • Instruções de Execução e Teste

### Pré-requisitos Necessários

- Java JDK 17
- Maven instalado e devidamente configurado nas variáveis de ambiente
- (Opcional) Instância do SonarQube em execução local na porta 9000

### Como Executar a Aplicação

Para compilar e iniciar o sistema manualmente, utilize os seguintes comandos no terminal:

1. **Compilar o projeto:**
   ```bash
   mvn clean compile
   ```

2. **Iniciar a aplicação:**
   ```bash
   java -cp target/classes inicio.MedAlerta
   ```

### Comandos Essenciais do Maven

Abaixo listamos os comandos principais configurados no arquivo `pom.xml` para gerenciar o ciclo de vida dos testes:

| Comando | Descrição |
|---------|-----------|
| `mvn clean test` | Limpa os artefatos de builds anteriores e roda os Testes Unitários (Surefire). |
| `mvn clean verify` | Executa o fluxo completo: testes unitários, testes de integração e geração de relatórios (JaCoCo/PIT). |
| `mvn clean install` | Realiza a compilação, execução dos testes e empacotamento, salvando o `.jar` no repositório local. |
| `mvn clean verify sonar:sonar` | Transmite as métricas de qualidade e cobertura para o servidor SonarQube (exige servidor ativo). |
| `mvn org.pitest:pitest-maven:mutationCoverage` | Roda os Testes de Mutação para avaliar a força e robustez da suíte de testes existente. |

### Entendendo os Níveis de Teste

**Testes Unitários** (`src/test/java/backend`):
- Focam na validação de classes de maneira isolada (ex: Agenda, Pessoa, Lógica de Horários).
- Fazem uso do Mockito para abstrair dependências externas, como acesso a arquivos ou banco de dados.

**Testes de Integração** (`src/test/java/backend/integracao`):
- Garantem que a comunicação entre os componentes reais funcione corretamente (ex: Persistência de Pessoa em arquivos, integração entre Farmácia e Estoque).
- São executados durante a fase de `verify` ou `integration-test` do Maven.

**Testes de Sistema/GUI** (`src/test/java/frontend`):
- Empregam o AssertJ Swing para emular interações reais do usuário, como cliques e preenchimento de formulários na interface.
- Validam cenários de uso completos (ex: Fluxo de Cadastro -> Login -> Inserção de Medicamento).

## • Integrantes

- **Alexandre Colmenero** ([@alexandrelimaxs](https://github.com/alexandrelimaxs))
- **Gabriel Soares** ([@Gabriel-HS](https://github.com/Gabriel-HS))
- **Leonardo Carvalho** ([@leonardocfonseca](https://github.com/leonardocfonseca))
- **Mateus Magalhães** ([@MGLHS](https://github.com/MGLHS))
- **Sandro Henrique** ([@sandrohmt](https://github.com/sandrohmt))

## • Entregas

### Primeira Entrega

- [**Template Base do Plano de Testes**](./docs/PLANO_DE_TESTE.md)
- [**Testes Manuais Realizados**](./docs/testes_manuais/)
- [**Especificação do Trabalho**](./docs/TRABALHO.md)
- [**Plano de Testes**](./docs/PLANO_DE_TESTE.md) ou (https://docs.google.com/document/d/1bwteCF50jB--BLISjtX2zsVKDJhSQqzDmmCRV9LKiVg/edit?tab=t.0)
- [**Testes Unitários Realizados**](./src/test/java/backend)
- [**Código Fonte Original**](https://github.com/valescamoura/MedAlertaV2)
- [**Apresentação**](./docs/Apresentação.pptx) ou (https://www.canva.com/design/DAHIAdlNSxg/wzmCGUJDJfVMAX8ZBiv2qg/edit)

#### Segunda Entrega

- [**Divisão de classes por integrante (Entrega 2)**](./docs/DIVISAO_CLASSES_ENTREGA2.md)
- [**Atributos Prioritários da ISO/IEC 25010 (Entrega 2)**](./docs/ATRIBUTOS_DE_QUALIDADE_PRIORITARIOS_ISO_25010.md)
- [**Testes Funcionais (Entrega 2)**](./docs/TESTES_FUNCIONAIS.md)
- **Alexandre Colmenero** — classe `Uso` (`backend.usuario`): testes estruturais (JaCoCo ≥ 80% branches) e mutação (PIT ≥ 80%) em `UsoTest` + teste E2E `CadastroMedicamentoE2ETest`
- **Gabriel Soares** — classe `Data` (`backend.gerenciamento`): testes estruturais (JaCoCo ≥ 80% branches) e mutação (PIT ≥ 80%) em `DataTest`
- **Sandro Teixeira** — classe `FuncoesArquivos` (`backend`): testes estruturais (JaCoCo ≥ 80% branches) e mutação (PIT ≥ 80%) em `FuncoesArquivosTest` + teste E2E `CadastroFarmaciaE2ETest`
- [**Apresentação**]()


## © Créditos e Histórico

A concepção original do **MedAlerta** ocorreu no semestre letivo de 2020/1, servindo como projeto final para a matéria de Programação Orientada a Objetos. Os responsáveis por idealizar e construir essa fundação foram:

- Leonardo Saracino
- Lucas Martello
- Valesca Moura

Deixamos aqui o nosso sincero agradecimento aos autores originais pela excelente base construída. Com esse ponto de partida, a nossa equipe abraça o sistema no escopo da disciplina de Qualidade e Teste de Software. Nossa missão é refinar o projeto, assegurando maior estabilidade, resistência a falhas e confiança, aplicando um arsenal completo de técnicas de garantia da qualidade e metodologias de teste.
