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
                    .spec(QuestaoSpecs.questaoReqSpec())
                    .body(questao)
                .when()
                    .post(QUESTAO);
    }

}
