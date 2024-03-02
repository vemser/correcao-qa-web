package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.*;
import com.vemser.correcao.dto.ErrorDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoGetFuncionalTest {
    @Test
    @DisplayName("[CTAXXX] Listar Questões - Listar Todas Questões (Espera Sucesso)")
    public void testQuestoes_listarTodasQuestoes_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao("0", "10")
                .then()
                    .statusCode(200)
                    .extract()
                    .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Verifica se retorna lista com tamnaho correto",
                () -> assertEquals(questaoResult.getNumberOfElements(), 10),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements())
        );
        assertEquals(questaoResult.getPageable().getPageNumber(), 0,"Verifica se retorna pagina correta");
    }

    @Test
    @DisplayName("[CTAXXX] Listar Questões - Buscar por uma pagina que não existe (Espera Falha)")
    public void testQuestoes_buscarPorUmaPaginaQueNaoExiste_esperaFalha() {
        ListaTodasQuestaoResponseDto questao = QuestaoClient.buscarTodasQuestao("0", "10")
                                                                    .as(ListaTodasQuestaoResponseDto.class);

        String paginaSolicitada = Integer.toString(questao.getTotalPages() + 1);

        ErrorDto questaoResult = QuestaoClient.buscarTodasQuestao(paginaSolicitada, "10")
                .then()
                    .statusCode(404)
                    .extract()
                    .as(ErrorDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertNotNull(questaoResult.getTimestamp()),
                () -> assertEquals(questaoResult.getStatus(), 404),
                () -> assertEquals(questaoResult.getErrors().get("error"), "Página não encontrada")
        );

    }

    @Test
    @DisplayName("[CTAXXX] Listar Questões - Buscar por uma pagina que não existe (Espera Falha)")
    public void testQuestoes_buscarPorUmaPaginaComValorNegativo_esperaFalha() {
        final String paginaSolicitada = "-1";
        final String tamanhoPagina = "10";

        ErrorDto questaoResult = QuestaoClient.buscarTodasQuestao(paginaSolicitada, tamanhoPagina)
                .then()
                    .statusCode(404)
                    .extract()
                    .as(ErrorDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(questaoResult.getStatus(), 404),
                () -> assertNotNull(questaoResult.getTimestamp()),
                () -> assertEquals(questaoResult.getErrors().get("error"), "Página não encontrada")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Listar Questões - Listar questões sem parâmetro de tamanho pagina (esperaFalha)")
    public void testQuestoes_listarQuestoesSemParametroDeTamanhoPagina_esperaFalha() {
        ErrorDto questaoResult = QuestaoClient.buscarTodasQuestao("0")
                .then()
                .statusCode(400)
                .log().all()
                .extract()
                .as(ErrorDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(questaoResult.getStatus(), 400),
                () -> assertNotNull(questaoResult.getTimestamp()),
                () -> assertEquals(questaoResult.getErrors().get("tamanhoPagina"), "Não pode ser nulo")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Listar Questões - Listar questões sem parâmetro de pagina solicitada (esperaFalha)")
    public void testQuestoes_listarQuestoesSemParametroDePaginaSolicitada_esperaFalha() {
        ErrorDto questaoResult = QuestaoClient.buscarTodasQuestao("0")
                .then()
                .statusCode(400)
                .log().all()
                .extract()
                .as(ErrorDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(questaoResult.getStatus(), 400),
                () -> assertNotNull(questaoResult.getTimestamp()),
                () -> assertEquals(questaoResult.getErrors().get("paginaSolicitada"), "Não pode ser nulo")
        );
    }
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

        ErrorDto erroDto = QuestaoClient.buscarQuestaoPorIdInexistente()
                .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(erroDto.getStatus(), 404),
                () -> assertNotNull(erroDto.getTimestamp()),
                () -> assertEquals(erroDto.getErrors().get("error"), "Questão não encontrada")
        );
    }
    @Test
    @DisplayName("[CTAXXX] Buscar Questão Por ID - Informar ID Maior Que O Limite (Espera Erro)")
    public void testQuestoes_buscarQuestaoComIDMaiorQueOLimite_esperaErro() {
        ErrorDto errorDto = QuestaoClient.buscarQuestaoPorIdMaiorQueOLimite()
                .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(errorDto.getStatus(), 404),
                () -> assertNotNull(errorDto.getTimestamp()),
                () -> assertEquals(errorDto.getErrors().get("error"), "Questão não encontrada")
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

        ErrorDto errorDto = QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(errorDto.getStatus(), 404),
                () -> assertNotNull(errorDto.getTimestamp()),
                () -> assertEquals(errorDto.getErrors().get("error"), "Questão não encontrada")
        );
    }
}
