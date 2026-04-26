# Guia de Preenchimento - Testes Manuais

Este documento descreve como preencher o template de teste manual para garantir a padronização e qualidade dos relatórios de teste do projeto MedAlerta.

## Estrutura do Documento

### 1. Cabeçalho
Substitua `[NOME_DA_FUNCIONALIDADE]` pelo nome da área que está sendo testada (ex: Login, Cadastro de Medicamento, etc).

### 2. Informações do Teste
- **ID do Teste**: Número identificador único do caso de teste.
- **Título**: Descrição sucinta do objetivo do teste.
- **Pré-condição**: Estado necessário do sistema antes de iniciar o teste (ex: "Usuário logado", "Base de dados vazia").
- **Dados de Entrada**: Informações específicas digitadas ou selecionadas durante o teste (ex: CPF, Nome, Senha).
- **Resultado Esperado**: O que o sistema deve apresentar ou como deve se comportar após a conclusão de todas as etapas.

### 3. Resumo do Relatório
- **Casos Planejados**: Total de cenários de teste previstos para esta funcionalidade.
- **Casos Executados**: Quantos dos cenários planejados foram realmente testados.
- **Casos com Problemas**: Quantidade de cenários que resultaram em falha.
- **Eficiência**: Percentual de sucesso (Cálculo: `((Executados - Problemas) / Executados) * 100`).

### 4. Etapas Realizadas (Tabela)
| Coluna | Descrição |
| --- | --- |
| **PASSO** | Número sequencial da etapa. |
| **ETAPAS** | Descrição clara da ação do usuário (ex: "Clicar no botão X"). |
| **RESULTADOS ESPERADOS** | O que deve acontecer especificamente após esta ação. |
| **RESULTADOS REAIS** | O que realmente aconteceu no sistema durante a execução. |
| **PASSOU/FALHOU** | Marque "Passou" se o real for igual ao esperado, ou "Falhou" caso contrário. |

---
**Nota**: Se um teste falhar, as evidências e detalhes técnicos devem ser registrados como uma Issue no GitHub vinculada ao ID do teste correspondente.
