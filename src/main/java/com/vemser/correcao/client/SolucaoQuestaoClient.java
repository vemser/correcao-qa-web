package com.vemser.correcao.client;

import com.vemser.correcao.dto.LoginDto;
import com.vemser.correcao.dto.SolucaoQuestaoDto;
import com.vemser.correcao.specs.InicialSpecs;
import com.vemser.correcao.specs.SolucaoQuestaoSpecs;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SolucaoQuestaoClient {
    private static final String SOLUCAO_QUESTAO = "/solucao-atividade-enviada/solucao-questao";
    private static final String SOLUCAO_QUESTAO_TESTAR = "/solucao-atividade-enviada/solucao-questao-testar";

    public static Response testarSolucaoCompleta(SolucaoQuestaoDto solucaoQuestaoDto) {
        return given()
                .spec(SolucaoQuestaoSpecs.solucaoQuestaoSpec())
                .contentType(ContentType.JSON)
                .body(solucaoQuestaoDto)
        .when()
                .post(SOLUCAO_QUESTAO);
    }
    public static Response testarSolucaoExemplos(SolucaoQuestaoDto solucaoQuestaoDto) {
        return given()
                .spec(SolucaoQuestaoSpecs.solucaoQuestaoSpec())
                .contentType(ContentType.JSON)
                .body(solucaoQuestaoDto)
        .when()
                .post(SOLUCAO_QUESTAO_TESTAR);
    }
}
