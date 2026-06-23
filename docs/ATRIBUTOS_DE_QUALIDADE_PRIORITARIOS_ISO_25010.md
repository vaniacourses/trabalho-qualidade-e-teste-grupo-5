# Atributos Prioritários da ISO/IEC 25010 para o Sistema MedAlerta

Documento alinhado aos slides **ISO 25010** da [Apresentação Entrega 2](https://canva.link/aop9a2ac2q3hgo9).

## Escala adotada

**Escala: 1 a 5**, onde **5 = prioridade máxima** para o contexto do MedAlerta.

| Atributo (ISO/IEC 25010) | Nota | Slide |
|--------------------------|:----:|:-----:|
| Adequação funcional | **5** | 07 |
| Eficiência de desempenho | **3** | 07 |
| Compatibilidade | **5** | 07 |
| Manutenibilidade | **4** | 07 |
| Portabilidade | **4** | 08 |
| Confiabilidade | **5** | 08 |
| Usabilidade | **5** | 08 |
| Segurança | **5** | 08 |

---

## Slide 07 — Atributos funcionais e de qualidade do produto

### 1. Adequação funcional (Functional Suitability) — Nota 5

O sistema deve executar **todas as funções críticas do negócio**: gerenciar contatos e uso de medicamentos, calcular horários e doses, notificações.

**Medidas possíveis:** cobertura dos requisitos funcionais (RF01–RF08); taxa de sucesso dos testes E2E dos fluxos principais.

**Relação com o projeto:** testes funcionais e E2E em [`TESTES_FUNCIONAIS.md`](./TESTES_FUNCIONAIS.md) e suíte `src/test/java/frontend/`.

---

### 2. Eficiência de desempenho (Performance Efficiency) — Nota 3

O sistema **não lida com processamento complexo de dados ou alto volume de transações em tempo real**.

**Medidas possíveis:** capacidade de atender múltiplos usuários simultâneos sem falhas; tempo total sob carga concorrente.

**Relação com o projeto:** `EstresseMultiUsuarioTest` — simula 50 usuários autenticando e lendo dados ao mesmo tempo, verificando que nenhuma requisição falha e o sistema responde dentro do limite aceitável.

---

### 3. Compatibilidade (Compatibility) — Nota 5

O sistema deve ter a **mais alta capacidade de coexistência** (rodar em segundo plano sem impactar a performance de outros aplicativos).

**Medidas possíveis:** uso de recursos em execução simultânea com outros processos; estabilidade com notificações em background.

**Relação com o projeto:** notificações de medicação via componentes em `backend.gerenciamento` e `backend.dsdesktopnotify`.

---

### 4. Manutenibilidade (Maintainability) — Nota 4

O código deve ser de **fácil manutenção** para suportar a adição de novas funcionalidades e a integração com futuros aplicativos de saúde ou plataformas de notificação.

**Medidas possíveis:** cobertura JaCoCo (≥ 80% branches), mutation score PIT (≥ 80%), issues Sonar corrigidas.

**Relação com o projeto:** divisão por classe em [`DIVISAO_CLASSES_ENTREGA2.md`](./DIVISAO_CLASSES_ENTREGA2.md); evidências Sonar na apresentação Entrega 2.

---

## Slide 08 — Atributos de qualidade em uso e segurança

### 5. Portabilidade (Portability) — Nota 4

O aplicativo precisa ser capaz de **funcionar em qualquer sistema operacional** e em **diferentes tipos de dispositivos** (Desktop/Móvel).

**Medidas possíveis:** execução em Windows, Linux e macOS com Java 17; validação em ambientes distintos da equipe.

**Relação com o projeto:** aplicação Java Swing + Maven; ambiente documentado no [`PLANO_DE_TESTE.md`](./PLANO_DE_TESTE.md).

---

### 6. Confiabilidade (Reliability) — Nota 5

O sistema existe para **emitir alertas nos horários corretos**. Se ele falhar, travar ou deixar de emitir notificações, o usuário pode esquecer de tomar um medicamento importante.

**Medidas possíveis:** percentual de alertas emitidos corretamente; taxa de falhas em execução; disponibilidade.

**Metas de exemplo:** 99,9% dos alertas disparados corretamente; disponibilidade superior a 99%.

---

### 7. Usabilidade (Usability) — Nota 5

O público-alvo são **pessoas idosas**, que podem possuir dificuldades visuais, cognitivas ou pouca familiaridade com tecnologia. Mesmo que o sistema funcione perfeitamente, ele não será útil se os usuários tiverem dificuldade para cadastrar medicamentos ou interpretar os alertas.

**Medidas possíveis:** tempo para cadastrar medicamento; taxa de erros do usuário; conclusão de tarefas sem auxílio.

**Relação com o projeto:** `InicioUsabilidadeTest`; testes manuais de usabilidade em [`testes_manuais/`](./testes_manuais/).

---

### 8. Segurança (Security) — Nota 5

O sistema armazena **dados pessoais e informações relacionadas à saúde** dos usuários. Dados como medicamentos utilizados, endereço, telefone e e-mail são informações sensíveis e devem ser protegidas contra acesso indevido.

**Medidas possíveis:** áreas restritas exigem autenticação; bloqueio de acesso com credenciais inválidas; vulnerabilidades críticas ausentes (SonarQube).

**Relação com o projeto:** `EntrarPessoaSegurancaTest` (CT-CE-01 em [`TESTES_FUNCIONAIS.md`](./TESTES_FUNCIONAIS.md)).
