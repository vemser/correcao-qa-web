package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.dto.TesteResponseDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoDeleteFuncionalTest {
    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Questao Existente Com Sucesso (Espera Sucesso)")
    public void testQuestoes_deletarQuestaoExistente_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria(2);

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
    @DisplayName("[CTAXX] Questoes - Deletar Questao Existente Com Permissao De Aluno (Espera Falha)")
    public void testQuestoes_deletarQuestaoExistenteComoAluno_esperaFalha() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String response = QuestaoClient.excluirQuestaoSemPermissao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(403)
                .extract().asString();

        assertTrue(response.contains("Forbidden"));

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Questao Existente Sem Token (Espera Falha)")
    public void testQuestoes_deletarQuestaoExistenteSemToken_esperaFalha() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String response = QuestaoClient.excluirQuestaoSemToken(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(403)
                .extract().asString();

        assertTrue(response.contains("Forbidden"));

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Questao Já Deletada (Espera Falha)")
    public void testQuestoes_deletarQuestaoJaDeletada_esperaFalha() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200)
                .extract().asString();

        String response = QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(404)
                .extract().asString();

        assertEquals("Falha ao deletar a questão: Questão inativa.", response, "Mensagem de falha deve ser igual");
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Questao Com ID inexistente (Espera Falha)")
    public void testQuestoes_deletarQuestaoComIdInexistente_esperaFalha() {

        String response = QuestaoClient.excluirQuestao(999999999)
            .then()
                .statusCode(404)
                .extract().asString();

        assertEquals("Falha ao deletar a questão: Questão não encontrada com o ID fornecido", response, "Mensagem de falha deve ser igual");
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Questao Com ID Nulo (Espera Falha)")
    public void testQuestoes_deletarQuestaoComIdNulo_esperaFalha() {

        String response = QuestaoClient.excluirQuestaoSemParam()
            .then()
                .statusCode(404)
                .extract().asString();
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Questao Com ID invalido (Espera Falha)")
    public void testQuestoes_deletarQuestaoComIdInvalido_esperaFalha() {

        String response = QuestaoClient.excluirQuestaoComIdInvalido()
            .then()
                .statusCode(400)
                .extract().asString();
    }

    @Test
    @DisplayName("[CTAXX] Questoes - Deletar Testes Quando Deleta Questao (Espera Falha)")
    public void testQuestoes_deletarQuestaoExistenteComTestes_esperaFalha() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria(2);

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

        String response = QuestaoClient.excluirTeste(testes.get(0).getTesteId())
            .then()
                .statusCode(200)
                .extract().asString();

        assertEquals("Falha ao deletar a teste: Teste não encontrado com o ID fornecido", response, "Mensagem de falha deve ser igual ao esperado");
    }

    @Test
    @DisplayName("[CTAXX] Testes - Deletar Testes Com ID Válido (Espera Sucesso)")
    public void testTestes_deletarTesteExistente_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria(2);

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
    @DisplayName("[CTAXX] Testes - Deletar Testes Até Limite Mínimo (Espera Falha)")
    public void testTestes_deletarTestesLimiteMinimo_esperaFalha() {
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

        String response = QuestaoClient.excluirTeste(testes.get(0).getTesteId())
            .then()
                .statusCode(400)
                .extract().asString();

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);

        assertTrue(response.contains("Falha ao deletar a teste"));
    }
    @Test
    @DisplayName("[CTAXX] Testes - Deletar Testes Com Sucesso (Espera Sucesso)")
    public void testTestes_deletarTestesComSucesso_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
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
    @DisplayName("[CTAXX] Testes - Deletar Testes Com Permissao De Aluno (Espera Falha)")
    public void testTestes_deletarTestesSemPermissao_esperaFalha() {
        String response = QuestaoClient.excluirTesteSemPermissao(123)
            .then()
                .statusCode(403)
                .extract().asString();
        assertTrue(response.contains("Forbidden"));
    }

    @Test
    @DisplayName("[CTAXX] Testes - Deletar Testes Com ID Inválido (Espera Falha)")
    public void testTestes_deletarTestesComIDInvalido_esperaFalha() {
        String response = QuestaoClient.excluirTesteComIDInvalido()
            .then()
                .statusCode(400)
                .extract().asString();
    }
    @Test
    @DisplayName("[CTAXX] Testes - Deletar Testes Com ID Inexistente (Espera Falha)")
    public void testTestes_deletarTestesComIDInexistente_esperaFalha() {
        String response = QuestaoClient.excluirTesteComIDInexistente()
            .then()
                .statusCode(404)
                .extract().asString();

        assertEquals("Falha ao deletar a teste: Teste não encontrado com o ID fornecido", response, "Mensagem de falha deve ser igual ao esperado");
    }
}
