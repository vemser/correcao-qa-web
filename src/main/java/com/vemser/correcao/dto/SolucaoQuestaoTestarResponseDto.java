package com.vemser.correcao.dto;

import com.vemser.compilador.enums.Linguagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolucaoQuestaoTestarResponseDto {
    private String correcao;
    private String testesPassados;
    private String testesTotais;
}
