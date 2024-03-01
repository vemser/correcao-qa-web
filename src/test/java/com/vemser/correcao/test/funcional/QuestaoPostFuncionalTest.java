package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoPostFuncionalTest {
    @Test
    @DisplayName("Criar Questão - Informar Campos Válidos (Espera Sucesso)")
    public void testQuestoes_cadastroComDadosValidos_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questões com sucesso",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertEquals(questao.getTitulo(), questaoResult.getQuestaoDTO().getTitulo(), "Título da questão deve ser igual"),
                () -> assertEquals(questao.getDificuldade(), questaoResult.getQuestaoDTO().getDificuldade(), "Dificuldade da questão deve ser igual"),
                () -> assertEquals(questao.getLinguagem(), questaoResult.getQuestaoDTO().getLinguagem(), "Linguagem da questão deve ser igual"),
                () -> assertEquals(questao.getTestes().size(), questaoResult.getTestes().size(), "Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @DisplayName("Criar Questão - Informar Título Vazio (Espera Erro)")
    public void testQuestoes_informarTituloVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoSemTitulo();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(QuestaoResponseDto.class);


    }
}