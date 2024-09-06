package com.onlaine.excel;

import com.onlaine.excel.domain.ExcelPathology;
import com.onlaine.excel.service.ExcelService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class Main {
    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);

        // Instancia o gerenciador
        ExcelService excelService = new ExcelService();


        // Resume a quantidade de linhas (geral, corretas e incorretas)
        int allLines = excelService.allLines();
        int rightLines = excelService.rightLines();
        int errorsLines = excelService.errorsLines();
        System.out.println("\nTotal de linhas: " + allLines + ".");
        System.out.println("Linhas preenchidas corretamente: " + rightLines + ".");
        System.out.println("Linhas com erro(s): " + errorsLines + ".");

        if (errorsLines > 0) {
            System.out.println("\nPor favor, verifique a(s) linha(s): " + excelService.listErrors() + ".");
        }

        // Tarefa: Armazenar somente as linhas sem erros.
        System.out.print("\nPLANILHA:\n" + excelService.getExcelsAccurate());
    }

}
