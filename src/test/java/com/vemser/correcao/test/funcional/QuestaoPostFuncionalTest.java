package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.dto.ErrorDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Questão - POST")
@DisplayName("Questão - POST")
@Owner("Gabriel Sales")
public class QuestaoPostFuncionalTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão com todos os campos válidos a API retorna 201 e a todos os dados da questão criada no body")
    public void testCriarQuestao_informarCamposValidos_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão informando campos validos",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertEquals(questao.getTitulo(), questaoResult.getQuestaoDTO().getTitulo(), "Título da questão deve ser igual"),
                () -> assertEquals(questao.getDificuldade(), questaoResult.getQuestaoDTO().getDificuldade(), "Dificuldade da questão deve ser igual"),
                () -> assertEquals(questao.getLinguagem(), questaoResult.getQuestaoDTO().getLinguagem(), "Linguagem da questão deve ser igual"),
                () -> assertEquals(questao.getTestes().size(), questaoResult.getTestes().size(), "Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Código Prévio Vazio")
    @Description("Teste que verifica se ao criar uma questão com todos os campos válidos menos o código prévio a API retorna 201 e a todos os dados da questão criada no body")
    @Severity(SeverityLevel.NORMAL)
    public void testCriarQuestao_informarCodigoPrevioVazio_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoCodigoVazio();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão informando código prévio vazio",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertEquals(questao.getTitulo(), questaoResult.getQuestaoDTO().getTitulo(), "Título da questão deve ser igual"),
                () -> assertEquals(questao.getDificuldade(), questaoResult.getQuestaoDTO().getDificuldade(), "Dificuldade da questão deve ser igual"),
                () -> assertEquals(questao.getLinguagem(), questaoResult.getQuestaoDTO().getLinguagem(), "Linguagem da questão deve ser igual"),
                () -> assertEquals(questao.getTestes().size(), questaoResult.getTestes().size(), "Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Título Vazio")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão com o título vazio a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_informarTituloVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoTituloVazio();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão informando título vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Descrição Vazia")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão com a descrição vazia a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_informarDescricaoVazia_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDescricaoVazia();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão informando descrição vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Dificuldade Vazia")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão com a dificuldade vazia a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_informarDificuldadeVazia_esperaErro() {
        String questao = QuestaoDataFactory.questaoDificuldadeVazia();

        System.out.println(questao);

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão informando dificuldade vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Linguagem Vazia")
    @Description("Teste que verifica se ao criar uma questão com a linguagem vazia a API retorna 400 e a mensagem ''")
    @Severity(SeverityLevel.NORMAL)
    public void testCriarQuestao_informarLinguagemVazia_esperaErro() {
        String questao = QuestaoDataFactory.questaoLinguagemVazia();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão informando linguagem vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Teste Oculto")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão sem testes ocultos a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_naoInformarTesteOculto_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoSemTesteOculto();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão não informando teste oculto",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Teste De Exemplo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão sem teste de exemplo a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_naoInformarTesteDeExemplo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoSemTesteExemplo();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão não informando teste de exemplo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar 4 Testes De Exemplo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão com quatro testes de exemplo a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_informar4TestesDeExemplo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoCom4TestesExemplos();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão informando 4 testes de exemplo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar 8 Testes Ocultos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão com oito testes ocultos a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_informar8TestesOcultos_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoCom8TestesOcultos();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão informando 8 testes oculto",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Apenas Um Teste")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão com apenas um teste a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_informarApenasUmTeste_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoComApenasUmTeste();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão informando apenas um teste",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Testes Com Valor De Entrada Vazio")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão com testes com entrada vazia a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_informarTestesComValorDeEntradaVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoComTesteValorDeEntradaVazio();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão informando teste com valor de entrada vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Testes Com Retorno Esperado Vazio")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma questão com testes com retorno vazio a API retorna 400 e a mensagem ''")
    public void testCriarQuestao_informarTestesComRetornoEsperadoVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoComTesteRetornoEsperadoVazio();

        ErrorDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar questão informando teste com retorno esperado vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }
}