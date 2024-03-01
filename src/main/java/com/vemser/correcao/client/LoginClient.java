package com.vemser.correcao.client;

import com.vemser.correcao.dto.LoginDto;
import com.vemser.correcao.specs.InicialSpecs;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginClient {
    private static final String AUTENTICACAO = "/auth/login";

    public static Response autenticar(LoginDto login) {
        return given()
                .spec(InicialSpecs.setupApi())
                .contentType(ContentType.JSON)
                .body(login)
        .when()
                .post(AUTENTICACAO);
    }
}
