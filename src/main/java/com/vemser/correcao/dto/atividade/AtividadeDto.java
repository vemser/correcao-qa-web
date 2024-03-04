package com.vemser.correcao.dto.atividade;

import com.vemser.correcao.enums.Exemplo;
import com.vemser.correcao.enums.Trilha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeDto {
    private Integer atividadeId;
    private Integer limiteEnvios;
    private String titulo;
    private String descricao;
    private String userName;
    private Trilha trilha;
    private String prazoEntrega;
    private Exemplo ativo;
}
