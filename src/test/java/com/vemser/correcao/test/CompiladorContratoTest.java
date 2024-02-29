package com.vemser.correcao.test;

import com.vemser.compilador.client.CompiladorClient;
import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.specs.CompiladorSpecs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CompiladorContratoTest {
    @Test
    @DisplayName("Compilador - Validar Contrato (Espera Sucesso)")
    public void testCompilador_validarContrato_esperaSucesso() {
        CompiladorDto compiladorDto = CompiladorDataFactory.compiladorJavaValido();

        CompiladorClient.compilarCodigo(compiladorDto).then()
                .spec(CompiladorSpecs.compiladorSucessoResponse());
    }
}