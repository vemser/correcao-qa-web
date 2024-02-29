package com.vemser.compilador.test;

import com.vemser.compilador.client.CompiladorClient;
import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.specs.CompiladorSpecs;
import com.vemser.correcao.client.CompiladorCorrecaoClient;
import com.vemser.correcao.specs.CompiladorCorrecaoSpecs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CompiladorCorrecaoContratoTest {
    @Test
    @DisplayName("Compilador Correção - Validar Contrato (Espera Sucesso)")
    public void testCompilador_validarContrato_esperaSucesso() {
        CompiladorDto compiladorDto = CompiladorDataFactory.compiladorJavaValido();

        CompiladorCorrecaoClient.compilarCodigo(compiladorDto).then()
                        .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoSucessoResponse());
    }
}