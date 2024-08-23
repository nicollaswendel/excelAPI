package com.onlaine;

import lombok.Cleanup;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelManager {

    static int errors = 0;
    static List<Integer> lines = new ArrayList<>();

    public List<Excel> create() throws IOException {

        List<Excel> excelsAccurate = new ArrayList<>();

        // FileInputStream permite ler o arquivo.
        @Cleanup FileInputStream file = new FileInputStream("src/main/resources/Patologias.xlsx");

        // Lê a pasta de trabalho (todas as planilhas do arquivo Excel).
        Workbook workbook = new XSSFWorkbook(file);

        // Indica especificamente (index) qual a planilha a ser lida.
        Sheet sheet = workbook.getSheetAt(0);

        // Seta as linhas da planilha a serem percorridas.
        @SuppressWarnings("unchecked")
        List<Row> rows = (List<Row>) toList(sheet.iterator());

        // Remove os cabeçalhos (primeira linha).
        rows.removeFirst();

        // Itera através das linhas.
        for (Row row : rows) {

            // Pega os valores das células pelo índice.
            String pathology = getCellValue(row.getCell(0));
            String sector = getCellValue(row.getCell(1));
            String notes = getCellValue(row.getCell(2));

            // Confere se os campos obrigatórios estão preenchidos.
            if (!pathology.isEmpty() && !sector.isEmpty()) {
                // Atribui os valores à classe Excel.
                Excel excel = Excel.builder()
                        .pathology(pathology)
                        .sector(sector)
                        .notes(notes)
                        .build();

                // Adiciona o objeto do tipo Excel à lista apenas se os campos obrigatórios estiverem preenchidos.
                excelsAccurate.add(excel);
            } else {
                errors++; // Incrementa à variável 'errors' a quantidade de linhas com preenchimento incorreto.
                lines.add(row.getRowNum() + 1);
            }
        }

        return excelsAccurate;
    }

    // Método para converter o valor da célula em uma string.
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return ""; // Return empty string for null cells.
        }

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> ""; // Handle other types as empty.
        };
    }

    // Converte o iterador para lista.
    public List<?> toList(Iterator<?> iterator) {
        return IteratorUtils.toList(iterator);
    }

    // Exibe a lista no console.
    public void print(@NotNull List<Excel> excels) {
        excels.forEach(System.out::println);
    }

}
