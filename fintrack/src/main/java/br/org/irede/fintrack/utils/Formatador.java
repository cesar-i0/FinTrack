package br.org.irede.fintrack.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Formatador {
    public LocalDate conversorData(String data){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try{
            return LocalDate.parse(data,formato);
        }catch (DateTimeParseException e){
            System.out.println("Erro: formatação com erro para a data '" + data + "'");
            return null;
        }
    }
    public String conversorString(LocalDate data){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formato);
    }
}
