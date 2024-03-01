package com.vemser.correcao.test.contrato;

import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.correcao.client.CompiladorCorrecaoClient;
import com.vemser.correcao.specs.CompiladorCorrecaoSpecs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CompiladorCorrecaoPostContratoTest {
    @Test
    @DisplayName("Compilador Correção - Validar Contrato (Espera Sucesso)")
    public void testCompilador_validarContrato_esperaSucesso() {
        CompiladorDto compiladorDto = CompiladorDataFactory.compiladorJavaValido();

        CompiladorCorrecaoClient.compilarCodigo(compiladorDto).then()
                        .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoSucessoResponse());
    }
}