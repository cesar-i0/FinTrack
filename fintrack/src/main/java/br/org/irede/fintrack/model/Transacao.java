package br.org.irede.fintrack.model;

import java.time.LocalDate;

public class Transacao {
    private Integer idTransacao;
    private String descricao;
    private Double valor;
    private LocalDate date;
    private Boolean ehReceita;
    private String categoria;

    public Transacao() {}

    public Transacao(String descricao, Double valor, LocalDate date, Boolean ehReceita, String categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.date = date;
        this.ehReceita = ehReceita;
        this.categoria = categoria;
    }

    public void setValor(Double valor){
        this.valor = valor;
    }

    public Double getValor(){
        return valor;
    }

    public void setDate(LocalDate data){
        this.date = data;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setReceita(Boolean ehReceita){
        this.ehReceita = ehReceita;
    }

    public Boolean getReceita(){
        return ehReceita;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setId(Integer id){
        this.idTransacao = id;
    }

    public Integer getId(){
        return idTransacao;
    }

    public String getTipo() {
        if (this.ehReceita == null) {
            return "";
        }
        return this.ehReceita ? "Receita" : "Despesa";
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getCategoria(){
        return categoria;
    }

    @Override
    public String toString(){
        return (descricao + " | R$ " + valor + " | " + ( ehReceita ? "Receita" : "Despesa"));
    }
}
