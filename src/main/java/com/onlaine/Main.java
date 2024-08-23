package com.onlaine;

import java.io.IOException;
import java.util.List;

import static com.onlaine.ExcelManager.errors;
import static com.onlaine.ExcelManager.lines;

public class Main {
    public static void main(String[] args) throws IOException {

        // Instancia o gerenciador
        ExcelManager excelManager = new ExcelManager();

        // Objeto chama o método create e insere os valores na lista excels
        List<Excel> excels = excelManager.create();

        // Exibição da lista e erros, se houver(em).
        if (errors == 0) {
            excelManager.print(excels);
            System.out.println("\nPlanilha preenchida corretamente.");
        } else if (errors == 1) {
            System.out.println("A patologia e/ou o setor não foram preenchidos. Por favor, verifique a linha " + lines + ".");
        } else {
            System.out.println("A patologia e/ou o setor não foram preenchidos. Por favor, verifique as linhas " + lines + ".");
        }
    }
}
