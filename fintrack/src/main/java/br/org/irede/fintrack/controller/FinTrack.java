package br.org.irede.fintrack.controller;
import br.org.irede.fintrack.model.Transacao;
import java.util.ArrayList;

public class FinTrack {
    private ArrayList<Transacao> trasacoes = new ArrayList<Transacao>();

    public void adcionar(Transacao a){
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
            System.out.println("Erro: o índice informado ultrapassa o tamanho existente!");
        }
        return a;
    }

    public void listar(){
        int index = 0;
        for(Transacao a : trasacoes){
            System.out.println(index + " : " + a.getDescricao() + " | " + a.getValor() + " | " + (a.getReceita() ? "Receita" : "Despesa"));
        }
    }

    public double calcularSaldo(){
        double aux = 0;
        for(Transacao a : trasacoes){
            if(a.getReceita()){
                aux += a.getValor();
            }else{
                aux -= a.getValor();
            }
        }
        return aux;
    }
}
