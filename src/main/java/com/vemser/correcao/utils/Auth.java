package com.vemser.correcao.utils;

import com.vemser.correcao.client.LoginClient;
import com.vemser.correcao.dto.login.LoginDto;
import com.vemser.correcao.dto.login.LoginResponseDto;
import com.vemser.correcao.data.factory.LoginDataFactory;

public class Auth {
    public static String obterTokenInstrutor() {
        LoginDto login = LoginDataFactory.loginInstrutor();

        String token = LoginClient.autenticar(login).getBody().asString();
        LoginResponseDto loginResponseDto = new LoginResponseDto(token);

        return loginResponseDto.getToken();
    }

    public static String obterTokenAluno() {
        LoginDto login = LoginDataFactory.loginAluno();

        String token = LoginClient.autenticar(login).getBody().asString();
        LoginResponseDto loginResponseDto = new LoginResponseDto(token);

        return loginResponseDto.getToken();
    }
}
