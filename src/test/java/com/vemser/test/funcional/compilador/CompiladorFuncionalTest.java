package com.vemser.test.funcional.compilador;

import com.vemser.correcao.client.compilador.CompiladorClient;
import com.vemser.correcao.data.factory.compilador.CompiladorDataFactory;
import com.vemser.correcao.dto.compilador.CompiladorDto;
import com.vemser.correcao.dto.compilador.CompiladorResponseDto;
import com.vemser.correcao.specs.compilador.CompiladorSpecs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompiladorFuncionalTest {
    @Test
    @DisplayName("Ofdioehuifh")
    public void testCompilador_camposValidosComCodigoJava_esperaSucesso() {
        CompiladorDto compiladorJava = CompiladorDataFactory.compiladorJavaValido();

        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorJava).then()
                .spec(CompiladorSpecs.compiladorSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

    @Test
    public void testCompilador_camposValidosComCodigoJavascript_esperaSucesso() {
        CompiladorDto compiladorJavascript = CompiladorDataFactory.compiladorJavascriptValido();

        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorJavascript).then()
                .spec(CompiladorSpecs.compiladorSucessoResponse())
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals(compiladorResponse.getRetorno(), "Vem Ser - Correção");
    }

//    @Test
//    public void testCompilador_codigoInvalido_esperaErro() {
//        CompiladorDto compiladorCodigoInvalido = CompiladorDataFactory.compiladorCodigoInvalido();
//
//        // TODO: implementar mensagem de erro
//        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorCodigoInvalido).then()
//                .spec(CompiladorSpecs.compiladorErroResponse())
//                .extract().as(CompiladorResponseDto.class);
//
//        Assertions.assertNotNull(compiladorResponse.getTimestamp());
//        Assertions.assertNotNull(compiladorResponse.getStatus());
//        Assertions.assertNotNull(compiladorResponse.getErrors());
//        Assertions.assertEquals(compiladorResponse.getStatus(), 400);
//        Assertions.assertTrue(compiladorResponse.getErrors().contains("???"));
//    }
//
//    @Test
//    public void testCompilador_linguagemInvalida_esperaErro() {
//        CompiladorDto compiladorLinguagemInvalida = CompiladorDataFactory.compiladorLinguagemInvalida();
//
//        // TODO: implementar mensagem de erro
//        CompiladorResponseDto compiladorResponse = CompiladorClient.compilarCodigo(compiladorLinguagemInvalida).then()
//                .spec(CompiladorSpecs.compiladorErroResponse())
//                .extract().as(CompiladorResponseDto.class);
//
//        Assertions.assertNotNull(compiladorResponse.getTimestamp());
//        Assertions.assertNotNull(compiladorResponse.getStatus());
//        Assertions.assertNotNull(compiladorResponse.getErrors());
//        Assertions.assertEquals(compiladorResponse.getStatus(), 400);
//        Assertions.assertTrue(compiladorResponse.getErrors().contains("???"));
//    }

    @Test
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
