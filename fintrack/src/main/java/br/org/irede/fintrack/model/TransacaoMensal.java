package br.org.irede.fintrack.model;

import java.time.LocalDate;

public class TransacaoMensal extends Transacao{
    private String tipoDeTransacao;

    public TransacaoMensal(String descricao, double valor, LocalDate data, boolean ehReceita, String tipoDeTransacao){
        super(descricao,valor, data, ehReceita);
        this.tipoDeTransacao = tipoDeTransacao;
    }

    public void setTipoDeTransacao(String tipoDeTransacao){
        this.tipoDeTransacao = tipoDeTransacao;
    }
    public String getTipoDeTransacao(){
        return tipoDeTransacao;
    }
}
