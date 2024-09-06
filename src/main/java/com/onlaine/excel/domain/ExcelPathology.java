package com.onlaine.excel.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data // Atalho que gera automaticamente os métodos da classe.
@Builder
// Cria os métodos getters e setters com o @Data e fornece o pattern builder para concatenarmos os atributos dentro da classe.
@Entity
public class ExcelPathology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String item;
    String pathology;

    @Transient
    String error;

    public ExcelPathology(int id, String item, String pathology, String error) {
        this.id = id;
        this.item = item;
        this.pathology = pathology;
        this.error = error;
    }

    public ExcelPathology() {

    }
}
