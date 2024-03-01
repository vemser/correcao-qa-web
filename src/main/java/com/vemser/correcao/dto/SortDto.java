package com.vemser.correcao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortDto {
    private Integer empty;
    private Integer sorted;
    private Integer unsorted;
}
