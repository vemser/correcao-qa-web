package com.vemser.correcao.dto.erro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroDto {
    private String timestamp;
    private Integer status;
    private Map<String, String> errors;
}
