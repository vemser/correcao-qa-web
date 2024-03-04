package com.vemser.correcao.dto.teste;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditarTesteDto {
    private String exemplo;
    private String retornoEsperado;
    private Integer testeId;
    private String valorEntrada;
}
