package com.vemser.correcao.dto;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.enums.Dificuldade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolucaoQuestaoDto {
    private Integer atividadeEnviadaId;
    private String codigo;
    private Linguagem linguagem;
    private Integer questaoId;
}
