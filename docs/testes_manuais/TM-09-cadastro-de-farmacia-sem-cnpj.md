# Teste Manual - Cadastro de Farmácia

## Informações do Teste
- **ID do Teste**: 9
- **Título**: Cadastro de nova farmácia sem preencher o CNPJ
- **Pré-condição**: Usuário iniciar a aplicação na tela de Início.
- **Dados de Entrada**: 
  - Email: farmacia@email.com
  - Senha: 123
  - CNPJ: (vazio)
  - Telefone: 123
  - Nome: farmacia
  - Endereço (Rua): Rua A
  - Número: 1
  - Complemento: N/A
- **Resultado Esperado**: O sistema deve exibir uma mensagem de aviso solicitando o preenchimento de todas as opções e não deve permitir a conclusão do cadastro.

## Resumo do Relatório
- **Casos Planejados**: 1
- **Casos Executados**: 1
- **Casos com Problemas**: 0
- **Eficiência**: 100%

## Etapas Realizadas

| PASSO | ETAPAS | RESULTADOS ESPERADOS | RESULTADOS REAIS | PASSOU/FALHOU |
| --- | --- | --- | --- | --- |
| 1 | Clicar no botão "Iniciar" correspondente a "Fazer cadastro como farmácia" na tela inicial. | Abrir a tela de cadastro de farmácia (LoginFarmacia). | A tela de cadastro foi aberta corretamente. | Passou |
| 2 | Preencher os campos: Email, Senha, Nome da farmácia, Telefone, Nome da rua, Número e Complemento (deixando o CNPJ vazio). | Campos preenchidos conforme os dados de teste. | Os campos foram preenchidos, mantendo o CNPJ em branco. | Passou |
| 3 | Clicar no botão "Prox.". | Exibir mensagem "Precisa preencher todas as opções corretamente!" e retornar à tela de cadastro. | A mensagem de aviso foi exibida e o cadastro não foi realizado. | Passou |
| 4 | Verificar se a farmácia foi salva no arquivo de registros. | Os dados da farmácia NÃO devem estar presentes no arquivo `backend\farmacia\RegistroFarmacias.txt`. | O arquivo permaneceu inalterado. | Passou |
