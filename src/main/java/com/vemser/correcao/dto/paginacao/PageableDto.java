package com.vemser.correcao.dto.paginacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableDto {
    private Integer offset;
    private Integer pageNumber;
    private Integer pageSize;
    private boolean paged;
    private SortDto sort;
    private boolean unpaged;
}
