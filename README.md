# FinTrack

FinTrack é uma aplicação Java em modo console para controle financeiro. O projeto foi desenvolvido com foco nos conteúdos do módulo inicial: Programação Orientada a Objetos, estruturas de controle, listas com ArrayList, tratamento de exceções e organização por pacotes.

## Objetivo

Permitir o cadastro e a consulta de transações financeiras, separando receitas e despesas, com suporte a lançamentos mensais e cálculo do saldo atual.

## Requisitos atendidos

- Programação Orientada a Objetos, com classes de domínio e herança.
- Estruturas de controle, com menu interativo, repetição e seleção de opções.
- Listas, usando ArrayList para armazenar as transações em memória.
- Tratamento de exceções, com validação de entrada do usuário e tratamento de erros comuns.
- Organização do código em pacotes, com separação entre app, controller, model e utils.

## Estrutura do projeto

- `fintrack/src/main/java/br/org/irede/fintrack/app/Main.java`: ponto de entrada da aplicação.
- `fintrack/src/main/java/br/org/irede/fintrack/controller/FinTrack.java`: gerenciamento da lista de transações.
- `fintrack/src/main/java/br/org/irede/fintrack/model/Transacao.java`: modelo base de transação.
- `fintrack/src/main/java/br/org/irede/fintrack/model/TransacaoMensal.java`: extensão para transações mensais.
- `fintrack/src/main/java/br/org/irede/fintrack/utils/TratamentoDeDados.java`: leitura e validação de dados no console.
- `fintrack/src/main/java/br/org/irede/fintrack/utils/Formatador.java`: conversão de datas.

## Funcionalidades

- Adicionar novas transações com descrição, valor, data e tipo.
- Registrar receitas e despesas.
- Cadastrar transações mensais com categoria pré-definida.
- Listar todas as transações cadastradas.
- Exibir o saldo total acumulado.
- Remover uma transação pelo índice.
- Buscar uma transação pelo índice.

## Execução

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