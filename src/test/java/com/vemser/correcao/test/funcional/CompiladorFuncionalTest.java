package com.vemser.correcao.test.funcional;

import com.vemser.compilador.client.CompiladorClient;
import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.dto.CompiladorResponseDto;
import com.vemser.compilador.specs.CompiladorSpecs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompiladorFuncionalTest {
    @Test
    @DisplayName("Compilador - Informar Campos Válidos Com Código Java (Espera Sucesso)")
    public void testCompilador_camposValidosComCodigoJava_esperaSucesso() {
        CompiladorDto compiladorJava = CompiladorDataFactory.compiladorJavaValido();

        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorJava).then()
                .spec(CompiladorSpecs.compiladorSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

    @Test
    @DisplayName("Compilador - Informar Campos Válidos Com Código JavaScript (Espera Sucesso)")
    public void testCompilador_camposValidosComCodigoJavascript_esperaSucesso() {
        CompiladorDto compiladorJavascript = CompiladorDataFactory.compiladorJavascriptValido();

        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorJavascript).then()
                .spec(CompiladorSpecs.compiladorSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

    @Test
    @DisplayName("Compilador - Informar Código Java Inválido (Espera Erro)")
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
    @DisplayName("Compilador - Informar Código JavaScript Inválido (Espera Erro)")
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
    @DisplayName("Compilador - Informar Linguagem Inválida (Espera Erro)")
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
    @DisplayName("Compilador - Não Informar Linguagem (Espera Erro)")
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
    @DisplayName("Compilador - Não Informar Código (Espera Erro)")
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
