package br.org.irede.fintrack.model;

import java.time.LocalDate;

public class TransacaoMensal extends Transacao{

    private LocalDate dataInicial;
    private LocalDate dataFinal;

    public TransacaoMensal(String descricao, double valor, LocalDate data, boolean ehReceita, String categoria, LocalDate dataInicial, LocalDate dataFinal) {
        super(descricao,valor, data, ehReceita,categoria);
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }


    @Override
    public String toString(){
        return super.toString() + " | " + dataInicial + " | " + dataFinal;
    }

}
