package com.vemser.correcao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrigirAtividadeDto {

    private Integer atividadeEnviadaID;
    private String feedbackProfessor;
    private Integer notaTestes;
}
