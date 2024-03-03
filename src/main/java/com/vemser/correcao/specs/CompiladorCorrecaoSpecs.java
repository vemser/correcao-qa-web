package com.vemser.correcao.specs;

import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.correcao.utils.Auth;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CompiladorCorrecaoSpecs {
    public static RequestSpecification compiladorCorrecaoReqAuthInstrutor(CompiladorDto compiladorDto) {
        String token = Auth.obterTokenInstrutor();

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON)
                .setBody(compiladorDto)
                .log(LogDetail.BODY)
                .build();
    }

    public static RequestSpecification compiladorCorrecaoReqAuthInstrutor(String compiladorJson) {
        String token = Auth.obterTokenInstrutor();

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON)
                .setBody(compiladorJson)
                .log(LogDetail.BODY)
                .build();
    }

    public static ResponseSpecification compiladorCorrecaoSucessoResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectBody(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/compiladorSucesso.json"))
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();
    }

    public static ResponseSpecification compiladorCorrecaoErroResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectBody(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/compiladorErro.json"))
                .expectStatusCode(400)
                .log(LogDetail.BODY)
                .build();
    }
}
