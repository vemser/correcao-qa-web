package com.vemser.correcao.client;

import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.specs.QuestaoSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class QuestaoClient {

    private static final String QUESTAO = "/questao/criar-questao-com-testes";

    public QuestaoClient() {
    }

    public static Response cadastrarQuestao(QuestaoDto questao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .body(questao)
<<<<<<< Updated upstream
=======
        .when()
                .post(CADASTRAR_QUESTAO);
    }

    public static Response cadastrarQuestaoPorLogin(QuestaoDto questao, String token) {
        return given()
                .spec(QuestaoSpecs.questaoPorLogin(token))
                .body(questao)
        .when()
                .post(CADASTRAR_QUESTAO);
    }

    public static Response buscarTodasQuestao(String paginaSolicitada, String tamanhoPagina) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("paginaSolicitada", paginaSolicitada);
        parametrosMap.put("tamanhoPagina", tamanhoPagina);

        return
                given()
                    .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                    .queryParams(parametrosMap)
        .when()
                    .get(LISTAR_QUESTAO_URL);
    }

    public static Response buscarTodasQuestao(HashMap<String, String> queryParams) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .queryParams(queryParams)
        .when()
                .get(LISTAR_QUESTAO_URL);
    }

    public static Response cadastrarQuestao(String questao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .body(questao)
        .when()
                .post(CADASTRAR_QUESTAO);
    }

    public static Response excluirQuestao(Integer idQuestao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", idQuestao)
        .when()
                .delete(DELETAR_QUESTAO);
    }
    public static Response excluirQuestaoSemParam() {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
        .when()
                .delete(DELETAR_QUESTAO_SEM_PARAM);
    }
    public static Response excluirQuestaoComIdInvalido() {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", "idInvalido")
        .when()
                .delete(DELETAR_QUESTAO);
    }
    public static Response excluirQuestaoSemPermissao(Integer idQuestao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthAlunoSpec())
                .pathParam("idQuestao", idQuestao)
        .when()
                .delete(DELETAR_QUESTAO);
    }
    public static Response excluirQuestaoSemToken(Integer idQuestao) {
        return given()
                .spec(QuestaoSpecs.questaoReqSemTokenSpec())
                .pathParam("idQuestao", idQuestao)
        .when()
                .delete(DELETAR_QUESTAO);
    }
    public static Response buscarQuestaoPorId(Integer idQuestao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", idQuestao)
        .when()
                .get(GET_QUESTAO_ID);
    }
    public static Response buscarQuestaoPorIdInexistente() {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", 9999999)
>>>>>>> Stashed changes
                .when()
                .post(QUESTAO);
    }

}
