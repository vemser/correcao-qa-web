package com.vemser.correcao.test.funcional;

import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.dto.CompiladorResponseDto;
import com.vemser.correcao.client.CompiladorCorrecaoClient;
import com.vemser.correcao.dto.ErrorDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Funcional Compilador Correção - POST")
@DisplayName("Compilador Correção - POST")
@Owner("Gabriel Sales")
public class CompiladorCorrecaoPostFuncionalTest {
    // TODO: CORRIGIR ERRO
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA008] Informar Campos Válidos Com Código Java")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código válido com a linguagem Java o compilador retorna 200 e a mensagem de retorno do código")
    public void testCompiladorCorrecao_camposValidosComCodigoJava_esperaSucesso() {
        CompiladorDto compiladorJava = CompiladorDataFactory.compiladorJavaValido();

        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorJava).then()
                .statusCode(200)
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals("Vem Ser - Correção", compiladorResponse.getRetorno());
    }

    // TODO: CORRIGIR ERRO
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA009] Informar Campos Válidos Com Código JavaScript")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código válido com a linguagem JavaScript o compilador retorna 200 e a mensagem de retorno do código")
    public void testCompiladorCorrecao_camposValidosComCodigoJavascript_esperaSucesso() {
        CompiladorDto compiladorJavascript = CompiladorDataFactory.compiladorJavascriptValido();

        CompiladorResponseDto compiladorResponse = CompiladorCorrecaoClient.compilarCodigo(compiladorJavascript).then()
                .statusCode(200)
                .extract().as(CompiladorResponseDto.class);

        Assertions.assertNotNull(compiladorResponse.getRetorno());
        Assertions.assertEquals("Vem Ser - Correção", compiladorResponse.getRetorno());
    }

    // TODO: ALTERAR TIPO DA MENSAGEM DE ERRO
    @Test
    @Feature("Espera Erro")
    @Story("[CTA010] Informar Código Java Inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código inválido com a linguagem Java o compilador retorna 400 e a mensagem 'Erro ao compilar o arquivo'")
    public void testCompiladorCorrecao_codigoJavaInvalido_esperaErro() {
        CompiladorDto compiladorCodigoInvalido = CompiladorDataFactory.compiladorCodigoJavaInvalido();

        ErrorDto erro = CompiladorCorrecaoClient.compilarCodigo(compiladorCodigoInvalido).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de compilador informando código java inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Erro ao compilar o arquivo", erro.getErrors().get("codigo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    // TODO: ALTERAR TIPO DA MENSAGEM DE ERRO
    @Test
    @Feature("Espera Erro")
    @Story("[CTA011] Informar Código JavaScript Inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar um código inválido com a linguagem JavaScript o compilador retorna 400 e a mensagem 'Erro ao compilar o arquivo'")
    public void testCompiladorCorrecao_codigoJavascriptInvalido_esperaErro() {
        CompiladorDto compiladorCodigoInvalido = CompiladorDataFactory.compiladorCodigoJavascriptInvalido();

        ErrorDto erro = CompiladorCorrecaoClient.compilarCodigo(compiladorCodigoInvalido).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de compilador informando código javascript inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Erro ao compilar o arquivo", erro.getErrors().get("codigo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA012] Informar Linguagem Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar uma linguagem inválida o compilador retorna 400 e a mensagem 'Linguagem não é válida'")
    public void testCompiladorCorrecao_linguagemInvalida_esperaErro() {
        CompiladorDto compiladorLinguagemInvalida = CompiladorDataFactory.compiladorLinguagemInvalida();

        ErrorDto erro = CompiladorCorrecaoClient.compilarCodigo(compiladorLinguagemInvalida).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de compilador informando linguagem inválida",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Linguagem não é válida", erro.getErrors().get("linguagem"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    // TODO: VERIFICAR ERRO
    @Test
    @Feature("Espera Erro")
    @Story("[CTA013] Informar Linguagem nula")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar linguagem nula o compilador retorna 400 e a mensagem 'Linguagem não pode ser nula'")
    public void testCompiladorCorrecao_linguagemNaoInformada_esperaErro() {
        CompiladorDto compilador = CompiladorDataFactory.compiladorLinguagemNula();

        ErrorDto erro = CompiladorCorrecaoClient.compilarCodigo(compilador).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de compilador informando linguagem nula",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Linguagem não pode ser nula", erro.getErrors().get("linguagem"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    // TODO: VERIFICAR ERRO
    @Test
    @Feature("Espera Erro")
    @Story("[CTA014] Informar Código Nulo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar código nulo o compilador retorna 400 e a mensagem 'Código não pode ser nulo'")
    public void testCompiladorCorrecao_codigoNaoInformado_esperaErro() {
        CompiladorDto compilador = CompiladorDataFactory.compiladorCodigoNulo();

        ErrorDto erro = CompiladorCorrecaoClient.compilarCodigo(compilador).then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de compilador informando código nulo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Código não pode ser nulo", erro.getErrors().get("codigo"), "Mensagem de erro deve ser igual a esperada")
        );
    }
}
