package com.vemser.correcao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroAlternativoDto {
    private String timestamp;
    private Integer status;
    private String error;
    private String path;
}
