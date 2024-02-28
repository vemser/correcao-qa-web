package com.vemser.correcao.specs;

import com.vemser.correcao.utils.ConfigProperties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.config;
import static io.restassured.config.LogConfig.logConfig;

public class InicialSpecs {

    private InicialSpecs() {}

    public static RequestSpecification setup() {
        ConfigProperties.initializePropertyFile();

        return new RequestSpecBuilder()
                .setBaseUri(ConfigProperties.properties.getProperty("CopiladorURL"))
                .setConfig(config().logConfig(
                        logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
                ))
                .build();
    }
}
