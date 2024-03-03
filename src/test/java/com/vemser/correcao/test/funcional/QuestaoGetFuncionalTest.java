package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.*;
import com.vemser.correcao.dto.ErroDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Questão - GET")
@DisplayName("Questão - GET")
@Owner("Italo Lacer | Vitor Nunes")
public class QuestaoGetFuncionalTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Questões Ao Informar Página e Tamanho Válidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões informando página e tamanho válido retorna 200 e todas as questões cadastradas no body")
    public void testQuestoes_listarQuestoesAoInformarPaginaETamanhoValidos_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao("0", "10")
                .then()
                    .statusCode(200)
                    .extract()
                    .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Testes de listar questão informando página e tamanho válido",
                () -> assertEquals(questaoResult.getNumberOfElements(), 10),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements()),
                () -> assertEquals(questaoResult.getPageable().getPageNumber(), 0,"Verifica se retorna pagina correta")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar Questões Ao Informar Página Que Não Existe")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões passando uma página que não existe retorna 404 e a mensagem ''")
    public void testQuestoes_listarQuestoesAoInformarPaginaQueNaoExiste_esperaErro() {
        ListaTodasQuestaoResponseDto questao = QuestaoClient.buscarTodasQuestao("0", "10")
                                                                    .as(ListaTodasQuestaoResponseDto.class);

        String paginaSolicitada = Integer.toString(questao.getPageable().getPageNumber() + 1);

        ErroDto erro = QuestaoClient.buscarTodasQuestao(paginaSolicitada, "10")
                .then()
                    .statusCode(404)
                    .extract()
                    .as(ErroDto.class);

        assertAll("Testes de listar questão informando página que não existe",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Erro ao compilar o arquivo", erro.getErrors().get("codigo"), "Mensagem de erro deve ser igual a esperada")
        );
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

        ErroDto questaoResult = QuestaoClient.buscarTodasQuestao(paginaSolicitada, tamanhoPagina)
                .then()
                    .statusCode(404)
                    .extract()
                    .as(ErroDto.class);

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

        ErroDto erroDto = QuestaoClient.buscarQuestaoPorIdInexistente()
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        //        TODO: Definir padrão de error para retorno
        assertAll("Testes de buscar questao informando ID inexistente",
                () -> assertNotNull(erroDto.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erroDto.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erroDto.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erroDto.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erroDto.getErrors().get("Colocar Parametros"), "")
        );
    }
    @Test
    @Owner("Vitor Colombo")
    @Feature("Listar Questões Por ID")
    @Story("[CTAXXX] Informar ID Maior Que O Limite (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão com um ID inexistente maior que o limite a API retorna 400 e a mensagem ''")
    public void testQuestoes_buscarQuestaoComIDMaiorQueOLimite_esperaErro() {
        ErroDto erro = QuestaoClient.buscarQuestaoPorIdMaiorQueOLimite()
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

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

        ErroDto erro = QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

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
