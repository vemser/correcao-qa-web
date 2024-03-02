package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoGetByIDFuncionalTest {
    @Test
    @DisplayName("[CTAXXX] Buscar Questão Por ID - Informar ID Existente (Espera Sucesso)")
    public void testQuestoes_buscarQuestaoComIDValido_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria(2);

        QuestaoResponseDto questaoCriada = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .extract().as(QuestaoResponseDto.class);

        QuestaoResponseDto questaoBuscada = QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200)
                .extract().as(QuestaoResponseDto.class);

        assertAll(
                () -> assertNotNull(questaoBuscada),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getQuestaoId(), questaoBuscada.getQuestaoDTO().getQuestaoId()),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getTitulo(), questaoBuscada.getQuestaoDTO().getTitulo()),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getDescricao(), questaoBuscada.getQuestaoDTO().getDescricao()),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getDificuldade(), questaoBuscada.getQuestaoDTO().getDificuldade()),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getTestes(), questaoBuscada.getQuestaoDTO().getTestes())
        );
    }
    @Test
    @DisplayName("[CTAXXX] Buscar Questão Por ID - Informar ID Inexistente (Espera Erro)")
    public void testQuestoes_buscarQuestaoComIDInexistente_esperaErro() {

        QuestaoResponseDto questaoBuscada = QuestaoClient.buscarQuestaoPorIdInexistente()
            .then()
                .statusCode(404)
                .extract().as(QuestaoResponseDto.class);

    }
    @Test
    @DisplayName("[CTAXXX] Buscar Questão Por ID - Informar ID Maior Que O Limite (Espera Erro)")
    public void testQuestoes_buscarQuestaoComIDMaiorQueOLimite_esperaErro() {


    }
    @Test
    @DisplayName("[CTAXXX] Buscar Questão Por ID - Informar ID Inativo (Espera Erro)")
    public void testQuestoes_buscarQuestaoComIDInativo_esperaErro() {


    }
}