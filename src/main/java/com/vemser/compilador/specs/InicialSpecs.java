package com.vemser.compilador.specs;

import com.vemser.compilador.utils.Manipulation;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;
import static io.restassured.config.LogConfig.logConfig;

public class InicialSpecs {

    private InicialSpecs() {}

    public static RequestSpecification setupCompilador() {
        return new RequestSpecBuilder()
                .setBaseUri(Manipulation.getProps().getProperty("CompiladorURL"))
                .setConfig(config().logConfig(
                        logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
                ))
                .build();
    }
}
