package br.org.irede.fintrack.model;

public class TransacaoMensal extends Transacao{
    private String tipoDeTransacao;

    public TransacaoMensal(String descricao, double valor, String dataTransacao, boolean receita, String tipoDeTransacao){
        super(descricao,valor, dataTransacao, receita);
        this.tipoDeTransacao = tipoDeTransacao;
    }

    public void setTipoDeTransacao(String tipoDeTransacao){
        this.tipoDeTransacao = tipoDeTransacao;
    }
    public String getTipoDeTransacao(){
        return tipoDeTransacao;
    }
}
