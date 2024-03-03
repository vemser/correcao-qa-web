package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.*;
import com.vemser.correcao.dto.ErrorDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Questão - GET")
@DisplayName("Questão - GET")
public class QuestaoGetFuncionalTest {
    @Test
    @Owner("Italo Lacerda")
    @Feature("Listar Todas Questões")
    @Story("[CTAXXX] Listar Todas Questões (Espera Sucesso)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar todas as questões API retorna 200 e todas as questões cadastradas no body")
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
    @Owner("Italo Lacerda")
    @Feature("Listar Todas Questões")
    @Story("[CTAXXX] Buscar por uma pagina que não existe (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar todas as questões passando uma página que não existe a API retorna 404 e a mensagem ''")
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
    @Owner("Italo Lacerda")
    @Feature("Listar Todas Questões")
    @Story("[CTAXXX] Buscar por uma pagina que não existe (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar todas as questões passando uma página negativa a API retorna 404 e a mensagem ''")
    public void testQuestoes_buscarPorUmaPaginaComValorNegativo_esperaFalha() {
        final String paginaSolicitada = "-1";
        final String tamanhoPagina = "10";

        ErrorDto questaoResult = QuestaoClient.buscarTodasQuestao(paginaSolicitada, tamanhoPagina)
                .then()
                    .statusCode(404)
                    .extract()
                    .as(ErrorDto.class);

        assertAll("Verifica se retorna lista com tamnaho correto",
                () -> assertEquals(questaoResult.getStatus(), 404),
                () -> assertNotNull(questaoResult.getTimestamp()),
                () -> assertEquals(questaoResult.getErrors().get("Colocar Parametros"), "")
        );
    }
    @Test
    @Owner("Vitor Colombo")
    @Feature("Listar Questões Por ID")
    @Story("[CTAXXX] Informar ID Existente (Espera Sucesso)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão válida por ID a API retorna 200 e todos os dados da questão no body")
    public void testQuestoes_buscarQuestaoComIDValido_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoCriada = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .   extract().as(QuestaoResponseDto.class);

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
    @Owner("Vitor Colombo")
    @Feature("Listar Questões Por ID")
    @Story("[CTAXXX] Informar ID Inexistente (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão com um ID inexistente a API retorna 400 e a mensagem ''")
    public void testQuestoes_buscarQuestaoComIDInexistente_esperaErro() {

        ErrorDto errorDto = QuestaoClient.buscarQuestaoPorIdInexistente()
                .then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        //        TODO: Definir padrão de error para retorno
        assertAll("Testes de buscar questao informando ID inexistente",
                () -> assertNotNull(errorDto.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(errorDto.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(errorDto.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(errorDto.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(errorDto.getErrors().get("Colocar Parametros"), "")
        );
    }
    @Test
    @Owner("Vitor Colombo")
    @Feature("Listar Questões Por ID")
    @Story("[CTAXXX] Informar ID Maior Que O Limite (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão com um ID inexistente maior que o limite a API retorna 400 e a mensagem ''")
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
                () -> assertEquals(erro.getErrors().get(""), "")
        );
    }
    @Test
    @Owner("Vitor Colombo")
    @Feature("Listar Questões Por ID")
    @Story("[CTAXXX] Informar ID Inativo (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão com um ID inativo a API retorna 404 e a mensagem ''")
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
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Owner("Italo Lacerda")
    @Feature("Listar Todas Questões")
    @Story("[CTAXXX] Listar questões sem parâmetro de tamanho pagina (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar todas as questões sem o tamanho da página a API retorna 404 e a mensagem ''")
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
