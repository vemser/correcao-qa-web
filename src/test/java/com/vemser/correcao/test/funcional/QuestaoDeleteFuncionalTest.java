package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoDeleteFuncionalTest {
    @Test
    @DisplayName("Questoes - Deletar Questao Existente Com Sucesso (Espera Sucesso)")
    public void testQuestoes_deletarQuestaoExistente_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria();

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
    @DisplayName("Questoes - Deletar Questao Existente Com Permissao De Aluno (Espera Falha)")
    public void testQuestoes_deletarQuestaoExistenteComoAluno_esperaFalha() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria();

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
    @DisplayName("Questoes - Deletar Questao Existente Sem Token (Espera Falha)")
    public void testQuestoes_deletarQuestaoExistenteSemToken_esperaFalha() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria();

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
    @DisplayName("Questoes - Deletar Questao Já Deletada (Espera Falha)")
    public void testQuestoes_deletarQuestaoJaDeletada_esperaFalha() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria();

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
    @DisplayName("Questoes - Deletar Questao Com ID inexistente (Espera Falha)")
    public void testQuestoes_deletarQuestaoComIdInexistente_esperaFalha() {

        String response = QuestaoClient.excluirQuestao(999999999)
            .then()
                .statusCode(404)
                .extract().asString();

        assertEquals("Falha ao deletar a questão: Questão não encontrada com o ID fornecido", response, "Mensagem de falha deve ser igual");
    }

    @Test
    @DisplayName("Questoes - Deletar Questao Com ID Nulo (Espera Falha)")
    public void testQuestoes_deletarQuestaoComIdNulo_esperaFalha() {

        String response = QuestaoClient.excluirQuestaoSemParam()
            .then()
                .statusCode(404)
                .extract().asString();
    }

    @Test
    @DisplayName("Questoes - Deletar Questao Com ID invalido (Espera Falha)")
    public void testQuestoes_deletarQuestaoComIdInvalido_esperaFalha() {

        String response = QuestaoClient.excluirQuestaoComIdInvalido()
            .then()
                .statusCode(400)
                .extract().asString();
    }

    @Test
    @DisplayName("Questoes - Deletar Testes Quando Deleta Questao (Espera Sucesso)")
    public void testQuestoes_deletarQuestaoExistenteComTestes_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.novaQuestaoAleatoria();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String response = QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200)
                .extract().asString();
        assertEquals("Questão deletada com sucesso!", response, "Mensagem de sucesso deve ser igual");
        //TODO: Verificar se os testes foram deletados usando endpoint de deletar testes ->caso delete, falhou

    }

}
