package br.org.irede.fintrack;
import br.org.irede.fintrack.controller.FinTrack;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.model.TransacaoMensal;
import br.org.irede.fintrack.utils.TratamentoDeDados;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        FinTrack gerenciador =  new FinTrack();
        TratamentoDeDados trata = new TratamentoDeDados();
        boolean condicao = true;
        int opcao = 0, index = 0;
        while(condicao){
            System.out.println("""
            ===== FINTRACK - SEU CONTROLE FINANCEIRO =====
            1. Adicionar nova transação
            2. Listar transações
            3. Mostrar saldo atual
            4. Remover transação
            5. Buscar transação
            6. Sair
            ==============================================""");
            opcao = trata.leituraInt();
            switch (opcao){
                case 1:
                    System.out.print("Digite a descrição: ");
                    String descricao = trata.leituraString();
                    System.out.print("Digite o valor: ");
                    double valor = trata.leituraDouble();
                    System.out.print("Digite a data: ");
                    LocalDate data = trata.leituraData();
                    System.out.print("Digite 'S' se for uma receita, caso contrário 'N': ");
                    char receita = trata.leituraChar();
                    System.out.print("Digite 'S' se for mensal, caso contrário 'N': ");
                    char mensal = trata.leituraChar();
                    Transacao nova;
                    if(mensal == 'S'){
                        String tipo = trata.tipoTransacao();
                        nova = new TransacaoMensal(descricao,(receita == 'S' ? valor : -valor),data,(receita == 'S'),tipo);
                    }else{
                        nova = new Transacao(descricao,(receita == 'S' ? valor : -valor),data,(receita == 'S'));
                    }
                    gerenciador.adcionarTransacao(nova);
                    break;
                case 2:
                    gerenciador.listarTransacoes();
                    break;
                case 3:
                    System.out.println("O saldo é: " + gerenciador.calcularSaldoTotal());
                    break;
                case 4:
                    gerenciador.listarTransacoes();
                    System.out.println("Digite o número da transação que deseja apagar: ");
                    index = trata.leituraInt();
                    gerenciador.remover(index);
                    break;
                case 5:
                    System.out.println("Digite o número da transação que deseja encontrar: ");
                    index = trata.leituraInt();
                    Transacao a = gerenciador.buscar(index);
                    if(a != null){
                        System.out.println(index + " : " + a.toString());
                    }
                    break;
                case 6:
                    System.out.println("Saindo...");
                    condicao = false;
                    break;
                default:
                    System.out.println("Opcao selecionada nao existe!");
                    break;
            }
        }
    }
}
