package com.vemser.correcao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaTodasQuestaoResponseDto {
    private List<QuestaoDto> content;
    private boolean empty;
    private boolean
}
