package br.org.irede.fintrack.model;

import java.time.LocalDate;

public class TransacaoMensal extends Transacao{

    private LocalDate dateEnd;

    public TransacaoMensal(String descricao, double valor, LocalDate data, boolean ehReceita, String categoria, LocalDate dataEnd) {
        super(descricao,valor, data, ehReceita,categoria);
        this.dateEnd = dataEnd;
    }

    public void setDateEnd(LocalDate dataFinal) {
        this.dateEnd = dataFinal;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }


    @Override
    public String toString(){
        return super.toString() + " | " + " | " + dateEnd;
    }

}
