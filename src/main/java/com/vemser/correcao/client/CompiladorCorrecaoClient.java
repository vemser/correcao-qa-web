package com.vemser.correcao.client;

import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.correcao.specs.CompiladorCorrecaoSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CompiladorCorrecaoClient {
    public static Response compilarCodigo(CompiladorDto compiladorDto) {
        return given()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoReqAuthInstrutor(compiladorDto))
                .when()
                .post("/compilador");
    }

    public static Response compilarCodigo(String compiladorJson) {
        return given()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoReqAuthInstrutor(compiladorJson))
                .when()
                .post("/compilador");
    }
}
