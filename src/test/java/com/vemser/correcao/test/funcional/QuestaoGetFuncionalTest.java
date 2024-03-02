package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.*;
import com.vemser.correcao.dto.errors.ErrorDto;
import com.vemser.correcao.dto.errors.ErrorsDto;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoGetFuncionalTest {
    @Test
    @DisplayName("Questoes - Listar Todas Questões (Espera Sucesso)")
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
    @DisplayName("Questoes - Buscar por uma pagina que não existe (Espera Falha)")
    public void testQuestoes_buscarPorUmaPaginaQueNaoExiste_esperaFalha() {
        ListaTodasQuestaoResponseDto questao = QuestaoClient.buscarTodasQuestao("0", "10")
                                                                    .as(ListaTodasQuestaoResponseDto.class);

        String paginaSolicitada = Integer.toString(questao.getPageable().getPageNumber() + 1);

        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao(paginaSolicitada, "10")
                .then()
                    .statusCode(404)
                    .extract()
                    .as(ListaTodasQuestaoResponseDto.class);

        //        TODO: Definir padrão de error para retorno
        assertAll("Verifica se retorna lista com tamnaho correto",
                () -> assertEquals(questaoResult.getNumberOfElements(), 0),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements())
        );
        assertEquals(questaoResult.getPageable().getPageNumber(), 0,"Verifica se retorna pagina correta");
    }

    @Test
    @DisplayName("Questoes - Buscar por uma pagina que não existe (Espera Falha)")
    public void testQuestoes_buscarPorUmaPaginaComValorNegativo_esperaFalha() {
        final String paginaSolicitada = "-1";
        final String tamanhoPagina = "10";

        ErrorDto<ErrorsDto> questaoResult = QuestaoClient.buscarTodasQuestao(paginaSolicitada, tamanhoPagina)
                .then()
                    .statusCode(404)
                    .extract()
                    .as(new TypeRef<ErrorDto<ErrorsDto>>() {});

        //        TODO: Definir padrão de error para retorno
        assertAll("Verifica se retorna lista com tamnaho correto",
                () -> assertEquals(questaoResult.getStatus(), 404),
                () -> assertNotNull(questaoResult.getTimestamp()),
                () -> assertEquals(questaoResult.getErrors().getJsonParseError(), "")
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
                .statusCode(400)
                .extract().as(ErrorDto.class);

        //        TODO: Definir padrão de error para retorno
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
        ErrorDto erro = QuestaoClient.buscarQuestaoPorIdMaiorQueOLimite()
                .then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        //        TODO: Definir padrão de error para retorno
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

        ErrorDto erro = QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        //        TODO: Definir padrão de error para retorno
        assertAll("Testes de buscar questao informando ID inativo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("Questoes - Listar questões sem parâmetro de tamanho pagina")
    public void testQuestoes_listarQuestoesSemParametroDeTamanhoPagina_esperaFalha() {
        ListaTodasQuestaoResponseDto questao = QuestaoClient.buscarTodasQuestao("0", "10")
                .as(ListaTodasQuestaoResponseDto.class);

        String paginaSolicitada = Integer.toString(questao.getPageable().getPageNumber() + 1);

        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao(paginaSolicitada, "10")
                .then()
                .statusCode(404)
                .extract()
                .as(ListaTodasQuestaoResponseDto.class);

        //        TODO: Definir padrão de error para retorno
        assertAll("Verifica se retorna lista com tamnaho correto",
                () -> assertEquals(questaoResult.getNumberOfElements(), 0),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements())
        );
        assertEquals(questaoResult.getPageable().getPageNumber(), 0,"Verifica se retorna pagina correta");
    }
}
