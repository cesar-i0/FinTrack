# FinTrack

FinTrack é uma aplicação Java em linha de comando para controle financeiro. O sistema permite cadastrar transações, listar os lançamentos, consultar o saldo atual, remover itens e buscar transações por índice.

## Funcionalidades

- Adicionar novas transações com descrição, valor, data e tipo.
- Registrar receitas e despesas.
- Cadastrar transações mensais com uma categoria pré-definida.
- Listar todas as transações cadastradas.
- Exibir o saldo total acumulado.
- Remover uma transação pelo índice.
- Buscar uma transação pelo índice.

## Estrutura do projeto

- `src/main/java/br/org/irede/fintrack/Main.java`: ponto de entrada da aplicação.
- `src/main/java/br/org/irede/fintrack/controller/FinTrack.java`: gerenciamento da lista de transações.
- `src/main/java/br/org/irede/fintrack/model/Transacao.java`: modelo base de transação.
- `src/main/java/br/org/irede/fintrack/model/TransacaoMensal.java`: extensão para transações mensais.
- `src/main/java/br/org/irede/fintrack/utils/TratamentoDeDados.java`: leitura e validação de dados no console.
- `src/main/java/br/org/irede/fintrack/utils/Formatador.java`: conversão de datas.

## Uso da aplicação

Ao iniciar, o sistema exibe um menu com as opções:

1. Adicionar nova transação
2. Listar transações
3. Mostrar saldo atual
4. Remover transação
5. Buscar transação
6. Sair

### Cadastro de transação

Ao adicionar uma transação, informe:

- descrição
- valor numérico
- data no formato `dd/MM/yyyy`
- `S` para receita ou `N` para despesa
- `S` se for mensal ou `N` se não for mensal

Se a transação for mensal, o sistema solicita também uma categoria, como salário, aluguel, energia elétrica, água e esgoto, internet, assinatura de software, plano de saúde e outras opções disponíveis no menu.

## Observações

- Os índices usados para buscar e remover seguem a posição da transação na lista exibida.
- Valores de receita entram positivamente no saldo; despesas entram negativamente.
- A aplicação usa armazenamento em memória, então os dados são perdidos ao encerrar o programa.