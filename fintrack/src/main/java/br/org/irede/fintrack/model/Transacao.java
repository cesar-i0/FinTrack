package br.org.irede.fintrack.model;

import java.time.LocalDate;

public class Transacao {
    private String descricao;
    private double valor;
    private LocalDate data;
    private boolean ehReceita;

    public Transacao(String descricao, double valor, LocalDate data, boolean ehReceita){
        this.descricao = descricao;
        this.valor = valor;
        this.data = this.data;
        this.ehReceita = ehReceita;
    }

    public void setValor(double valor){
        this.valor = valor;
    }

    public double getValor(){
        return valor;
    }

    public void setDataTransacao(LocalDate dataTransacao){
        this.data = data;
    }

    public LocalDate getData(){
        return data;
    }

    public void setReceita(boolean receita){
        this.ehReceita = receita;
    }

    public boolean getReceita(){
        return ehReceita;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

    @Override
    public String toString(){
        return (descricao + " | R$ " + valor + " | " + ( ehReceita ? "Receita" : "Despesa"));
    }
}
