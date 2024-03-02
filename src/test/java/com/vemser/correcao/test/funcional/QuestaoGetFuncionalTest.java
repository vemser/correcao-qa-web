package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.dto.ListaTodasQuestaoResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoGetFuncionalTest {
    @Test
    @DisplayName("Questoes - Listar Todas Questões (Espera Sucesso)")
    public void testQuestoes_cadastroComDadosValidos_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao("0", "10")
                .then()
                    .statusCode(200)
                .log().all()
                    .extract()
                    .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Verifica se retorna lista com tamnaho correto",
                () -> assertEquals(questaoResult.getNumberOfElements(), 10),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements())
        );
        assertEquals(questaoResult.getPageable().getPageNumber(), 0,"Verifica se retorna pagina correta");
    }

}
