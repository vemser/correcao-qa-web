package com.vemser.correcao.dto;

import com.vemser.compilador.enums.Linguagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TesteDto {
    private String exemplo;
    private String retornoEsperado;
    private String valorEntrada;
}