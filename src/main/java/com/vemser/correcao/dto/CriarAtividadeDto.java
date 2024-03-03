package com.vemser.correcao.dto;

import com.vemser.correcao.enums.Trilha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarAtividadeDto {

    private String descricao;
    private String edicaoVemSer;
    private String prazoEntrega;
    private List<Integer> questoes;
    private String titulo;
    private Trilha trilha;
}
