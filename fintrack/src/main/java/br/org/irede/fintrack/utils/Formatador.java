package br.org.irede.fintrack.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Formatador {
    public static LocalDate conversorData(String data){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try{
            return LocalDate.parse(data,formato);
        }catch (DateTimeParseException e){
            System.out.println("Erro: formatação com erro para a data");
            return null;
        }
    }
    public static String conversorString(LocalDate data){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formato);
    }

    public static Double conversorDouble(String valor){
        if (valor == null || valor.isBlank()) {
            return 0.0;
        }
        try {
            String valorTratado = valor.replace(",", ".").trim();
            return Double.parseDouble(valorTratado);
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valor para Double: " + valor);
            return 0.0;
        }
    }
}
