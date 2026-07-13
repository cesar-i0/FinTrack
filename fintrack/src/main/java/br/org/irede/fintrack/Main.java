package br.org.irede.fintrack;
import br.org.irede.fintrack.controller.FinTrack;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.utils.TratamentoDeDados;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

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
            Scanner scanner = new Scanner(System.in);
            try{
                opcao = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Erro: você inseriu uma entrada inválida!");
            }
            scanner.nextLine();
            switch (opcao){
                case 1:
                    System.out.println("Digite a descrição: ");
                    String descricao = trata.leituraString();
                    System.out.println("Digite o valor: ");
                    double valor = trata.leituraDouble();
                    System.out.println("Digite a data: ");
                    LocalDate data = trata.leituraData();
                    System.out.println("Digite 'S' se for uma receita, caso contrário 'N': ");
                    char receita = trata.leituraChar();
                    Transacao nova = new Transacao(descricao,valor,data,(receita == 'S'));
                    gerenciador.adcionar(nova);
                    break;
                case 2:
                    gerenciador.listar();
                    break;
                case 3:
                    System.out.println("O saldo é: " + gerenciador.calcularSaldo());
                    break;
                case 4:
                    try{
                        System.out.println("Digite o número da transação que deseja apagar: ");
                        index = scanner.nextInt();
                        gerenciador.remover(index);
                    }catch (InputMismatchException e){
                        System.out.println("Erro: você inseriu uma entrada inválida!");
                    }
                    break;
                case 5:
                    try{
                        System.out.println("Digite o número da transação que deseja encontrar: ");
                        index = scanner.nextInt();
                        Transacao a = gerenciador.buscar(index);
                        System.out.println(index + " : " + a.getDescricao() + " | " + a.getValor() + " | " + (a.getReceita() ? "Receita" : "Despesa"));
                    }catch (InputMismatchException e){
                        System.out.println("Erro: você inseriu uma entrada inválida!");
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
