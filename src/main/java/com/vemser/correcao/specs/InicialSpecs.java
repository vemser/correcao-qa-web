package com.vemser.correcao.specs;

import com.vemser.correcao.utils.ConfigProperties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;
import static io.restassured.config.LogConfig.logConfig;

public class InicialSpecs {

    private InicialSpecs() {}

    public static RequestSpecification setupCompilador() {
        ConfigProperties.initializePropertyFile();

        return new RequestSpecBuilder()
                .setBaseUri(ConfigProperties.properties.getProperty("CopiladorURL"))
                .setConfig(config().logConfig(
                        logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
                ))
                .build();
    }

    public static RequestSpecification setupApi() {
        ConfigProperties.initializePropertyFile();

        return new RequestSpecBuilder()
                .setBaseUri(ConfigProperties.properties.getProperty("ApiURL"))
                .setConfig(config().logConfig(
                        logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
                ))
                .build();
    }
}
