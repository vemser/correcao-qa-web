package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.ErroDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.dto.TesteResponseDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Questão - DELETE")
@DisplayName("Questão - DELETE")
@Owner("Vitor Colombo")

public class QuestaoDeleteFuncionalTest {

    @Test
    @Feature("Deletar Questão Por ID")
    @Story("[CTAXXX] Informar ID Existente (Espera Sucesso)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar uma questão existente por ID a API retorna 200 e a mensagem 'Questão deletada com sucesso!'")
    public void testQuestoes_deletarQuestaoExistente_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String response = QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200)
                .extract().asString();
        assertEquals("Questão deletada com sucesso!", response, "Mensagem de sucesso deve ser igual");
    }

    @Test
    @Feature("Deletar Teste Por ID")
    @Story("[CTAXXX] Informar ID Existente (Espera Sucesso)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar um teste existente por ID a API retorna 200 e a mensagem 'Questão deletada com sucesso!'")
    public void testTestes_deletarTesteExistente_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(200)
                .extract().as(QuestaoResponseDto.class);

        questaoResult = QuestaoClient.buscarQuestaoPorId(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200)
                .extract().as(QuestaoResponseDto.class);

        List<TesteResponseDto> testes = questaoResult.getTestes();

        String response = QuestaoClient.excluirTeste(testes.get(0).getTesteId())
                .then()
                .statusCode(200)
                .extract().asString();

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200);

        assertEquals("Teste deletado com sucesso!", response, "Mensagem de sucesso deve ser igual ao esperado");
    }

    @Test
    @Feature("Deletar Questão Por ID (Espera Erro)")
    @Story("[CTAXXX] Informar ID Existente Com Permissao De Aluno")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar uma questão existente por ID como aluno a API retorna 403 e a mensagem ''")
    public void testQuestoes_deletarQuestaoExistenteComoAluno_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        ErroDto erro = QuestaoClient.excluirQuestaoSemPermissao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(403)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200);

        assertAll("Testes de deletar questão informando id existente com permissão de aluno",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertEquals(erro.getStatus(), 403, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get(""), "","Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Deletar Questão Por ID (Espera Erro)")
    @Story("[CTAXX] Deletar Questao Existente Sem Token")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar uma questão existente por ID sem token de acesso a API retorna 403 e a mensagem ''")
    public void testQuestoes_deletarQuestaoExistenteSemToken_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        ErroDto erro = QuestaoClient.excluirQuestaoSemToken(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(403)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);

        assertAll("Testes de deletar questão informando id existente sem token",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 403, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Deletar Questão Por ID (Espera Erro)")
    @Story("[CTAXX] Deletar Questao Já Deletada")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar uma questão já deletada a API retorna 404 e a mensagem ''")
    public void testQuestoes_deletarQuestaoJaDeletada_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200)
                .extract().asString();

        ErroDto erro = QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar questão informando id de questão inativa",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Deletar Questão Por ID")
    @Story("[CTAXX] Deletar Questao Com ID inexistente (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar uma questão com ID inexistente a API retorna 404 e a mensagem ''")
    public void testQuestoes_deletarQuestaoComIdInexistente_esperaErro() {

        ErroDto erro = QuestaoClient.excluirQuestao(999999999)
            .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar questão informando id inexistente",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Deletar Questão Por ID")
    @Story("[CTAXX] Questoes - Deletar Questao Com ID Nulo (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar uma questão com ID nulo a API retorna 404 e a mensagem ''")
    public void testQuestoes_deletarQuestaoComIdNulo_esperaErro() {

        ErroDto erro = QuestaoClient.excluirQuestaoSemParam()
            .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar questão não informando id",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Deletar Questão Por ID")
    @Story("[CTAXX] Questoes - Deletar Questao Com ID invalido (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar uma questão com ID inválido a API retorna 400 e a mensagem ''")
    public void testQuestoes_deletarQuestaoComIdInvalido_esperaErro() {

        ErroDto erro = QuestaoClient.excluirQuestaoComIdInvalido()
            .then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Deletar Questão Por ID")
    @Story("[CTAXX] Deletar Testes Quando Deleta Questao (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar uma questão com ID inválido a API retorna 400 e a mensagem ''")
    public void testQuestoes_deletarQuestaoExistenteComTestes_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        questaoResult = QuestaoClient.buscarQuestaoPorId(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200)
                .extract().as(QuestaoResponseDto.class);
        List<TesteResponseDto> testes = questaoResult.getTestes();

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);

        ErroDto erro = QuestaoClient.excluirTeste(testes.get(0).getTesteId())
            .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Deletar Teste Por ID")
    @Story("[CTAXX] Deletar Testes Até Limite Mínimo (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar um teste ")
    public void testTestes_deletarTestesLimiteMinimo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoLimiteMinimoTestes();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        questaoResult = QuestaoClient.buscarQuestaoPorId(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200)
                .extract().as(QuestaoResponseDto.class);

        List<TesteResponseDto> testes = questaoResult.getTestes();

        ErroDto erro = QuestaoClient.excluirTeste(testes.get(0).getTesteId())
            .then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Deletar Teste Por ID")
    @Story("[CTAXX] Deletar Testes Com Permissao De Aluno (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar um teste sem permissão a API retorna 403 e a mensagem ''")
    public void testTestes_deletarTestesSemPermissao_esperaErro() {
        ErroDto erro = QuestaoClient.excluirTesteSemPermissao(123)
            .then()
                .statusCode(403)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 403, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Deletar Teste Por ID")
    @Story("[CTAXX] Deletar Testes Com ID Inválido (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar um teste com ID inválido a API retorna 404 e a mensagem ''")
    public void testTestes_deletarTestesComIDInvalido_esperaErro() {
        ErroDto erro = QuestaoClient.excluirTesteComIDInvalido()
            .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @Feature("Deletar Teste Por ID")
    @Story("[CTAXX] Deletar Testes Com ID Inexistente (Espera Erro)")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao deletar um teste com ID inexistente a API retorna 404 e a mensagem ''")
    public void testTestes_deletarTestesComIDInexistente_esperaErro() {
        ErroDto erro = QuestaoClient.excluirTesteComIDInexistente()
            .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }
}
