# Teste Manual - Cadastro de usuário como Pessoa Física

## Informações do Teste
- **ID do Teste**: 7
- **Título**: Cadastro de usuário como Pessoa Física (sem senha)
- **Pré-condição**: Usuário iniciar a aplicação
- **Dados de Entrada**: Dados PessoaisEmail: teste@teste.comSenha: Nome Completo: Testador da SilvaCPF: 123.456.789-01Telefone: (21) 98765-4321EndereçoNome da rua: Rua TesteNúmero: 123Complemento: Teste
- **Resultado Esperado**: Não permitir o cadastro do usuário

## Resumo do Relatório
- **Casos Planejados**: 4
- **Casos Executados**: 4
- **Casos com Problemas**: 2
- **Eficiência**: 50%

## Etapas Realizadas

| PASSO | ETAPAS | RESULTADOS ESPERADOS | RESULTADOS REAIS | PASSOU/FALHOU |
| --- | --- | --- | --- | --- |
| Clicar no botão Iniciar (Fazer cadastro como pessoa) | Abrir o menu para criar o usuário | Menu para criar usuário aberto | Passou |  |
| Preencher todas as informações (menos a senha) | Informações preenchidas serem exibidas | Informações preenchidas foram exibidas | Passou |  |
| Clicar no botão Próx. | Mostrar um aviso para preencher todas as opções e voltar para o menu de criar usuário | Conta criada e tela com informações donovo usuário foi mostrada | Falhou |  |
| Conferir os dados cadastrados | Não haverá dados cadastrados para serem conferidos | Dados conferem com os que foram cadastrados | Falhou |  |
