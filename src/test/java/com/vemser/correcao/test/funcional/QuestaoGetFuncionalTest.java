package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.ErroDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoGetFuncionalTest {
    @Test
    @DisplayName("[CTAXXX] Buscar Questão Por ID - Informar ID Existente (Espera Sucesso)")
    public void testQuestoes_buscarQuestaoComIDValido_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoCriada = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .extract().as(QuestaoResponseDto.class);

        QuestaoResponseDto questaoBuscada = QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200)
                .extract().as(QuestaoResponseDto.class);

        assertAll("Testes de buscar questao informando ID existente",
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

        ErroDto erroDto = QuestaoClient.buscarQuestaoPorIdInexistente()
            .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar questao informando ID inexistente",
                () -> assertNotNull(erroDto.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erroDto.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erroDto.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erroDto.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erroDto.getErrors().contains("Questão não encontrada com o ID fornecido"), "Mensagem de erro deve ser igual ao esperado")
        );
    }
    @Test
    @DisplayName("[CTAXXX] Buscar Questão Por ID - Informar ID Maior Que O Limite (Espera Erro)")
    public void testQuestoes_buscarQuestaoComIDMaiorQueOLimite_esperaErro() {
        ErroDto erro = QuestaoClient.buscarQuestaoPorIdMaiorQueOLimite()
            .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar questao informando ID maior que o limite",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }
    @Test
    @DisplayName("[CTAXXX] Buscar Questão Por ID - Informar ID Inativo (Espera Erro)")
    public void testQuestoes_buscarQuestaoComIDInativo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoCriada = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoCriada.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);

        ErroDto erro = QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar questao informando ID inativo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }
}