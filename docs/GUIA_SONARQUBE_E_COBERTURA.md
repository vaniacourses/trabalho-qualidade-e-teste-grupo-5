# Guia: SonarQube (Docker), JaCoCo e PITest

Este documento descreve como subir o SonarQube localmente com Docker, enviar a análise do projeto MedAlerta e consultar os relatórios de cobertura (JaCoCo) e mutação (PITest).

## Pré-requisitos

- **Java JDK 17**
- **Maven** instalado e configurado no `PATH`
- **Docker** instalado e em execução (Docker Desktop no macOS/Windows, ou Docker Engine no Linux)

No macOS com Homebrew, confirme o Java 17 antes de rodar o Maven:

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17
java -version
mvn -version
```

---

## 1. Subir o SonarQube com Docker

### 1.1. Iniciar o container

A imagem **Community Edition** é suficiente para análise local do repositório:

```bash
docker run -d \
  --name sonarqube \
  -p 9000:9000 \
  sonarqube:community
```

Aguarde o servidor ficar pronto (a primeira subida pode levar alguns minutos):

```bash
docker logs -f sonarqube
```

Quando aparecer uma linagem semelhante a `SonarQube is operational`, pressione `Ctrl+C` para sair dos logs.

### 1.2. Acessar a interface web

Abra no navegador:

**http://localhost:9000**

Credenciais padrão na primeira vez:

| Campo    | Valor   |
|----------|---------|
| Usuário  | `admin` |
| Senha    | `admin` |

O SonarQube exige a troca da senha no primeiro login.

### 1.3. Criar o projeto e gerar token

1. Em **Projects → Create project**, escolha **Manually**.
2. Defina:
   - **Project display name:** `MedAlerta`
   - **Project key:** `medalerta` (deve coincidir com `-Dsonar.projectKey` no Maven)
3. Em **Provide a token**, clique em **Generate** e copie o token gerado.
4. Guarde o token em local seguro — ele **não** deve ser commitado no repositório.

> **Importante:** nunca compartilhe tokens reais em issues, PRs ou documentação. Use variáveis de ambiente ou parâmetros locais.

### 1.4. Parar e remover o container (opcional)

```bash
docker stop sonarqube
docker rm sonarqube
```

Para reutilizar o mesmo nome na próxima execução, basta rodar o `docker run` novamente (sem volume, os dados do SonarQube são perdidos ao remover o container).

---

## 2. Enviar análise para o SonarQube

Na raiz do repositório, com o SonarQube rodando em `localhost:9000`:

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17   # ajuste conforme sua instalação

mvn clean test jacoco:report sonar:sonar \
  -Dmaven.test.failure.ignore=true \
  -Dsonar.projectKey=medalerta \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<SEU_TOKEN_AQUI> \
  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

Substitua `<SEU_TOKEN_AQUI>` pelo token gerado na seção 1.3 (formato `sqp_...`).

### Alternativa com variável de ambiente (recomendado)

Evita expor o token no histórico do terminal:

```bash
export SONAR_TOKEN=<SEU_TOKEN_AQUI>

mvn clean test jacoco:report sonar:sonar \
  -Dmaven.test.failure.ignore=true \
  -Dsonar.projectKey=medalerta \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login="${SONAR_TOKEN}" \
  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

### O que cada parâmetro faz

| Parâmetro | Função |
|-----------|--------|
| `clean test` | Compila, executa os testes e prepara dados para cobertura |
| `jacoco:report` | Gera o relatório HTML e o XML do JaCoCo |
| `sonar:sonar` | Envia código, issues e cobertura ao SonarQube |
| `-Dmaven.test.failure.ignore=true` | Continua a análise mesmo se algum teste falhar (útil durante o desenvolvimento) |
| `-Dsonar.projectKey=medalerta` | Identificador do projeto no SonarQube |
| `-Dsonar.host.url=...` | URL do servidor local |
| `-Dsonar.login=...` | Token de autenticação (não use senha de usuário) |
| `-Dsonar.coverage.jacoco.xmlReportPaths=...` | Informa ao Sonar onde está o XML de cobertura |

Após o build, abra **http://localhost:9000** → projeto **MedAlerta** para ver bugs, code smells, duplicação e cobertura integrada.

---

## 3. Relatório de cobertura — JaCoCo

O plugin JaCoCo está configurado no `pom.xml` e gera relatório automaticamente na fase `test`.

### Gerar o relatório

```bash
mvn clean test
```

Se quiser ignorar falhas de teste durante a geração:

```bash
mvn clean test -Dmaven.test.failure.ignore=true
```

### Onde encontrar os arquivos

| Artefato | Caminho |
|----------|---------|
| Relatório HTML (principal) | `target/site/jacoco/index.html` |
| XML (usado pelo SonarQube) | `target/site/jacoco/jacoco.xml` |
| Dados brutos de execução | `target/jacoco.exec` |

