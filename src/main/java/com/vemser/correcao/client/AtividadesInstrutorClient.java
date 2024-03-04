package com.vemser.correcao.client;

import com.vemser.correcao.dto.atividade.CriarAtividadeDto;
import com.vemser.correcao.enums.QuestoesParametro;
import com.vemser.correcao.specs.AtividadesSpecs;
import com.vemser.correcao.specs.LoginSpecs;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AtividadesInstrutorClient {

    private static final String DELETAR_ATIVIDADE_POR_ID = "/atividades/deletar/{id}";
    private static final String LISTAR_TODAS_ATIVIDADES = "/atividades/listar-atividades-instrutor";
    private static final String CRIAR_ATIVIDADE = "/atividades/criar";
    private static final String CORRIGIR_ATIVIDADE = "/atividades/retorno-do-professor";
    private static final String EDITAR_ATIVIDADE = "/atividades/editar/{idAtividade}";
    private static final String EDITAR_ATIVIDADE_SEM_ID = "/atividades/editar/";
    private static final String BUSCAR_ATIVIDADE_POR_ID = "/atividades/listar-atividades-estagiario/{id}";

    public AtividadesInstrutorClient(){}

    public static Response criarAtividade(CriarAtividadeDto atividade) {
        return given()
                    .spec(AtividadesSpecs.atividadeInstrutorSpec())
                    .body(atividade)
                .when()
                .log().all()
                    .post(CRIAR_ATIVIDADE);
    }

    public static Response criarAtividadeString(String atividade) {
        return given()
                .spec(AtividadesSpecs.atividadeInstrutorSpec())
                .body(atividade)
                .when()
                .post(CRIAR_ATIVIDADE);
    }

    public static Response excluirAtividade(Integer atividadeId) {
        return given()
                .spec(AtividadesSpecs.atividadeInstrutorSpec())
                .pathParam("id", atividadeId)
                .when()
                .delete(DELETAR_ATIVIDADE_POR_ID);
    }

    public static Response excluirAtividadeString(String atividadeId) {
        return given()
                .spec(AtividadesSpecs.atividadeInstrutorSpec())
                .pathParam("id", atividadeId)
                .when()
                .delete(DELETAR_ATIVIDADE_POR_ID);
    }

    public static Response editarAtividade(Integer atividadeId, CriarAtividadeDto atividadeEditada) {
        return given()
                .spec(AtividadesSpecs.atividadeInstrutorSpec())
                .pathParam("idAtividade", atividadeId)
                .body(atividadeEditada)
            .when()
                .put(EDITAR_ATIVIDADE);
    }

    public static Response editarAtividadeSemId(CriarAtividadeDto atividadeEditada) {
        return given()
                .spec(AtividadesSpecs.atividadeInstrutorSpec())
                .body(atividadeEditada)
                .when()
                .put(EDITAR_ATIVIDADE_SEM_ID);
    }

    public static Response editarAtividadeComoString(Integer atividadeId, String atividadeEditada) {
        return given()
                .spec(AtividadesSpecs.atividadeInstrutorSpec())
                .pathParam("idAtividade", atividadeId)
                .body(atividadeEditada)
                .when()
                .put(EDITAR_ATIVIDADE);
    }

    public static Response editarAtividadeSemAutenticacao(Integer atividadeId, CriarAtividadeDto atividadeEditada) {
        return given()
                .spec(LoginSpecs.reqSemTokenSpec())
                .pathParam("idAtividade", atividadeId)
                .body(atividadeEditada)
                .when()
                .put(EDITAR_ATIVIDADE);
    }

    public static Response listarAtividades(String page, String size) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("page", page);
        parametrosMap.put("size", size);

        return given()
                .spec(LoginSpecs.loginInstrutorReqSpec())
                .queryParams(parametrosMap)
        .when()
                .get(LISTAR_TODAS_ATIVIDADES);
    }

    public static Response listarAtividades(QuestoesParametro parametro, String valor) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put(parametro.toString(), valor);

        return given()
                .spec(LoginSpecs.loginInstrutorReqSpec())
                .queryParams(parametrosMap)
                .when()
                .get(LISTAR_TODAS_ATIVIDADES);
    }

    public static Response listarAtividades() {
        return given()
                .spec(LoginSpecs.loginInstrutorReqSpec())
        .when()
                .get(LISTAR_TODAS_ATIVIDADES);
    }

    public static Response listarAtividadesSemAutenticacao() {
        return given()
                .spec(LoginSpecs.reqSemTokenSpec())
                .when()
                .get(LISTAR_TODAS_ATIVIDADES);
    }

    public static Response listarAtividadesComoAluno() {
        return given()
                .spec(LoginSpecs.loginAlunoReqSpec())
                .when()
                .get(LISTAR_TODAS_ATIVIDADES);
    }

    public static Response buscarAtividadePorID(String id) {
        return given()
                .spec(LoginSpecs.loginInstrutorReqSpec())
                .pathParam("id", id)
        .when()
                .get(BUSCAR_ATIVIDADE_POR_ID);
    }

    public static Response buscarAtividadePorIDSemAutenticacao(String id) {
        return given()
                .spec(LoginSpecs.reqSemTokenSpec())
                .pathParam("id", id)
        .when()
                .get(BUSCAR_ATIVIDADE_POR_ID);
    }

    public static Response buscarAtividadePorComoAluno(String id) {
        return given()
                .spec(LoginSpecs.loginAlunoReqSpec())
                .pathParam("id", id)
        .when()
                .get(BUSCAR_ATIVIDADE_POR_ID);
    }
}
