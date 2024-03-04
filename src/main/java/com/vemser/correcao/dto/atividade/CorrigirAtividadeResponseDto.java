package com.vemser.correcao.dto.atividade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrigirAtividadeResponseDto {

    private Integer atividadesEnviadasId;
    private Integer atividadesId;
    private String userName;
    private String feedbackInstrutor;
    private String status;
    private Double notaTestes;
    private AtividadeCorrigirDto atividade;
}