### Como visualizar

**macOS:**

```bash
open target/site/jacoco/index.html
```

**Linux:**

```bash
xdg-open target/site/jacoco/index.html
```

**Windows (PowerShell):**

```powershell
Start-Process target/site/jacoco/index.html
```

Também é possível abrir o arquivo diretamente no navegador (arrastar o `index.html` ou usar `file://` com o caminho absoluto).

### Como ler o relatório

1. Na página inicial, clique no pacote (ex.: `backend.gerenciamento`).
2. Clique na classe desejada.
3. Linhas **verdes** = executadas nos testes; **vermelhas** = não cobertas; **amarelas** = parcialmente cobertas (branches).
4. Para a Entrega 2, a meta de **Branches ≥ 80%** na classe sob responsabilidade de cada integrante é verificada na coluna **Branched** (ou **Branches**) do relatório da classe.

---

## 4. Relatório de mutação — PITest

O PITest avalia a qualidade dos testes alterando o bytecode e verificando se a suíte detecta as mutações.

### Gerar o relatório

Com testes compilados:

```bash
mvn clean compile test-compile org.pitest:pitest-maven:mutationCoverage
```

Ou, seguindo o fluxo do `verify`:

```bash
mvn clean verify org.pitest:pitest-maven:mutationCoverage
```

> **Nota:** o `pom.xml` define `<targetClasses>` e `<targetTests>` para as classes em análise (ex.: `Uso`, `Data`). Cada integrante deve incluir sua classe nessas listas antes de rodar o PIT na classe sob sua responsabilidade.

### Onde encontrar os arquivos

O PITest cria um diretório com timestamp:

```text
target/pit-reports/
└── YYYYMMDDHHmm/          # pasta com data/hora da execução
    ├── index.html         # página principal
    └── ...                # relatórios por pacote/classe
```

### Como visualizar

**macOS:**

```bash
open target/pit-reports/*/index.html
```

**Linux:**

```bash
xdg-open target/pit-reports/*/index.html
```

Se houver mais de uma pasta em `pit-reports/`, abra a mais recente (maior nome de timestamp).

### Como ler o relatório

1. **Mutation coverage / Mutation score** — percentual de mutantes mortos pelos testes (meta do projeto: **≥ 80%**).
2. Clique na classe para ver cada mutante:
   - **KILLED** — teste detectou a mutação (desejável).
   - **SURVIVED** — mutação passou; indica lacuna nos testes.
   - **NO COVERAGE** — código não exercitado pelos testes.
3. Use os mutantes **SURVIVED** como guia para escrever ou reforçar casos de teste.

---

## 5. Fluxo resumido

```text
┌─────────────────┐     docker run      ┌──────────────────┐
│  Docker         │ ──────────────────► │ SonarQube :9000  │
└─────────────────┘                     └────────┬─────────┘
                                                 │
┌─────────────────┐   mvn test + sonar:sonar     │
│  Repositório    │ ─────────────────────────────►│ Dashboard web
│  MedAlerta      │                             │
└────────┬────────┘                             │
         │                                      │
         ├──► target/site/jacoco/index.html     (cobertura local)
         └──► target/pit-reports/.../index.html (mutação local)
```

| Objetivo | Comando principal | Onde ver |
|----------|-------------------|----------|
| Cobertura de linhas/branches | `mvn clean test` | `target/site/jacoco/index.html` |
| Testes de mutação | `mvn ... org.pitest:pitest-maven:mutationCoverage` | `target/pit-reports/*/index.html` |
| Qualidade + cobertura centralizada | `mvn clean test jacoco:report sonar:sonar ...` | http://localhost:9000 |

---

## 6. Problemas comuns

| Sintoma | Possível causa | O que fazer |
|---------|----------------|-------------|
| SonarQube não abre em `:9000` | Container ainda iniciando ou porta ocupada | `docker logs sonarqube`; verifique `docker ps` |
| `Connection refused` no `sonar:sonar` | SonarQube parado | `docker start sonarqube` ou suba o container novamente |
| Erro de autenticação | Token inválido ou expirado | Gere novo token em **My Account → Security** |
| Cobertura 0% no Sonar | XML do JaCoCo ausente | Rode `mvn test jacoco:report` antes do `sonar:sonar` |
| PIT não analisa minha classe | Classe fora de `targetClasses` | Atualize o `pitest-maven-plugin` no `pom.xml` |
| `vm.max_map_count` (Linux) | Limite do kernel | `sudo sysctl -w vm.max_map_count=262144` |

---

## Referências no repositório

- Configuração Maven: [`pom.xml`](../pom.xml)
- Divisão de classes e metas de cobertura: [`DIVISAO_CLASSES_ENTREGA2.md`](./DIVISAO_CLASSES_ENTREGA2.md)
- Comandos gerais de teste: [`README.md`](../README.md)
