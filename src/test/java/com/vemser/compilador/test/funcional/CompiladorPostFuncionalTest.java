package com.vemser.compilador.test.funcional;

import com.vemser.compilador.client.CompiladorClient;
import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.dto.CompiladorResponseDto;
import com.vemser.compilador.specs.CompiladorSpecs;
import org.junit.jupiter.api.Assertions;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Funcional Compilador - POST")
@DisplayName("Compilador - POST")
@Owner("Gabriel Sales")
public class CompiladorPostFuncionalTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos Com Código Java")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código válido com a linguagem Java o compilador retorna 200 com uma string")
    public void testCompilador_camposValidosComCodigoJava_esperaSucesso() {
        CompiladorDto compiladorJava = CompiladorDataFactory.compiladorJavaValido();

        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorJava).then()
                .spec(CompiladorSpecs.compiladorSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos Com Código JavaScript")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código válido com a linguagem JavaScript o compilador retorna 200 com uma string")
    public void testCompilador_camposValidosComCodigoJavascript_esperaSucesso() {
        CompiladorDto compiladorJavascript = CompiladorDataFactory.compiladorJavascriptValido();

        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorJavascript).then()
                .spec(CompiladorSpecs.compiladorSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Código Java Inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código inválido com a linguagem Java o compilador retorna a mensagem 'Erro ao compilar o arquivo'")
    public void testCompilador_codigoJavaInvalido_esperaErro() {
        CompiladorDto compiladorCodigoInvalido = CompiladorDataFactory.compiladorCodigoJavaInvalido();

        // TODO: implementar mensagem de erro
        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorCodigoInvalido).then()
                .spec(CompiladorSpecs.compiladorErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Erro ao compilar o arquivo");
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Código JavaScript Inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código inválido com a linguagem JavaScript o compilador retorna a mensagem 'Erro ao compilar o arquivo'")
    public void testCompilador_codigoJavascriptInvalido_esperaErro() {
        CompiladorDto compiladorCodigoInvalido = CompiladorDataFactory.compiladorCodigoJavascriptInvalido();

        // TODO: implementar mensagem de erro
        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorCodigoInvalido).then()
                .spec(CompiladorSpecs.compiladorErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Erro ao compilar o arquivo");
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Linguagem Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar uma linguagem inválida o compilador retorna a mensagem ''")
    public void testCompilador_linguagemInvalida_esperaErro() {
        CompiladorDto compiladorLinguagemInvalida = CompiladorDataFactory.compiladorLinguagemInvalida();

        // TODO: implementar mensagem de erro
        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorLinguagemInvalida).then()
                .spec(CompiladorSpecs.compiladorErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getTimestamp());
        Assertions.assertNotNull(compiladorResponse.getStatus());
        Assertions.assertNotNull(compiladorResponse.getErrors());
        Assertions.assertEquals(compiladorResponse.getStatus(), 400);
        Assertions.assertTrue(compiladorResponse.getErrors().contains("???"));
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Linguagem")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código sem informar a linguagem o compilador retorna 400 e a mensagem 'linguagem: must not be null'")
    public void testCompilador_linguagemNaoInformada_esperaErro() {
        CompiladorDto compilador = CompiladorDataFactory.compiladorJavaValido();
        String compiladorJson = String.format("""
                {
                    "codigo": "%s"
                }
                """, compilador.getCodigo());

        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorJson).then()
                .spec(CompiladorSpecs.compiladorErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getTimestamp());
        Assertions.assertNotNull(compiladorResponse.getStatus());
        Assertions.assertNotNull(compiladorResponse.getErrors());
        Assertions.assertEquals(compiladorResponse.getStatus(), 400);
        // TODO: alterar mensagem de erro para português
        Assertions.assertTrue(compiladorResponse.getErrors().contains("linguagem: must not be null"));
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Código")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao informar a linguagem e não informar um código o compilador retorna 400 e a mensagem 'codigo: must not be blank'")
    public void testCompilador_codigoNaoInformado_esperaErro() {
        CompiladorDto compilador = CompiladorDataFactory.compiladorJavaValido();
        String compiladorJson = String.format("""
                {
                    "linguagem": "%s"
                }
                """, compilador.getLinguagem());

        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorJson).then()
                .spec(CompiladorSpecs.compiladorErroResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getTimestamp());
        Assertions.assertNotNull(compiladorResponse.getStatus());
        Assertions.assertNotNull(compiladorResponse.getErrors());
        Assertions.assertEquals(compiladorResponse.getStatus(), 400);
        // TODO: alterar mensagem de erro para português
        Assertions.assertTrue(compiladorResponse.getErrors().contains("codigo: must not be blank"));
    }
}
