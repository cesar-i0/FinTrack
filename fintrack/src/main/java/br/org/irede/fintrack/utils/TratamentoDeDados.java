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
                return texto.charAt(0);
            }catch (StringIndexOutOfBoundsException e){
                System.out.println("Erro: sua inserção não pertence ao tipo pedido! Tente novamente.");
            }
        }
    }
}
