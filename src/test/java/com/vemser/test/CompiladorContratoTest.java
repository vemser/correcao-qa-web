package com.vemser.test;

import com.vemser.correcao.data.factory.CompiladorDataFactory;
import com.vemser.correcao.dto.CompiladorDto;
import com.vemser.correcao.specs.CompiladorSpecs;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CompiladorContratoTest {
    @Test
    public void testValidarContratoCompilador() {
        CompiladorDto compiladorDto = CompiladorDataFactory.compiladorJavaValido();

        given()
                .spec(CompiladorSpecs.compiladorRequest(compiladorDto))
        .when()
                .post("/compilador")
        .then()
                .spec(CompiladorSpecs.compiladorSucessoResponse());
    }
}