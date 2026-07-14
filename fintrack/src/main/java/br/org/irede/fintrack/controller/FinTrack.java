package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.model.Transacao;
import java.util.ArrayList;

public class FinTrack {
    private ArrayList<Transacao> trasacoes = new ArrayList<Transacao>();

    public void adcionarTransacao(Transacao a){
        trasacoes.add(a);
    }

    public void remover(int index){
        try{
            trasacoes.remove(index);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Erro: o índice informado não existe!");
        }
    }

    public Transacao buscar(int index){
        Transacao a = null;
        try{
            a = trasacoes.get(index);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Erro: transação não encontrada!");
        }
        return a;
    }

    public void listarTransacoes(){
        int index = 0;
        for(Transacao a : trasacoes){
            System.out.println(index + " : " + a.toString());
            index++;
        }
    }

    public double calcularSaldoTotal(){
        double aux = 0;
        for(Transacao a : trasacoes){
            aux += a.getValor();
        }
        return aux;
    }

}
