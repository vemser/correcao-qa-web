package com.vemser.correcao.client;

import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.enums.QuestoesParametro;
import com.vemser.correcao.specs.AtividadesSpecs;
import com.vemser.correcao.specs.LoginSpecs;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AtividadesEnviadaClient {

    private static final String LISTAR_TODAS_ATIVIDADES_DO_ALUNO = "/atividades-enviadas/listar-todos-do-aluno-logado";

    public AtividadesEnviadaClient(){}

    public static Response listarAtividadesDoAluno() {
        return given()
                    .spec(AtividadesSpecs.atividadeAlunoSpec())
                .when()
                    .get(LISTAR_TODAS_ATIVIDADES_DO_ALUNO);
    }

    public static Response listarAtividadesDoAluno(QuestoesParametro parametro, String value) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put(String.valueOf(parametro), value);

        return
                given()
                    .spec(AtividadesSpecs.atividadeAlunoSpec())
                    .queryParams(parametrosMap)
                .when()
                    .get(LISTAR_TODAS_ATIVIDADES_DO_ALUNO);
    }

    public static Response listarAtividadesDoAluno(String page, String size) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("page", page);
        parametrosMap.put("size", size);

        return
                given()
                        .spec(AtividadesSpecs.atividadeAlunoSpec())
                        .queryParams(parametrosMap)
                        .when()
                        .get(LISTAR_TODAS_ATIVIDADES_DO_ALUNO);
    }

    public static Response listarAtividadesDoAlunoSemEstarLogado(String page, String size) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("page", page);
        parametrosMap.put("size", size);

        return given()
                .spec(LoginSpecs.reqSemTokenSpec())
                .queryParams(parametrosMap)
                .when()
                .get(LISTAR_TODAS_ATIVIDADES_DO_ALUNO);
    }

    public static Response listarAtividadesDoAlunoComoInstrutor(String page, String size) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("page", page);
        parametrosMap.put("size", size);

        return given()
                .spec(LoginSpecs.loginInstrutorReqSpec())
                .queryParams(parametrosMap)
                .when()
                .get(LISTAR_TODAS_ATIVIDADES_DO_ALUNO);
    }

}
