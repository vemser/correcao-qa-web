package com.vemser.correcao.test.funcional;

import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.dto.CompiladorResponseDto;
import com.vemser.correcao.client.CompiladorCorrecaoClient;
import com.vemser.correcao.specs.CompiladorCorrecaoSpecs;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Funcional Compilador Correção - POST")
@DisplayName("Compilador Correção - POST")
@Owner("Gabriel Sales")

public class CompiladorCorrecaoPostFuncionalTest {
    @Test
    @Feature("Espera Sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Story("Informar Campos Válidos Com Código Java")
    public void testCompiladorCorrecao_camposValidosComCodigoJava_esperaSucesso() {
        CompiladorDto compiladorJava = CompiladorDataFactory.compiladorJavaValido();

        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorJava).then()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

    @Test
    @Feature("Espera Sucesso")
    @Severity(SeverityLevel.NORMAL)
    @Story("Informar Campos Válidos Com Código JavaScript")
    public void testCompiladorCorrecao_camposValidosComCodigoJavascript_esperaSucesso() {
        CompiladorDto compiladorJavascript = CompiladorDataFactory.compiladorJavascriptValido();

        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorJavascript).then()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

    @Test
    @Feature("Espera Erro")
    @Severity(SeverityLevel.NORMAL)
    @Story("[CTAXXX] Informar Código Java Inválido")
    public void testCompiladorCorrecao_codigoJavaInvalido_esperaErro() {
        CompiladorDto compiladorCodigoInvalido = CompiladorDataFactory.compiladorCodigoJavaInvalido();

        // TODO: implementar mensagem de erro
        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorCodigoInvalido).then()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Erro ao compilar o arquivo");
    }

    @Test
    @Feature("Espera Erro")
    @Severity(SeverityLevel.NORMAL)
    @Story("[CTAXXX] Informar Código JavaScript Inválido")
    public void testCompiladorCorrecao_codigoJavascriptInvalido_esperaErro() {
        CompiladorDto compiladorCodigoInvalido = CompiladorDataFactory.compiladorCodigoJavascriptInvalido();

        // TODO: implementar mensagem de erro
        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorCodigoInvalido).then()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Erro ao compilar o arquivo");
    }

    @Test
    @Feature("Espera Erro")
    @Severity(SeverityLevel.NORMAL)
    @Story("[CTAXXX] Informar Linguagem Inválida")
    public void testCompiladorCorrecao_linguagemInvalida_esperaErro() {
        CompiladorDto compiladorLinguagemInvalida = CompiladorDataFactory.compiladorLinguagemInvalida();

        // TODO: implementar mensagem de erro
        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorLinguagemInvalida).then()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getTimestamp());
        Assertions.assertNotNull(compiladorResponse.getStatus());
        Assertions.assertNotNull(compiladorResponse.getErrors());
        Assertions.assertEquals(compiladorResponse.getStatus(), 400);
        Assertions.assertTrue(compiladorResponse.getErrors().contains("???"));
    }

    @Test
    @Feature("Espera Erro")
    @Severity(SeverityLevel.NORMAL)
    @Story("[CTAXXX] Não Informar Linguagem")
    public void testCompiladorCorrecao_linguagemNaoInformada_esperaErro() {
        CompiladorDto compilador = CompiladorDataFactory.compiladorJavaValido();
        String compiladorJson = String.format("""
                {
                    "codigo": "%s"
                }
                """, compilador.getCodigo());

        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorJson).then()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getTimestamp());
        Assertions.assertNotNull(compiladorResponse.getStatus());
        Assertions.assertNotNull(compiladorResponse.getErrors());
        Assertions.assertEquals(compiladorResponse.getStatus(), 400);
        Assertions.assertTrue(compiladorResponse.getErrors().contains("linguagem: must not be null"));
    }

    @Test
    @Feature("Espera Erro")
    @Severity(SeverityLevel.NORMAL)
    @Story("[CTAXXX] Não Informar Código")
    public void testCompiladorCorrecao_codigoNaoInformado_esperaErro() {
        CompiladorDto compilador = CompiladorDataFactory.compiladorJavaValido();
        String compiladorJson = String.format("""
                {
                    "linguagem": "%s"
                }
                """, compilador.getLinguagem());

        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorJson).then()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getTimestamp());
        Assertions.assertNotNull(compiladorResponse.getStatus());
        Assertions.assertNotNull(compiladorResponse.getErrors());
        Assertions.assertEquals(compiladorResponse.getStatus(), 400);
        Assertions.assertTrue(compiladorResponse.getErrors().contains("codigo: must not be blank"));
    }
}
