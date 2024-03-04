package com.vemser.correcao.dto.atividade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarAtividadeResponseDto {

    private int atividadeId;
    private String titulo;
    private String descricao;
    private List<Integer> questoes;
    private String trilha;
    private String prazoEntrega;
}
