# Teste Manual - Cadastro de Farmácia

## Informações do Teste
- **ID do Teste**: 8
- **Título**: Cadastro de nova farmácia no sistema
- **Pré-condição**: Usuário iniciar a aplicação na tela de Início.
- **Dados de Entrada**: 
  - Email: farmacia@email.com
  - Senha: 123
  - CNPJ: 123
  - Nome: farmacia
  - Endereço (Rua): Rua A
  - Número: 1
  - Complemento: N/A
- **Resultado Esperado**: Farmácia cadastrada com sucesso e redirecionada para a tela inicial da farmácia (HomeDaFarmacia).

## Resumo do Relatório
- **Casos Planejados**: 1
- **Casos Executados**: 1
- **Casos com Problemas**: 0
- **Eficiência**: 100%

## Etapas Realizadas

| PASSO | ETAPAS | RESULTADOS ESPERADOS | RESULTADOS REAIS | PASSOU/FALHOU |
| --- | --- | --- | --- | --- |
| 1 | Clicar no botão "Iniciar" correspondente a "Fazer cadastro como farmácia" na tela inicial. | Abrir a tela de cadastro de farmácia (LoginFarmacia). | A tela de cadastro foi aberta com sucesso. | Passou |
| 2 | Preencher os campos: Email, Senha, Nome da farmácia, CNPJ, Telefone (não fornecido, usado "000"), Nome da rua, Número e Complemento. | Campos preenchidos corretamente. | Os campos foram preenchidos com os dados de teste. | Passou |
| 3 | Clicar no botão "Prox.". | O sistema deve salvar os dados e abrir a tela HomeDaFarmacia. | Os dados foram salvos no arquivo e a tela HomeDaFarmacia foi exibida. | Passou |
| 4 | Verificar a existência da farmácia no arquivo de registros. | Os dados da farmácia devem estar presentes no arquivo `backend\farmacia\RegistroFarmacias.txt`. | Os dados foram persistidos corretamente. | Passou |
