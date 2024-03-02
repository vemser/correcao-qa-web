package com.vemser.correcao.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto<T> {
    private String timestamp;
    private Integer status;
    private T errors;
}
