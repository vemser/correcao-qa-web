package com.vemser.compilador.specs;

import com.vemser.compilador.dto.CompiladorDto;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CompiladorSpecs {
    public static RequestSpecification compiladorRequest() {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupCompilador())
                .setContentType(ContentType.JSON)
                .build();
    }
}
