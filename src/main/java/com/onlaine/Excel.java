package com.onlaine;

import lombok.Builder;
import lombok.Data;

@Data // Atalho que gera automaticamente os métodos da classe.
@Builder // Cria os métodos getters e setters com o @Data e fornece o pattern builder para concatenarmos os atributos dentro da classe.
public class Excel {

    String pathology;
    String sector;
    String notes;

}
