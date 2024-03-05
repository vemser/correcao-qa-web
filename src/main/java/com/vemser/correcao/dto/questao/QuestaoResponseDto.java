package com.vemser.correcao.dto.questao;

import com.vemser.correcao.dto.teste.TesteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoResponseDto {
    private QuestaoDto questaoDTO;
    private List<TesteResponseDto> testes;
}
