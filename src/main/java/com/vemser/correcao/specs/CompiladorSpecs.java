package com.vemser.correcao.specs;

import com.vemser.correcao.dto.CompiladorDto;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CompiladorSpecs {
    public static RequestSpecification compiladorRequest(CompiladorDto compiladorDto) {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setUp())
                .setContentType(ContentType.JSON)
                .setBody(compiladorDto)
                .log(LogDetail.BODY)
                .build();
    }

    public static RequestSpecification compiladorRequest(String compiladorJson) {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setUp())
                .setContentType(ContentType.JSON)
                .setBody(compiladorJson)
                .log(LogDetail.BODY)
                .build();
    }

    public static ResponseSpecification compiladorSucessoResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectBody(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/compiladorSucesso.json"))
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();
    }

    public static ResponseSpecification compiladorErroResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectBody(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/compiladorErro.json"))
                .expectStatusCode(400)
                .log(LogDetail.BODY)
                .build();
    }
}
