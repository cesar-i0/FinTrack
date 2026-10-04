package br.org.irede.fintrack.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Formatador {
    static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static LocalDate conversorData(String data){
        if (data == null || data.isBlank()){
            return null;
        }
        try{
            return LocalDate.parse(data,formato);
        }catch (DateTimeParseException e){
            System.out.println("Erro: formatação com erro para a data");
            return null;
        }
    }
    public static String conversorString(LocalDate data){
        if (data == null){
            return null;
        }
        return data.format(formato);
    }

    public static LocalDate conversorDoBanco(String data){
        if (data == null || data.isBlank()){
            return null;
        }
        try{
            return LocalDate.parse(data);
        }catch (DateTimeParseException e){
            System.out.println("Erro: data inválida no banco: " + data);
            return null;
        }
    }

    public static String conversorParaBanco(LocalDate data){
        if (data == null){
            return null;
        }
        return data.toString();
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
