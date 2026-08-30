package br.org.irede.fintrack.model;

import java.time.LocalDate;

public class Transacao {
    private String descricao;
    private Double valor;
    private LocalDate data;
    private Boolean ehReceita;
    private Integer idTransacao;

    public Transacao() {}

    public Transacao(String descricao, Double valor, LocalDate data, Boolean ehReceita){
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.ehReceita = ehReceita;
    }

    public void setValor(Double valor){
        this.valor = valor;
    }

    public Double getValor(){
        return valor;
    }

    public void setDataTransacao(LocalDate data){
        this.data = data;
    }

    public LocalDate getData(){
        return data;
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

    @Override
    public String toString(){
        return (descricao + " | R$ " + valor + " | " + ( ehReceita ? "Receita" : "Despesa"));
    }
}
