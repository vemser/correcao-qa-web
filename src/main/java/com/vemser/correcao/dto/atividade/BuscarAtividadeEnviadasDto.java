package com.vemser.correcao.dto.atividade;

import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.enums.Trilha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuscarAtividadeEnviadasDto {
    private String userName;
    private Trilha trilha;
    private Integer atividadesEnviadasId;
    private String feedbackInstrutor;
    private String status;
    private String comentarioAluno;
    private String titulo;
    private String descricao;
    private int limiteEnvios;
    private String prazoEntrega;
    private int notaTestes;
    private List<QuestaoDto> questoes;
}
