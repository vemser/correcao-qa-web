package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.dto.ListaTodasQuestaoResponseDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.factory.QuestaoDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoGetFuncionalTest {
    @Test
    @DisplayName("Questoes - Criar Questao Informando Campos Válidos (Espera Sucesso)")
    public void testQuestoes_cadastroComDadosValidos_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao()
                .then()
                    .statusCode(200)
                .log().all()
                    .extract()
                    .as(ListaTodasQuestaoResponseDto.class);

//        assertAll("Testes de criar questões com sucesso",
//                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
//                () -> assertEquals(questao.getTitulo(), questaoResult.getQuestaoDTO().getTitulo(), "Título da questão deve ser igual"),
//                () -> assertEquals(questao.getDificuldade(), questaoResult.getQuestaoDTO().getDificuldade(), "Dificuldade da questão deve ser igual"),
//                () -> assertEquals(questao.getLinguagem(), questaoResult.getQuestaoDTO().getLinguagem(), "Linguagem da questão deve ser igual"),
//                () -> assertEquals(questao.getTestes().size(), questaoResult.getQuestaoDTO().getTestes().size(), "Quantidade de testes da questão deve ser igual")
//        );
    }

}
