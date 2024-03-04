package com.vemser.correcao.dto.erro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroForbiddenDto {
    private String timestamp;
    private Integer status;
    private String error;
    private String path;
}
