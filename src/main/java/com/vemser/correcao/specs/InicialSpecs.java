package com.vemser.correcao.specs;

import com.vemser.correcao.utils.Manipulation;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;
import static io.restassured.config.LogConfig.logConfig;

public class InicialSpecs {

    private InicialSpecs() {}
    public static RequestSpecification setupApi() {
        return new RequestSpecBuilder()
                .setBaseUri(Manipulation.getProps().getProperty("ApiURL"))
                .setConfig(config().logConfig(
                        logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
                ))
                .build();
    }
}
