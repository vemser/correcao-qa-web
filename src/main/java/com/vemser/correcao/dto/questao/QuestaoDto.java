package com.vemser.correcao.dto.questao;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.dto.teste.TesteDto;
import com.vemser.correcao.enums.Dificuldade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoDto {
    private Integer questaoId;
    private String codigo;
    private String descricao;
    private Dificuldade dificuldade;
    private Linguagem linguagem;
    private String titulo;
    private ArrayList<TesteDto> testes;
    private String criado;
    private String editado;
    private  boolean resolvida;
    private String ativo;
}
