package com.vemser.correcao.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public class InicialSpecs {
    public static RequestSpecification setUp() {
        return new RequestSpecBuilder()
                .setBaseUri("https://compilador-vemserdbc.koyeb.app")
                .build();
    }
}
