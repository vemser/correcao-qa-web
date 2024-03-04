package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.LoginClient;
import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.LoginDataFactory;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.*;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Login - POST")
@DisplayName("Login - POST")
@Owner("Vitor Colombo")
public class LoginFuncionalTest {
    // CENÁRIOS POSITIVOS
    @Test
    @Feature("Fazer Login")
    @Story("[CTAXXX] Login - Logar Como Instrutor Com Dados Válidos")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Teste que verifica se o usuário logou como instrutor com as permissões devidas. A API deve permitir request de cadastro e delete a partir deste token")
    public void testLogin_logarInstrutor_esperaSucesso() {
        LoginDto login = LoginDataFactory.loginInstrutor();
        String token = LoginClient.autenticar(login)
            .then()
                .statusCode(200)
                .extract()
                .asString();
        assertNotNull(token);

        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);
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
    @Feature("Fazer Login")
    @Story("[CTAXXX] Login - Logar Como Aluno Com Dados Válidos")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Teste que verifica se o usuário logou como aluno com as permissões devidas.")
    public void testLogin_logarAluno_esperaSucesso() {
        LoginDto login = LoginDataFactory.loginAluno();
        String token = LoginClient.autenticar(login)
            .then()
                 .statusCode(200)
                .extract()
                .asString();

        assertNotNull(token);
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(3);
        ErrorDeleteDto erro = QuestaoClient.cadastrarQuestaoPorLogin(questao, token)
            .then()
                .statusCode(403)
                .extract()
                .as(ErrorDeleteDto.class);

        assertAll("Testar se o usuário foi barrado por falta de permissão",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertEquals(erro.getStatus(), 403, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getError(), "Forbidden")
        );
    }

    // CENÁRIOS NEGATIVOS
    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Login - Informar Username Vazio")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Teste que verifica se ao informar username vazio e password válido a API retorna 400 e a mensagem 'username: não deve estar em branco'")
    public void testLogin_informarUsernameVazio_esperaErro() {
        LoginDto login = LoginDataFactory.loginAluno();
        login.setUsername("");
        ErroDto erro = LoginClient.autenticar(login)
            .then()
                .statusCode(400)
                .extract()
                .as(ErroDto.class);

        assertAll("Testar erro de login invalido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("username"), "O campo username é obrigatório")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Login - Informar Password Vazio")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Teste que verifica se ao informar username válido e password vazio a API retorna 400 e a mensagem 'password: não deve estar em branco'")
    public void testLogin_informarPasswordVazio_esperaErro() {
        LoginDto login = LoginDataFactory.loginAluno();
        login.setPassword("");
        ErroDto erro = LoginClient.autenticar(login)
            .then()
                .statusCode(400)
                .extract()
                .as(ErroDto.class);

        assertAll("Testar erro de login invalido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("password"), "O campo password é obrigatório")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Login - Informar Campos Vazios")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Teste que verifica se ao informar campos vazios a API retorna 400 e as mensagens 'password: não deve estar em branco, username: não deve estar em branco'")
    public void testLogin_informarCamposVazios_esperaErro() {
        LoginDto login = LoginDataFactory.loginAluno();
        login.setUsername("");
        login.setPassword("");
        ErroDto erro = LoginClient.autenticar(login)
            .then()
                .statusCode(400)
                .extract()
                .as(ErroDto.class);

        assertAll("Testar erro de login invalido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("password"), "O campo password é obrigatório"),
                () -> assertEquals(erro.getErrors().get("username"), "O campo username é obrigatório")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Login - Informar Senha Incorreta")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Teste que verifica se ao informar username válido e senha incorreta a API retorna 400 e a mensagem 'login e senha inválidos!'")
    public void testCriarQuestao_informarSenhaIncorreta_esperaErro() {
        LoginDto login = LoginDataFactory.loginAluno();
        login.setPassword("senhaIncorreta");
        ErroDto erro = LoginClient.autenticar(login)
            .then()
                .statusCode(400)
                .extract()
                .as(ErroDto.class);

        assertAll("Testar erro de login invalido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "Usuário ou senha incorretos!")
        );
    }
}