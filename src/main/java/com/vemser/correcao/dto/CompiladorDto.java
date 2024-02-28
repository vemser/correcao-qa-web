package com.vemser.correcao.dto;

import com.vemser.correcao.enums.Linguagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompiladorDto {
    private String codigo;
    private Linguagem linguagem;
}
