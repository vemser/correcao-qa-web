package com.vemser.correcao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortDto {
    private boolean empty;
    private boolean sorted;
    private boolean unsorted;
}
