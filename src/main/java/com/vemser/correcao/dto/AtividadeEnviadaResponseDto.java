package com.vemser.correcao.dto;

import com.vemser.correcao.enums.Exemplo;
import com.vemser.correcao.enums.Trilha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeEnviadaResponseDto {
    private Integer atividadesEnviadasId;
    private String comentarioAluno;
    private String descricao;
    private String feedbackInstrutor;
    private Integer limiteEnvios;
    private Integer notaTestes;
    private String prazoEntrega;
    private ArrayList<QuestaoDto> questoes;
    private String status;
    private String titulo;
    private Trilha trilha;
    private String userName;
}
