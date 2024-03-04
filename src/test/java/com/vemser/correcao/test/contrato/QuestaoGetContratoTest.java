package com.vemser.correcao.test.contrato;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.erro.ErroDto;
import com.vemser.correcao.dto.questao.ListaTodasQuestaoResponseDto;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoResponseDto;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Contrato Questões - GET")
@DisplayName("Questões - GET")
@Owner("Gabriel Sales")
public class QuestaoGetContratoTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Questões Ao Informar Página e Tamanho Válidos Para Validar Corpo Da Resposta De Sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões informando página e tamanho válido retorna corpo válido da resposta de sucesso")
    public void testListarQuestoes_informarPaginaETamanhoValidosParaValidarCorpoDaRespostaDeSucesso_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao("0", "10")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/questao-get-listar-todos-sucesso.json"))
                .extract()
                .as(ListaTodasQuestaoResponseDto.class);
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar Questões Ao Informar Página Que Não Existe Para Validar Corpo Da Resposta De Erro")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões passando uma página que não existe retorna corpo válido de resposta de erro")
    public void testListarQuestoes_informarPaginaQueNaoExisteParaValidarCorpoDaRespostaDeErro_esperaErro() {
        ListaTodasQuestaoResponseDto questao = QuestaoClient.buscarTodasQuestao("0", "10")
                .as(ListaTodasQuestaoResponseDto.class);

        String paginaSolicitada = Integer.toString(questao.getTotalPages() + 1);

        QuestaoClient.buscarTodasQuestao(paginaSolicitada, "10")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/erro.json"))
                .extract()
                .as(ErroDto.class);
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Buscar Questão Por ID Ao Informar ID Existente Para Validar Corpo Da Resposta De Sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID Existente retorna corpo válido da resposta de sucesso")
    public void testBuscarQuestaoPorID_informarIDExistenteParaValidarCorpoDaRespostaDeSucesso_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoCriada = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/questao-get-por-id-sucesso.json"))
                .log().all()
                .extract().as(QuestaoResponseDto.class);
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Buscar Questão Por ID Ao Informar ID Inexistente Para Validar Corpo Da Resposta De Erro")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID inexistente retorna corpo válido de resposta de erro")
    public void testBuscarQuestaoPorID_informarIDInexistenteParaValidarCorpoDaRespostaDeErro_esperaErro() {
        ErroDto erro = QuestaoClient.buscarQuestaoPorIdInexistente()
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/erro.json"))
                .extract().as(ErroDto.class);
    }
}
