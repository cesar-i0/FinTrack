# FinTrack

Aplicação desktop de controle financeiro desenvolvida em JavaFX. O sistema permite cadastrar receitas e despesas, consultar transações, editar registros, remover lançamentos, calcular totais e trabalhar com transações mensais.

## Tecnologias

- Java 26
- Maven
- JavaFX 26.0.2
- SQLite 3.46.1.3
- JUnit 5.10.3

## Pré-requisitos

Para executar o projeto em outra máquina, instale:

1. JDK 26, com a variável `JAVA_HOME` configurada.
2. Maven 3.9 ou superior.
3. Git, caso o projeto seja clonado de um repositório.

Confirme as instalações com:

```bash
java -version
javac -version
mvn -version
```

O Maven baixa automaticamente as dependências descritas no `pom.xml`, incluindo JavaFX, SQLite e JUnit. Não é necessário instalar o JavaFX ou o driver SQLite manualmente.

## Baixar e preparar o projeto

Clone o projeto e entre no diretório que contém o `pom.xml`:

```bash
git clone <URL_DO_REPOSITORIO>
cd FinTrack/fintrack
```

Se os arquivos já estiverem disponíveis localmente:

```bash
cd FinTrack/fintrack
```

O comando Maven deve ser executado dentro de `fintrack`, e não na pasta raiz `FinTrack`, porque o `pom.xml` está dentro de `fintrack`.

## Executar a aplicação

Inicie a aplicação JavaFX com:

```bash
mvn javafx:run
```

Também é possível compilar o projeto sem iniciá-lo:

```bash
mvn compile
```

Na primeira execução, o método `Main.start()` cria automaticamente a tabela `transactions` no banco SQLite. O arquivo `fintrack.db` é criado no diretório de onde a aplicação for iniciada, normalmente `fintrack/`.

## Banco de dados da aplicação

A aplicação usa SQLite persistente por meio de [DataBaseConnection.java](fintrack/src/main/java/br/org/irede/fintrack/utils/DataBaseConnection.java):

```text
jdbc:sqlite:fintrack.db
```

O banco contém a tabela `transactions`, com os campos:

- `t_id`: identificador da transação;
- `description`: descrição;
- `t_value`: valor;
- `t_type`: `Receita` ou `Despesa`;
- `t_date`: data no formato `dd/MM/yyyy`;
- `category`: categoria;
- `end_date`: data final de uma transação mensal.

O arquivo `fintrack.db` contém os dados reais da aplicação. Para começar com um banco vazio, feche a aplicação e remova esse arquivo. Ele será recriado na próxima execução.

## Estrutura principal

- [Main.java](fintrack/src/main/java/br/org/irede/fintrack/app/Main.java): inicia a aplicação JavaFX e o banco.
- `fintrack/src/main/java/br/org/irede/fintrack/controller/`: controllers das telas.
- `fintrack/src/main/java/br/org/irede/fintrack/model/`: modelos `Transacao` e `TransacaoMensal`.
- [TransacaoDAO.java](fintrack/src/main/java/br/org/irede/fintrack/dao/TransacaoDAO.java): operações de inserção, consulta, atualização, remoção, totais e saldo.
- [RepositorioGenerico.java](fintrack/src/main/java/br/org/irede/fintrack/dao/RepositorioGenerico.java): contrato genérico para salvar, buscar por ID e remover entidades.
- `fintrack/src/main/resources/br/org/irede/fintrack/view/`: telas FXML.
- `fintrack/src/main/resources/br/org/irede/fintrack/styles/`: estilos CSS.

## Testes com JUnit

Execute todos os testes com:

```bash
mvn test
```

O resultado esperado é semelhante a:

```text
Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Para executar uma classe específica:

```bash
mvn -Dtest=TransacaoDAOTest test
```

Para executar um método específico:

```bash
mvn -Dtest=TransacaoDAOTest#deveCalcularSaldoComReceitasEDespesas test
```

Para visualizar mais detalhes, não use a opção `-q`. Os testes têm nomes numerados com `@DisplayName`, que também aparecem na árvore de testes do VS Code e nos relatórios do Surefire.

### Cobertura dos testes

- [TransacaoTest.java](fintrack/src/test/java/br/org/irede/fintrack/model/TransacaoTest.java): criação, getters, setters, tipos e transações mensais.
- [RepositorioGenericoTest.java](fintrack/src/test/java/br/org/irede/fintrack/dao/RepositorioGenericoTest.java): uso do `TransacaoDAO` pelo contrato genérico.
- [TransacaoDAOTest.java](fintrack/src/test/java/br/org/irede/fintrack/dao/TransacaoDAOTest.java): inserção, consulta, atualização, remoção, saldo, transações mensais e validações.
- [FormatadorTest.java](fintrack/src/test/java/br/org/irede/fintrack/utils/FormatadorTest.java): conversão de datas e valores.

Os testes do DAO usam um banco SQLite em memória:

```text
jdbc:sqlite::memory:
```

Cada teste cria sua própria conexão e tabela com `@BeforeEach`. A conexão é fechada com `@AfterEach`, portanto os testes não alteram o arquivo real `fintrack.db`.

As validações usam `Assertions.assertEquals()`, `assertNull()`, `assertFalse()`, `assertNotNull()` e `assertThrows()`.

## Solução de problemas

### Maven informa que não encontrou o POM

Entre no módulo correto:

```bash
cd FinTrack/fintrack
mvn test
```

### Erro de versão do Java

Verifique se o Maven está usando o JDK 26:

```bash
mvn -version
```

Se aparecer outra versão, configure `JAVA_HOME` para o JDK 26 e abra um novo terminal.

### Avisos do SQLite ou do Maven

Avisos sobre acesso nativo do SQLite ou mutação reflexiva do Maven não significam falha do teste. A execução foi bem-sucedida quando o relatório termina com `BUILD SUCCESS` e `Failures: 0`.

## Licença

Este projeto não define uma licença no repositório.