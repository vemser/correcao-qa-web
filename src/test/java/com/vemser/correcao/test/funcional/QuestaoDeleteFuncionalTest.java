package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.ErrorDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.dto.TesteResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoDeleteFuncionalTest {
    // CENÁRIOS POSITIVOS
    @Test
    @DisplayName("[CTAXXX] Deletar Questão - Informar ID Existente (Espera Sucesso)")
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
    @DisplayName("[CTAXXX] Deletar Teste - Informar ID Existente (Espera Sucesso)")
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

    // CENÁRIOS NEGATIVOS
    @Test
    @DisplayName("[CTAXXX] Deletar Questão - Informar ID Existente Com Permissao De Aluno (Espera Erro)")
    public void testQuestoes_deletarQuestaoExistenteComoAluno_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        ErrorDto erro = QuestaoClient.excluirQuestaoSemPermissao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(403)
                .extract().as(ErrorDto.class);

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
    @DisplayName("[CTAXX] Questoes - Deletar Questao Existente Sem Token (Espera Erro)")
    public void testQuestoes_deletarQuestaoExistenteSemToken_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        ErrorDto erro = QuestaoClient.excluirQuestaoSemToken(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(403)
                .extract().as(ErrorDto.class);

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
    @DisplayName("[CTAXX] Questoes - Deletar Questao Já Deletada (Espera Erro)")
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

        ErrorDto erro = QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        assertAll("Testes de deletar questão informando id de questão inativa",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Questao Com ID inexistente (Espera Erro)")
    public void testQuestoes_deletarQuestaoComIdInexistente_esperaErro() {

        ErrorDto erro = QuestaoClient.excluirQuestao(999999999)
            .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        assertAll("Testes de deletar questão informando id inexistente",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Questao Com ID Nulo (Espera Erro)")
    public void testQuestoes_deletarQuestaoComIdNulo_esperaErro() {

        ErrorDto erro = QuestaoClient.excluirQuestaoSemParam()
            .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        assertAll("Testes de deletar questão não informando id",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Questao Com ID invalido (Espera Erro)")
    public void testQuestoes_deletarQuestaoComIdInvalido_esperaErro() {

        ErrorDto erro = QuestaoClient.excluirQuestaoComIdInvalido()
            .then()
                .statusCode(400)
                .log().all()
                .extract().as(ErrorDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Testes Quando Deleta Questao (Espera Erro)")
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

        ErrorDto erro = QuestaoClient.excluirTeste(testes.get(0).getTesteId())
            .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @DisplayName("[CTAXX] Testes - Deletar Testes Até Limite Mínimo (Espera Erro)")
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

        ErrorDto erro = QuestaoClient.excluirTeste(testes.get(0).getTesteId())
            .then()
                .statusCode(400)
                .log().all()
                .extract().as(ErrorDto.class);

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
    @DisplayName("[CTAXX] Testes - Deletar Testes Com Permissao De Aluno (Espera Erro)")
    public void testTestes_deletarTestesSemPermissao_esperaErro() {
        ErrorDto erro = QuestaoClient.excluirTesteSemPermissao(123)
            .then()
                .statusCode(403)
                .extract().as(ErrorDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 403, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @DisplayName("[CTAXX] Testes - Deletar Testes Com ID Inválido (Espera Erro)")
    public void testTestes_deletarTestesComIDInvalido_esperaErro() {
        ErrorDto erro = QuestaoClient.excluirTesteComIDInvalido()
            .then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }

    @Test
    @DisplayName("[CTAXX] Testes - Deletar Testes Com ID Inexistente (Espera Erro)")
    public void testTestes_deletarTestesComIDInexistente_esperaErro() {
        ErrorDto erro = QuestaoClient.excluirTesteComIDInexistente()
            .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        assertAll("Testes de deletar questão informando id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("Colocar Parametros"), "")
        );
    }
}
