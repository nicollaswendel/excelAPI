package com.onlaine.excel.controller;

import com.onlaine.excel.domain.ExcelPathology;
import com.onlaine.excel.service.ExcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService service = new ExcelService();

    @GetMapping
    public List<ExcelPathology> listAll(){
        return service.listAll();
    }

    @PostMapping("/import")
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        service.importExcel(file);
        return ResponseEntity.ok("Planilha salva com sucesso.");
    }

}
