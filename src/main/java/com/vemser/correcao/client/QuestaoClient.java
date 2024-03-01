package com.vemser.correcao.client;

import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.specs.QuestaoSpecs;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class QuestaoClient {

    private static final String QUESTAO = "/questao/criar-questao-com-testes";
    private static final String LISTAR_QUESTAO_URL = "/questao/listar-todas-questoes";

    public QuestaoClient() {
    }

    public static Response cadastrarQuestao(QuestaoDto questao) {
        return given()
                .spec(QuestaoSpecs.questaoReqSpec())
                .body(questao)
                .when()
                .post(QUESTAO);
    }

    public static Response buscarTodasQuestao() {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("paginaSolicitada", "0");
        parametrosMap.put("tamanhoPagina", "10");

        return
                given()
                    .spec(QuestaoSpecs.questaoReqSpec())
                    .queryParams(parametrosMap)
                        .log().all()
                .when()
                    .get(LISTAR_QUESTAO_URL);
    }
}
