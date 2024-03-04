package com.vemser.correcao.dto.questao;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.dto.teste.EditarTesteDto;
import com.vemser.correcao.enums.Dificuldade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditarQuestaoDto {
    private String codigo;
    private String descricao;
    private Dificuldade dificuldade;
    private Linguagem linguagem;
    private List<EditarTesteDto> testes;
    private String titulo;
}
