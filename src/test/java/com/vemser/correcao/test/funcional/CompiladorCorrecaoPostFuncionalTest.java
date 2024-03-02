package com.vemser.correcao.test.funcional;

import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.dto.CompiladorResponseDto;
import com.vemser.correcao.client.CompiladorCorrecaoClient;
import com.vemser.correcao.specs.CompiladorCorrecaoSpecs;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Funcional Compilador Correção - POST")

public class CompiladorCorrecaoPostFuncionalTest {
    @Test
    @DisplayName("Compilador Correção - Informar Campos Válidos Com Código Java (Espera Sucesso)")
    public void testCompiladorCorrecao_camposValidosComCodigoJava_esperaSucesso() {
        CompiladorDto compiladorJava = CompiladorDataFactory.compiladorJavaValido();

        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorJava).then()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

    @Test
    @DisplayName("Compilador Correção - Informar Campos Válidos Com Código JavaScript (Espera Sucesso)")
    public void testCompiladorCorrecao_camposValidosComCodigoJavascript_esperaSucesso() {
        CompiladorDto compiladorJavascript = CompiladorDataFactory.compiladorJavascriptValido();

        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorJavascript).then()
                .spec(CompiladorCorrecaoSpecs.compiladorCorrecaoSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

    @Test
    @DisplayName("Compilador Correção - Informar Código Java Inválido (Espera Erro)")
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
    @DisplayName("Compilador Correção - Informar Código JavaScript Inválido (Espera Erro)")
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
    @DisplayName("Compilador Correção - Informar Linguagem Inválida (Espera Erro)")
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
    @DisplayName("Compilador Correção - Não Informar Linguagem (Espera Erro)")
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
    @DisplayName("Compilador Correção - Não Informar Código (Espera Erro)")
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
