package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.LoginClient;
import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.LoginDataFactory;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.ErrorDto;
import com.vemser.correcao.dto.LoginDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.specs.LoginSpecs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginFuncionalTest {
    // CENÁRIOS POSITIVOS
    @Test
    @DisplayName("[CTAXXX] Login - Logar Como Instrutor Com Dados Válidos (Espera Sucesso)")
    public void testLogin_logarInstrutor_esperaSucesso() {
        LoginDto login = LoginDataFactory.loginInstrutor();
        String token = LoginClient.autenticar(login)
            .then()
                .statusCode(200)
                .extract()
                .asString();
        assertNotNull(token);

        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(3);
        QuestaoResponseDto questaoResponseDto = QuestaoClient.cadastrarQuestaoPorLogin(questao, token)
            .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResponseDto.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);

    }

    @Test
    @DisplayName("[CTAXXX] Login - Logar Como Aluno Com Dados Válidos (Espera Sucesso)")
    public void testLogin_logarAluno_esperaSucesso() {
        LoginDto login = LoginDataFactory.loginAluno();
        String token = LoginClient.autenticar(login)
            .then()
                .statusCode(200)
                .extract()
                .asString();

        assertNotNull(token);
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(3);
        ErrorDto erro = QuestaoClient.cadastrarQuestaoPorLogin(questao, token)
            .then()
                .statusCode(403)
                .extract()
                .as(ErrorDto.class);

        assertAll("Testar se o usuário foi barrado por falta de permissão",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertEquals(erro.getStatus(), 403, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "Acesso negado")
        );
    }

    // CENÁRIOS NEGATIVOS
    @Test
    @DisplayName("[CTAXXX] Login - Informar Username Vazio (Espera Erro)")
    public void testLogin_informarUsernameVazio_esperaErro() {
        LoginDto login = LoginDataFactory.loginAluno();
        login.setUsername("");
        ErrorDto erro = LoginClient.autenticar(login)
            .then()
                .statusCode(400)
                .extract()
                .as(ErrorDto.class);

        assertAll("Testar erro de login invalido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("username"), "não deve estar em branco")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Login - Informar Password Vazio (Espera Erro)")
    public void testLogin_informarPasswordVazio_esperaErro() {
        LoginDto login = LoginDataFactory.loginAluno();
        login.setPassword("");
        ErrorDto erro = LoginClient.autenticar(login)
            .then()
                .statusCode(400)
                .extract()
                .as(ErrorDto.class);

        assertAll("Testar erro de login invalido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("password"), "não deve estar em branco")
        );
    }
    @Test
    @DisplayName("[CTAXXX] Login - Informar Campos Vazios (Espera Erro)")
    public void testLogin_informarCamposVazios_esperaErro() {
        LoginDto login = LoginDataFactory.loginAluno();
        login.setUsername("");
        login.setPassword("");
        ErrorDto erro = LoginClient.autenticar(login)
            .then()
                .statusCode(400)
                .extract()
                .as(ErrorDto.class);

        assertAll("Testar erro de login invalido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("password"), "não deve estar em branco"),
                () -> assertEquals(erro.getErrors().get("username"), "não deve estar em branco")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Login - Informar Senha Incorreta (Espera Erro)")
    public void testCriarQuestao_informarSenhaIncorreta_esperaErro() {
        LoginDto login = LoginDataFactory.loginAluno();
        login.setPassword("senhaIncorreta");
        ErrorDto erro = LoginClient.autenticar(login)
            .then()
                .statusCode(400)
                .extract()
                .as(ErrorDto.class);

        assertAll("Testar erro de login invalido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "login e senha inválidos!")
        );
    }
}