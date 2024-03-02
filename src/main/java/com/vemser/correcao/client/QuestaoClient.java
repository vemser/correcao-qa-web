package com.vemser.correcao.client;

import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.specs.QuestaoSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class QuestaoClient {

    private static final String CADASTRAR_QUESTAO = "/questao/criar-questao-com-testes";
    private static final String DELETAR_QUESTAO = "/questao/deletar-questao/{idQuestao}";
    private static final String DELETAR_QUESTAO_SEM_PARAM = "/questao/deletar-questao/";
    private static final String GET_QUESTAO_ID = "/questao/questao-com-testes-publico/{idQuestao}";
    private static final String DELETE_TESTE_ID = "/questao/deletar-teste/{idTeste}";

    public QuestaoClient() {
    }

    public static Response cadastrarQuestao(QuestaoDto questao) {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .body(questao)
        .when()
                .post(CADASTRAR_QUESTAO);
    }

    public static Response cadastrarQuestao(String questao) {
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
    public static Response excluirQuestaoSemParam() {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
        .when()
                .delete(DELETAR_QUESTAO_SEM_PARAM);
    }
    public static Response excluirQuestaoComIdInvalido() {
        return given()
                .spec(QuestaoSpecs.questaoReqAuthInstrutorSpec())
                .pathParam("idQuestao", "idInvalido")
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
                .spec(QuestaoSpecs.questaoReqSemTokenSpec())
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
}
