package com.vemser.correcao.client;

import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.specs.AtividadesSpecs;
import com.vemser.correcao.specs.QuestaoSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AtividadesInstrutorClient {

    private static final String DELETAR_ATIVIDADE_POR_ID = "/atividades/{id}/deletar/{ìd}";
    private static final String LISTAR_TODAS_ATIVIDADES = "/atividades";
    private static final String CRIAR_ATIVIDADE = "/atividades/criar";
    private static final String CORRIGIR_ATIVIDADE = "/atividades/retorno-do-professor";
    private static final String EDITAR_ATIVIDADE = "/atividades/editar";

    public AtividadesInstrutorClient(){}

    public static Response criarAtividade(CriarAtividadeDto atividade) {
        return given()
                .spec(AtividadesSpecs.atividadeInstrutorSpec())
                .body(atividade)
                .when()
                .post(CRIAR_ATIVIDADE);
    }
}
