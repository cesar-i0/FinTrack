package br.org.irede.fintrack.model;

public class Transacao {
    private String descricao;
    private double valor;
    private String dataTransacao;
    private boolean receita;

    public Transacao(String descricao, double valor, String dataTransacao, boolean receita){
        this.descricao = descricao;
        this.valor = valor;
        this.dataTransacao = dataTransacao;
        this.receita = receita;
    }

    public void setValor(double valor){
        this.valor = valor;
    }

    public double getValor(){
        return valor;
    }

    public void setDataTransacao(String dataTransacao){
        this.dataTransacao = dataTransacao;
    }

    public String getDataTransacao(){
        return dataTransacao;
    }

    public void setReceita(boolean receita){
        this.receita = receita;
    }

    public boolean getReceita(){
        return receita;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
