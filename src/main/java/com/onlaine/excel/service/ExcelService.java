package com.onlaine.excel.service;

import com.onlaine.excel.domain.ExcelPathology;
import com.onlaine.excel.repository.ExcelRepository;
import lombok.Cleanup;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private ExcelRepository excelRepository;

    static int contRight = 0;
    static int contErrors = 0;
    static int contAll;
    static List<Integer> lines = new ArrayList<>();
    static List<ExcelPathology> allExcels = new ArrayList<>();
    static List<ExcelPathology> excelsInaccurate = new ArrayList<>();
    static List<ExcelPathology> excelsAccurate = new ArrayList<>();

    // Método para ler e gravar as informações do Excel em uma lista.
    public void importExcel(MultipartFile file) throws IOException {

        InputStream excelFile = file.getInputStream();

        // FileInputStream permite ler o arquivo.
        // @Cleanup FileInputStream file = new FileInputStream("src/main/resources/Patologias.xlsx");

        // Lê a pasta de trabalho (todas as planilhas do arquivo Excel).
        Workbook workbook = new XSSFWorkbook(excelFile);

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
            String item = getCellValue(row.getCell(0));
            String pathology = getCellValue(row.getCell(1));

            // Confere se os campos obrigatórios estão preenchidos.
            if (!pathology.isEmpty() && !item.isEmpty()) {
                // Atribui os valores à classe Excel.
                ExcelPathology excel = ExcelPathology.builder()
                        .item(item)
                        .pathology(pathology)
                        .error("")
                        .build();

                contRight++;
                excelsAccurate.add(excel);
                allExcels.add(excel);

            } else {

                if (pathology.isEmpty() && !item.isEmpty()) {
                    ExcelPathology excel = ExcelPathology.builder()
                            .item(item)
                            .pathology(pathology)
                            .error("O campo patologia é obrigatório.")
                            .build();
                    excelsInaccurate.add(excel);
                    allExcels.add(excel);
                }

                if (item.isEmpty() && !pathology.isEmpty()){
                    ExcelPathology excel = ExcelPathology.builder()
                            .item(item)
                            .pathology(pathology)
                            .error("O campo item é obrigatório.")
                            .build();
                    excelsInaccurate.add(excel);
                    allExcels.add(excel);
                }

                if (pathology.isEmpty() && item.isEmpty()) {
                    ExcelPathology excel = ExcelPathology.builder()
                            .item(item)
                            .pathology(pathology)
                            .error("Os campos 'patologia' e 'setor' são obrigatórios.")
                            .build();
                    excelsInaccurate.add(excel);
                    allExcels.add(excel);
                }

                contErrors++; // Incrementa à variável 'errors' a quantidade de linhas com preenchimento incorreto.
                lines.add(row.getRowNum() + 1);
            }
        }

        contAll = contRight + contErrors;
    }


    // Método para retornar a lista correta.
    public String getExcelsAccurate() {
        return excelsAccurate.toString();
    }


    // Método para retornar a lista incorreta.
    public String getExcelsInaccurate() {
        return excelsInaccurate.toString();
    }


    // Método para contagem de todas as linhas.
    public int allLines() {
        return contAll;
    }


    // Método para contagem de linha(s) correta(s).
    public int rightLines() {
        return contRight;
    }


    // Método para contagem de linha(s) com erro(s).
    public int errorsLines() {
        return contErrors;
    }


    // Método para exibir a lista que contém o(s) índice(s) da(s) linha(s) com erro(s).
    public List<Integer> listErrors() {
        return lines;
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
    public void print(@NotNull List<ExcelPathology> excels) {
        excels.forEach(System.out::println);
    }


    // Listar todos os registros.
    public List<ExcelPathology> listAll() {
        return (List<ExcelPathology>) excelRepository.findAll();
    }

    // Salvar as informações no banco de dados.
    public void save(List<ExcelPathology> excel){
        excelRepository.saveAll(excel);
    }

}