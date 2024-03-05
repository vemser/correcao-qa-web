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
//                .addHeader("Authorization", token)
                .addHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imluc3RydXRvci50ZXN0ZSIsImp0aSI6IjUiLCJjYXJnb3MiOlsiUk9MRV9JTlNUUlVUT1IiXSwiaWF0IjoxNzA5NTc3OTEzLCJleHAiOjE3MTIxNjk5MDh9.ZodW-s7MHGyTcVyaA6VOqxDEsaYSS7eB7BYQzIu20KI")
                .setContentType(ContentType.JSON)
                .setBody(compiladorDto)
                .log(LogDetail.BODY)
                .build();
    }

    public static RequestSpecification compiladorCorrecaoReqAuthInstrutor(String compiladorJson) {
        String token = Auth.obterTokenInstrutor();

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
//                .addHeader("Authorization", token)
                .addHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imluc3RydXRvci50ZXN0ZSIsImp0aSI6IjUiLCJjYXJnb3MiOlsiUk9MRV9JTlNUUlVUT1IiXSwiaWF0IjoxNzA5NTc3OTEzLCJleHAiOjE3MTIxNjk5MDh9.ZodW-s7MHGyTcVyaA6VOqxDEsaYSS7eB7BYQzIu20KI")
                .setContentType(ContentType.JSON)
                .setBody(compiladorJson)
                .log(LogDetail.BODY)
                .build();
    }
}
