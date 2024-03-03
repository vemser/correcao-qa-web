package com.vemser.correcao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AtividadeEnviadaDto{
    private Integer atividadesEnviadasId;
    private Integer atividadesId;
    private Integer tentativas;
    private String userName;
    private Double notaTestes;
    private String dataEnvio;
    private String feedbackInstrutor;
    private String status;
    private String comentarioAluno;
    private String criado;
    private String editado;
    private AtividadeDto atividade;
}
