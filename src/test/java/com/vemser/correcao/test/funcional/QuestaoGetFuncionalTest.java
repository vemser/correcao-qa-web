package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.*;
import com.vemser.correcao.dto.ErroDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Questão - GET")
@DisplayName("Questão - GET")
@Owner("Italo Lacerda | Vitor Nunes")
public class QuestaoGetFuncionalTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Questões Ao Informar Página e Tamanho Válidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões informando página e tamanho válido retorna 200 e todas as questões cadastradas")
    public void testListarQuestoes_informarPaginaETamanhoValidos_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao("0", "10")
                .then()
                    .statusCode(200)
                    .extract()
                    .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Testes de listar questão informando página e tamanho válido",
                () -> assertEquals(questaoResult.getNumberOfElements(), 10, "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(questaoResult.getPageable().getPageNumber(), 0,"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar Questões Ao Informar Página Que Não Existe")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões passando uma página que não existe retorna 404 e a mensagem '?????????'")
    public void testListarQuestoes_informarPaginaQueNaoExiste_esperaErro() {
        ListaTodasQuestaoResponseDto questao = QuestaoClient.buscarTodasQuestao("0", "10")
                                                                    .as(ListaTodasQuestaoResponseDto.class);

        String paginaSolicitada = Integer.toString(questao.getTotalPages() + 1);

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
                () -> assertEquals("?????", erro.getErrors().get("????"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar Questões Ao Informar Página Inválida (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões informando página inválida retorna 404 e a mensagem '?????????'")
    public void testListarQuestoes_informarPaginaInvalida_esperaErro() {
        String paginaSolicitada = "-1";
        String tamanhoPagina = "10";

        ErroDto erro = QuestaoClient.buscarTodasQuestao(paginaSolicitada, tamanhoPagina)
                .then()
                    .statusCode(404)
                    .extract()
                    .as(ErroDto.class);

        assertAll("Testes de listar questão informando página inválida",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("?????", erro.getErrors().get("????"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Buscar Questão Por ID Ao Informar ID Existente")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID Existente retorna 200 e todos os dados da questão")
    public void testBuscarQuestaoPorID_informarIDExistente_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoCriada = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .   extract().as(QuestaoResponseDto.class);

        QuestaoResponseDto questaoBuscada = QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
                .then()
                    .statusCode(200)
                    .extract().as(QuestaoResponseDto.class);

        assertAll("Testes de buscar questão por ID informando ID existente",
                () -> assertNotNull(questaoBuscada, "Questão buscada não deve ser nula"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getQuestaoId(), questaoBuscada.getQuestaoDTO().getQuestaoId(), "ID da questão buscada deve ser igual ao ID da questão criada"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getTitulo(), questaoBuscada.getQuestaoDTO().getTitulo(), "Título da questão buscada deve ser igual ao título da questão criada"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getDescricao(), questaoBuscada.getQuestaoDTO().getDescricao(), "Descrição da questão buscada deve ser igual a descrição da questão criada"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getDificuldade(), questaoBuscada.getQuestaoDTO().getDificuldade(), "Dificuldade da questão buscada deve ser igual a dificuldade da questão criada"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getTestes(), questaoBuscada.getQuestaoDTO().getTestes(), "Testes da questão buscada deve ser igual aos testes da questão criada")
        );
    }
  
    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Buscar Questão Por ID Ao Informar ID Inexistente (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID inexistente retorna 400 e a mensagem 'Questão não encontrada com o ID fornecido'")
    public void testBuscarQuestaoPorID_informarIDInexistente_esperaErro() {

        ErroDto erro = QuestaoClient.buscarQuestaoPorIdInexistente()
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar questão por ID informando ID Inexistente",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Questão não encontrada com o ID fornecido", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }
  
    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Buscar Questão Por ID Ao Informar ID Maior Que O Limite")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID maior que o limite retorna 400 e a mensagem '???????'")
    public void testBuscarQuestaoPorId_informarIDMaiorQueOLimite_esperaErro() {
        ErroDto erro = QuestaoClient.buscarQuestaoPorIdMaiorQueOLimite()
                .then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar questão informando ID maior que o limite",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("??????", erro.getErrors().get("??????"), "Mensagem de erro deve ser igual a esperada")
        );
    }
  
    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Buscar Questão Por ID Ao Informar ID Inativo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID inativo retorna 404 e a mensagem '?????'")
    public void testBuscarQuestaoPorID_informarIDInativo_esperaErro() {
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

        assertAll("Testes de buscar questão informando ID inativo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("??????", erro.getErrors().get("??????"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar Questões Ao Não Informar Parâmetro De Tamanho Da Página")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão não informando parâmetro de tamanho de página retorna 400 e a mensagem '??????'")
    public void testListarQuestoes_naoInformarParametroDeTamanhoPagina_esperaErro() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("paginaSolicitada", "0");

        ErroDto erro = QuestaoClient.buscarTodasQuestao(queryParams)
                .then()
                    .statusCode(400)
                    .extract()
                    .as(ErroDto.class);

        assertAll("Testes de buscar questão não informando parâmetro de tamanho da página",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("??????", erro.getErrors().get("??????"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar Questões Ao Não Informar Parâmetro De Página Solicitada")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão não informando parâmetro de tamanho de página retorna 400 e a mensagem '??????'")
    public void testListarQuestoes_naoInformarParametroDePaginaSolicitada_esperaErro() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("tamanhoPagina", "10");

        ErroDto erro = QuestaoClient.buscarTodasQuestao(queryParams)
                .then()
                .statusCode(400)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de buscar questão não informando parâmetro de página solicitada",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("??????", erro.getErrors().get("??????"), "Mensagem de erro deve ser igual a esperada")
        );
    }
}
