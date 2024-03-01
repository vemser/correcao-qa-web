package com.vemser.correcao.specs;

import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.correcao.utils.Manipulation;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class QuestaoSpecs {
    public static RequestSpecification questaoReqSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", Manipulation.getProps().getProperty("TokenInstrutor"))
                .build();
    }
}
