package com.vemser.test.contrato.compilador;

import com.vemser.correcao.data.factory.compilador.CompiladorDataFactory;
import com.vemser.correcao.dto.compilador.CompiladorDto;
import com.vemser.correcao.specs.compilador.CompiladorSpecs;
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