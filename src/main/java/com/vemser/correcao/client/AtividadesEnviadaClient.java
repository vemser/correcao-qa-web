package com.vemser.correcao.client;

import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.specs.AtividadesSpecs;
import io.restassured.response.Response;

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

}
