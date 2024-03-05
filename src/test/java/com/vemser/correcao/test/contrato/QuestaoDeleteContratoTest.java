package com.vemser.correcao.test.contrato;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.erro.ErroDto;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoResponseDto;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Epic("Contrato Questões - DELETE")
@DisplayName("Questões - DELETE")
@Owner("Gabriel Sales")
public class QuestaoDeleteContratoTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA010] Deletar Questão Ao Informar ID Existente Para Validar Corpo Da Resposta De Sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao deletar uma questão existente por ID a API retorna corpo válido da resposta de sucesso")
    public void testDeletarQuestao_informarIDExistenteParaValidarCorpoDaRespostaDeSucesso_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);
        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .extract().asString();
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA011] Deletar Questão Ao Informar ID Existente Para Validar Corpo Da Resposta De Sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao deletar uma questão existente por ID a API retorna corpo válido da resposta de sucesso")
    public void testDeletarQuestao_informarIDExistenteValidarCorpoDaRespostaDeErro_esperaSucesso() {
        QuestaoClient.excluirQuestao(999999999)
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/erro.json"))
                .extract().as(ErroDto.class);
    }
}
