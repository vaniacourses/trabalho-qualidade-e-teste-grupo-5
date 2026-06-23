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

- **JUnit 5**: Base para criação e execução dos testes unitários e de integração.
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
│       │   ├── farmacia
│       │   ├── gerenciamento
│       │   └── usuario
│       ├── frontend        # Interface Swing
│       └── inicio          # Ponto de entrada (MedAlerta)
└── test
    └── java
        ├── backend         # Testes unitários
        ├── backend/integracao
        └── frontend        # Testes E2E / GUI (AssertJ Swing)
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
| `mvn clean test` | Compila o projeto, executa todos os testes (unitários, integração e GUI) via Surefire e gera relatório JaCoCo em `target/site/jacoco/`. |
| `mvn clean verify` | Igual ao `test`, com fase `verify` do Maven (sem PIT automático). |
| `mvn clean install` | Compila, executa os testes e empacota o artefato no repositório local Maven. |
| `mvn org.pitest:pitest-maven:mutationCoverage` | Roda testes de mutação (PIT) nas classes configuradas no `pom.xml`. |
| `mvn clean test jacoco:report sonar:sonar` | Envia análise ao SonarQube local (requer servidor em `localhost:9000`, ex.: via Docker). |

### Entendendo os Níveis de Teste

**Testes Unitários** (`src/test/java/backend`):
- Focam na validação de classes de maneira isolada (ex: Agenda, Pessoa, Lógica de Horários).
- Fazem uso do Mockito para abstrair dependências externas, como acesso a arquivos ou banco de dados.

**Testes de Integração** (`src/test/java/backend/integracao`):
- Garantem que a comunicação entre os componentes reais funcione corretamente (ex: Persistência de Pessoa em arquivos, integração entre Farmácia e Estoque).
- Executados junto com os demais testes em `mvn clean test` (Surefire).

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

- [**Plano de Testes**](./docs/PLANO_DE_TESTE.md) ([Google Docs](https://docs.google.com/document/d/1bwteCF50jB--BLISjtX2zsVKDJhSQqzDmmCRV9LKiVg/edit?tab=t.0))
- [**Testes Manuais Realizados**](./docs/testes_manuais/)
- [**Especificação do Trabalho**](./docs/TRABALHO.md)
- [**Testes Unitários Realizados**](./src/test/java/backend)
- [**Código Fonte Original**](https://github.com/valescamoura/MedAlertaV2)
- [**Apresentação**](./docs/Apresentação.pptx) ([Canva](https://www.canva.com/design/DAHIAdlNSxg/wzmCGUJDJfVMAX8ZBiv2qg/edit))
- [**Issues registradas**](https://github.com/vaniacourses/trabalho-qualidade-e-teste-grupo-5/issues)
- [**TestLink**](http://vania.ic.uff.br/testlink/) — plano de teste **TEP: Teste em produção** (responsável: Gabriel)

### Segunda Entrega

- [**Divisão de classes por integrante (Entrega 2)**](./docs/DIVISAO_CLASSES_ENTREGA2.md)
- [**Atributos Prioritários da ISO/IEC 25010 (Entrega 2)**](./docs/ATRIBUTOS_DE_QUALIDADE_PRIORITARIOS_ISO_25010.md)
- [**Testes Funcionais (Entrega 2)**](./docs/TESTES_FUNCIONAIS.md)
- [**Uso de agentes de IA no projeto**](./docs/USO_DE_AGENTES_IA.md)
- [**Issues registradas**](https://github.com/vaniacourses/trabalho-qualidade-e-teste-grupo-5/issues)
- **Inspeção SonarQube** — responsabilidade **compartilhada** da equipe; evidências na [**Apresentação Entrega 2**](https://canva.link/aop9a2ac2q3hgo9)
- **Alexandre Colmenero** — classe `Uso` (`backend.usuario`): testes estruturais (JaCoCo ≥ 80% branches) e mutação (PIT ≥ 80%) em `UsoTest` + teste E2E `CadastroMedicamentoE2ETest`
- **Gabriel Soares** — classe `Data` (`backend.gerenciamento`): testes estruturais (JaCoCo ≥ 80% branches) e mutação (PIT ≥ 80%) em `DataTest` + teste E2E `InicioE2ETest`
- **Leonardo Carvalho** — classe `Agenda` (`backend`): testes estruturais (JaCoCo ≥ 80% branches) e mutação (PIT ≥ 80%) em `AgendaTest` + teste E2E `AgendaContatoMedicoE2ETest`
- **Mateus Magalhães** — classe `PessoaFisica` (`backend.usuario`): testes estruturais (JaCoCo ≥ 80% branches) e mutação (PIT ≥ 80%) em `PessoaFisicaTest` + teste funcional E2E `FluxoCadastroLoginE2ETest` (**CT-CE-03** — técnica *Grafo Causa-Efeito*)
- **Sandro Henrique** — classe `FuncoesArquivos` (`backend`): testes estruturais (JaCoCo ≥ 80% branches) e mutação (PIT ≥ 80%) em `FuncoesArquivosTest` + teste E2E `CadastroFarmaciaE2ETest`
- [**Apresentação Entrega 2**](https://canva.link/aop9a2ac2q3hgo9)

### Testes complementares (RNF e suporte)

| Teste | Tipo | Responsável |
|-------|------|-------------|
| `EntrarPessoaSegurancaTest` | RNF Segurança (CT-CE-01) | Gabriel Soares |
| `EstresseMultiUsuarioTest` | RNF Estresse (concorrência) | Gabriel Soares |
| `InicioUsabilidadeTest` | RNF Usabilidade | Gabriel Soares |
| `CadastroPessoaE2ETest` / `CadastroPessoaValidacaoE2ETest` | E2E cadastro pessoa | Mateus Magalhães |
| `EntrarFarmaciaE2ETest` | E2E login farmácia | Sandro Henrique |


## © Créditos e Histórico

A concepção original do **MedAlerta** ocorreu no semestre letivo de 2020/1, servindo como projeto final para a matéria de Programação Orientada a Objetos. Os responsáveis por idealizar e construir essa fundação foram:

- Leonardo Saracino
- Lucas Martello
- Valesca Moura

Deixamos aqui o nosso sincero agradecimento aos autores originais pela excelente base construída. Com esse ponto de partida, a nossa equipe abraça o sistema no escopo da disciplina de Qualidade e Teste de Software. Nossa missão é refinar o projeto, assegurando maior estabilidade, resistência a falhas e confiança, aplicando um arsenal completo de técnicas de garantia da qualidade e metodologias de teste.
