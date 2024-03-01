package com.vemser.correcao.dto;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.enums.Dificuldade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoDto {
    private String questaoId;
    private String codigo;
    private String descricao;
    private Dificuldade dificuldade;
    private Linguagem linguagem;
    private String titulo;
    private List<TesteDto> testes;
    private String criado;
    private String editado;
    private String ativo;
}
