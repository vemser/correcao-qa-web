package com.vemser.correcao.dto;

import com.vemser.correcao.enums.Trilha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarAtividadeDto {

    private String descricao;
    private String edicaoVemSer;
    private String prazoEntrega;
    private ArrayList<Integer> questoes;
    private String titulo;
    private Trilha trilha;
}
