package com.vemser.correcao.test.contrato;

import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.correcao.client.CompiladorCorrecaoClient;
import com.vemser.correcao.specs.CompiladorCorrecaoSpecs;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Epic("Contrato POST Compilador Correção")
@DisplayName("Contrato POST Compilador Correção")
@Owner("Gabriel Sales")
public class CompiladorCorrecaoPostContratoTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("Validar Contrato")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Teste que verifica o contrato do POST do compilador correção")
    public void testCompilador_validarContrato_esperaSucesso() {
        CompiladorDto compiladorDto = CompiladorDataFactory.compiladorJavaValido();

        CompiladorCorrecaoClient.compilarCodigo(compiladorDto).then()
                        .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoSucessoResponse());
    }
}