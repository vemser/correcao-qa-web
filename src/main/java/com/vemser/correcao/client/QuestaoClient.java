package com.vemser.correcao.client;

import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.specs.QuestaoSpecs;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class QuestaoClient {

    private static final String LISTAR_QUESTAO_URL = "/questao/listar-todas-questoes";
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

    public static Response buscarTodasQuestao() {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("paginaSolicitada", "0");
        parametrosMap.put("tamanhoPagina", "10");

        return
                given()
                    .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                    .queryParams(parametrosMap)
                .when()
                    .get(LISTAR_QUESTAO_URL);
    }

    public static Response excluirQuestao(Integer idQuestao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", idQuestao)
        .when()
                .delete(DELETAR_QUESTAO);
    }
}
