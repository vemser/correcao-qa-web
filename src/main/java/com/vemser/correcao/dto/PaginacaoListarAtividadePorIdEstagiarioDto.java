package com.vemser.correcao.dto;

import com.vemser.correcao.dto.paginacao.PageableDto;
import com.vemser.correcao.dto.paginacao.SortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginacaoListarAtividadePorIdEstagiarioDto {
    private List<AtividadeEnviadaEstagiarioDto> content;
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
