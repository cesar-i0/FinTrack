package br.org.irede.fintrack.utils;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TratamentoDeDados {
    Scanner s = new Scanner(System.in);
    String texto;
    Formatador f = new Formatador();
    public String leituraString(){
        while (true){
            texto = s.nextLine();
            if(texto != null && !texto.trim().isEmpty()){
                return texto;
            }
            System.out.println("Erro: sua inserção não pode estar vazia! Tente novamente.");
        }
    }

    public LocalDate leituraData(){
        LocalDate data;
        while (true){
            texto = leituraString();
            data = f.conversorData(texto);
            if(data != null){
                return data;
            }
        }
    }

    public double leituraDouble(){
        double valor;
        while (true){
            try{
                valor = s.nextDouble();
                s.nextLine();
                return valor;
            }catch (InputMismatchException e){
                System.out.println("Erro: sua inserção não pertence ao tipo pedido! Tente novamente.");
                s.nextLine();
            }
        }
    }

    public char leituraChar(){
        while (true){
            try {
                texto = leituraString();
                if(texto.charAt(0) == 'S' || texto.charAt(0) == 'N'){
                    return texto.charAt(0);
                }
                System.out.println("Erro: sua inserção não é válida! Tente novamente.");
            }catch (StringIndexOutOfBoundsException e){
                System.out.println("Erro: sua inserção não pertence ao tipo pedido! Tente novamente.");
            }
        }
    }

    public int leituraInt(){
        while (true){
            try{
                int aux = s.nextInt();
                s.nextLine();
                return aux;
            }catch (InputMismatchException e){
                System.out.println("Erro: sua inserção é inválida! Tente novamente.");
                s.nextLine();
            }
        }
    }

    public String tipoTransacao(){
        System.out.println("""
        ==============================================
        Selecione o tipo de transação mensal:
        1  - Salário
        2  - Aluguel
        3  - Energia elétrica
        4  - Água e esgoto
        5  - Internet e telefonia
        6  - Assinatura de software (SaaS)
        7  - Plano de saúde
        8  - Vale-transporte / vale-alimentação
        9  - Contabilidade
        10 - Taxas bancárias
        11 - Empréstimo
        12 - Seguro (predial, empresarial, equipamentos)
        13 - Marketing
        14 - Impostos (ISS, ICMS, Simples Nacional, etc.)
        15 - Compra programada
        16 - Outros
        ==============================================""");

        while (true){
            int op = leituraInt();
            switch (op){
                case 1:
                    return "Salário";
                case 2:
                    return "Aluguel";
                case 3:
                    return "Energia elétrica";
                case 4:
                    return "Água e esgoto";
                case 5:
                    return "Internet e telefonia";
                case 6:
                    return "Assinatura de software";
                case 7:
                    return "Plano de saúde";
                case 8:
                    return "Vales";
                case 9:
                    return "Contabilidade";
                case 10:
                    return "Taxas bancárias";
                case 11:
                    return  "Empréstimo";
                case 12:
                    return "Seguro";
                case 13:
                    return "Marketing";
                case 14:
                    return "Impostos";
                case 15:
                    return "Compra programada";
                case 16:
                    return "Outros";
                default:
                    System.out.println("Selecione uma opção válida");
                    break;
            }

        }
    }

}
