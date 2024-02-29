package com.vemser.compilador.client;

import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.specs.CompiladorSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CompiladorClient {
    public static Response compilarCodigo(CompiladorDto compiladorDto) {
        return given()
                .spec(CompiladorSpecs.compiladorRequest(compiladorDto))
        .when()
                .post("/compilador");
    }

    public static Response compilarCodigo(String compiladorJson) {
        return given()
                .spec(CompiladorSpecs.compiladorRequest(compiladorJson))
                .when()
                .post("/compilador");
    }
}
