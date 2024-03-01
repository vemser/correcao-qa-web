package com.vemser.correcao.test;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.factory.QuestaoDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoFuncionalTest {
    @Test
    @DisplayName("Questoes - Informar Campos Válidos (Espera Sucesso)")
    public void testQuestoes_cadastroComDadosValidos_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria();
        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);

        assertAll("questaoResult",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId())
        );
    }



}
