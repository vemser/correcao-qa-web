package com.vemser.correcao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompiladorResponseDto {
    private String retorno;
    private String timestamp;
    private Integer status;
    private ArrayList<String> errors;
}
