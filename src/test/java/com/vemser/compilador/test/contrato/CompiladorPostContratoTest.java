package com.vemser.compilador.test.contrato;

import com.vemser.compilador.client.CompiladorClient;
import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.specs.CompiladorSpecs;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Contrato Compilador - POST")
@DisplayName("Contrato compilador - POST")
@Owner("Gabriel Sales")
public class CompiladorPostContratoTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA008] Informar Campos Válidos Com Código Java Para Validar Corpo Da Resposta De Sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código válido com a linguagem Java o compilador retorna corpo válido da resposta de sucesso")
    public void testCompilador_informarCamposValidosComCodigoJavaParaValidarCorpoDaRespostaDeSucesso_esperaSucesso() {
        CompiladorDto compiladorDto = CompiladorDataFactory.compiladorJavaValido();

        CompiladorClient.compilarCodigo(compiladorDto).then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/compilador-post-sucesso.json"));
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA009] Informar Código Java Inválido Para Validar Corpo Da Resposta De Erro")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao enviar um código inválido com a linguagem Java o compilador retorna 400 e a mensagem 'Erro ao compilar o arquivo'")
    public void testCompilador_informarCodigoJavaInvalidoParaValidarCorpoDaRespostaDeErro_esperaErro() {
        CompiladorDto compiladorCodigoInvalido = CompiladorDataFactory.compiladorCodigoJavaInvalido();

        CompiladorClient.compilarCodigo(compiladorCodigoInvalido).then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/erro-compilador.json"))
                .extract().asString();
    }
}