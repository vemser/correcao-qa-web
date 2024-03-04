package com.vemser.correcao.specs;

import com.vemser.correcao.utils.Auth;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class AtividadesSpecs {

    public static RequestSpecification atividadeInstrutorSpec() {
        String tokenInstrutor = Auth.obterTokenInstrutor();

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .setContentType(ContentType.JSON)
//                .addHeader("Authorization", tokenInstrutor)
                .addHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imluc3RydXRvci50ZXN0ZSIsImp0aSI6IjUiLCJjYXJnb3MiOlsiUk9MRV9JTlNUUlVUT1IiXSwiaWF0IjoxNzA5NTc3OTEzLCJleHAiOjE3MTIxNjk5MDh9.ZodW-s7MHGyTcVyaA6VOqxDEsaYSS7eB7BYQzIu20KI")
                .build();
    }

    public static RequestSpecification atividadeAlunoSpec() {
        String tokenAluno = Auth.obterTokenAluno();

        return new RequestSpecBuilder()
                .addRequestSpecification(InicialSpecs.setupApi())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", tokenAluno)
                .build();
    }
}
