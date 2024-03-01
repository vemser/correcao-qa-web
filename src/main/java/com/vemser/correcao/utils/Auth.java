package com.vemser.correcao.utils;

import com.vemser.correcao.client.LoginClient;
import com.vemser.correcao.dto.LoginDto;
import com.vemser.correcao.dto.LoginResponseDto;
import com.vemser.correcao.factory.LoginDataFactory;

public class Auth {
    public static void realizarLoginInstrutor() {
        LoginDto login = LoginDataFactory.loginInstrutor();

        String token = LoginClient.autenticar(login).getBody().asString();

        Manipulation.setProp("TokenInstrutor", token);
    }

    public static void realizarLoginAluno() {
        LoginDto login = LoginDataFactory.loginAluno();

        String token = LoginClient.autenticar(login).getBody().asString();

        Manipulation.setProp("TokenAluno", token);
    }
}
