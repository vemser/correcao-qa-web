package com.vemser.correcao.dto.atividade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeCorrigirDto {
    private String userName;
    private String titulo;
    private String descricao;
    private String trilha;
}