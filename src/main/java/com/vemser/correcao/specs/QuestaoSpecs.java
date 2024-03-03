package com.vemser.correcao.specs;

import com.vemser.correcao.utils.Auth;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class QuestaoSpecs {
    public static RequestSpecification questaoReqAuthInstrutorSpec() {
        String tokenInstrutor = Auth.obterTokenInstrutor();

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", tokenInstrutor)
                .build();
    }

    public static RequestSpecification questaoReqAuthAlunoSpec() {
        String tokenAluno = Auth.obterTokenAluno();

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", tokenAluno)
                .build();
    }

    public static RequestSpecification questaoReqSemTokenSpec() {

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .setContentType(ContentType.JSON)
                .build();
    }
}
