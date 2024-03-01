package com.vemser.correcao.client;

import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.specs.QuestaoSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class QuestaoClient {

    private static final String CADASTRAR_QUESTAO = "/questao/criar-questao-com-testes";
    private static final String DELETAR_QUESTAO = "/questao/deletar-questao/{idQuestao}";
    private static final String DELETAR_QUESTAO_SEM_PARAM = "/questao/deletar-questao/";


    public QuestaoClient() {
    }

    public static Response cadastrarQuestao(QuestaoDto questao) {
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
}
