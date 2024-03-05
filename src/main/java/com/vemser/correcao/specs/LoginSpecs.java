package com.vemser.correcao.specs;

import com.vemser.correcao.utils.Auth;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class LoginSpecs {
    public static RequestSpecification loginInstrutorReqSpec() {
        String tokenInstrutor = Auth.obterTokenInstrutor();

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", tokenInstrutor)
                .build();
    }

    public static RequestSpecification loginAlunoReqSpec() {
        String tokenAluno = Auth.obterTokenAluno();

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", tokenAluno)
                .build();
    }

    public static RequestSpecification reqSemTokenSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .setContentType(ContentType.JSON)
                .build();
    }

}
