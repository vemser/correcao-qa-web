package com.vemser.correcao.client;

import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.specs.QuestaoSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class QuestaoClient {

    private static final String CADASTRAR_QUESTAO = "/questao/criar-questao-com-testes";
    private static final String DELETAR_QUESTAO = "/questao/deletar-questao/{idQuestao}";

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
}
