package com.vemser.correcao.dto.teste;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.enums.Exemplo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TesteDto {
    private Exemplo exemplo;
    private String retornoEsperado;
    private String valorEntrada;
}