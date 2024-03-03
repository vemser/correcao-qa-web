package com.vemser.correcao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TesteResponseDto {
    private Integer testeId;
    private String valorEntrada;
    private String retornoEsperado;
    private String exemplo;
    private String criado;
    private String editado;
}
