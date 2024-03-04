package com.vemser.correcao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginacaoAtividadeEnviadaDto {
    private List<AtividadeEnviadaDto> content;
    private boolean empty;
    private boolean first;
    private Integer number;
    private Integer numberOfElements;
    private PageableDto pageable;
    private Integer size;
    private boolean last;
    private SortDto sort;
    private Integer totalElements;
    private Integer totalPages;
}
