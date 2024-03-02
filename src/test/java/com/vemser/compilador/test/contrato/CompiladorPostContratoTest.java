package com.vemser.compilador.test.contrato;

import com.vemser.compilador.client.CompiladorClient;
import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.specs.CompiladorSpecs;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Epic("Contrato Compilador - POST")
@DisplayName("Contrato compilador - POST")
@Owner("Gabriel Sales")
public class CompiladorPostContratoTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Validar Contrato")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica o contrato do POST do compilador")
    public void testCompilador_validarContrato_esperaSucesso() {
        CompiladorDto compiladorDto = CompiladorDataFactory.compiladorJavaValido();

        CompiladorClient.compilarCodigo(compiladorDto).then()
                .spec(CompiladorSpecs.compiladorSucessoResponse());
    }
}