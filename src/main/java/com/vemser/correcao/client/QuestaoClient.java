package com.vemser.correcao.client;

import com.vemser.correcao.dto.questao.EditarQuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.enums.QuestoesParametro;
import com.vemser.correcao.specs.LoginSpecs;
import com.vemser.correcao.specs.QuestaoSpecs;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class QuestaoClient {

    private static final String LISTAR_QUESTAO_URL = "/questao/listar-todas-questoes";
    private static final String CADASTRAR_QUESTAO = "/questao/criar-questao-com-testes";
    private static final String DELETAR_QUESTAO = "/questao/deletar-questao/{idQuestao}";
    private static final String DELETAR_QUESTAO_SEM_PARAM = "/questao/deletar-questao/";
    private static final String GET_QUESTAO_ID = "/questao/questao-com-testes-publico/{idQuestao}";
    private static final String DELETE_TESTE_ID = "/questao/deletar-teste/{idTeste}";
    public static final String EDITAR_QUESTAO_ID = "/questao/editar-questao/{idQuestao}";
    public static final String EDITAR_QUESTAO_SEM_PARAM = "/questao/editar-questao/";

    public QuestaoClient() {
    }

    public static Response cadastrarQuestao(QuestaoDto questao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .body(questao)
        .when()
                .post(CADASTRAR_QUESTAO);
    }

    public static Response buscarTodasQuestao(HashMap<String, String> queryParams) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .queryParams(queryParams)
        .when()
                .get(LISTAR_QUESTAO_URL);
    }

    public static Response cadastrarQuestao(String questao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .body(questao)
        .when()
                .post(CADASTRAR_QUESTAO);
    }

    public static Response cadastrarQuestaoPorLogin(QuestaoDto questao, String token) {
        return given()
                .spec(QuestaoSpecs.questaoPorLogin(token))
                .body(questao)
        .when()
                .post(CADASTRAR_QUESTAO);
    }

    public static Response buscarTodasQuestao(String paginaSolicitada, String tamanhoPagina) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("page", paginaSolicitada);
        parametrosMap.put("size", tamanhoPagina);

        return
                given()
                    .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                    .queryParams(parametrosMap)
        .when()
                    .get(LISTAR_QUESTAO_URL);
    }

    public static Response buscarTodasQuestao(QuestoesParametro parametro, String value) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put(String.valueOf(parametro), value);

        return
                given()
                        .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                        .queryParams(parametrosMap)
                .when()
                        .get(LISTAR_QUESTAO_URL);
    }

    public static Response buscarTodasQuestao() {
        return
                given()
                        .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .when()
                        .get(LISTAR_QUESTAO_URL);
    }

    public static Response buscarTodasQuestaoSemEstarLogado(String paginaSolicitada, String tamanhoPagina) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("page", paginaSolicitada);
        parametrosMap.put("size", tamanhoPagina);

        return
                given()
                    .spec(LoginSpecs.reqSemTokenSpec())
                    .queryParams(parametrosMap)
                .when()
                    .get(LISTAR_QUESTAO_URL);
    }

    public static Response buscarTodasQuestaoLogadoComoAluno(String paginaSolicitada, String tamanhoPagina) {
        Map<String, String> parametrosMap = new HashMap<>();
        parametrosMap.put("page", paginaSolicitada);
        parametrosMap.put("size", tamanhoPagina);

        return
                given()
                        .spec(QuestaoSpecs.questaoReqAuthAlunoSpec())
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
    public static Response excluirQuestaoSemParam() {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
        .when()
                .delete(DELETAR_QUESTAO_SEM_PARAM);
    }
    public static Response excluirQuestaoComIdInvalido() {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", "idQuestaoInvalido")
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
                .spec(LoginSpecs.reqSemTokenSpec())
                .pathParam("idQuestao", idQuestao)
        .when()
                .delete(DELETAR_QUESTAO);
    }
    public static Response buscarQuestaoPorId(Integer idQuestao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", idQuestao)
        .when()
                .get(GET_QUESTAO_ID);
    }
    public static Response buscarQuestaoPorIdInexistente() {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", 9999999)
                .when()
                .get(GET_QUESTAO_ID);
    }
    public static Response buscarQuestaoPorIdMaiorQueOLimite() {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", "99993333999")
                .when()
                .get(GET_QUESTAO_ID);
    }
    public static Response excluirTeste(Integer idTeste){
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idTeste", idTeste)
        .when()
                .delete(DELETE_TESTE_ID);
    }
    public static Response excluirTesteSemPermissao(Integer idTeste){
        return given()
                .spec(QuestaoSpecs.questaoReqAuthAlunoSpec())
                .pathParam("idTeste", idTeste)
        .when()
                .delete(DELETE_TESTE_ID);
    }
    public static Response excluirTesteComIDInvalido(){
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idTeste", "idInvalido")
        .when()
                .delete(DELETE_TESTE_ID);
    }
    public static Response excluirTesteComIDInexistente(){
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idTeste", 9999999)
        .when()
                .delete(DELETE_TESTE_ID);
    }

    public static Response editarQuestao(EditarQuestaoDto questaoEditada, Integer questaoId){
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", questaoId)
                .body(questaoEditada)
            .when()
                .put(EDITAR_QUESTAO_ID);
    }

    public static Response editarQuestao(String questaoEditada, Integer questaoId){
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", questaoId)
                .body(questaoEditada)
                .when()
                .put(EDITAR_QUESTAO_ID);
    }

    public static Response editarQuestaoSemAutenticacao(EditarQuestaoDto questaoEditada, Integer questaoId){
        return given()
                .spec(LoginSpecs.reqSemTokenSpec())
                .pathParam("idQuestao", questaoId)
                .body(questaoEditada)
                .when()
                .put(EDITAR_QUESTAO_ID);
    }
}
