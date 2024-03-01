package com.vemser.correcao.factory;

import com.vemser.correcao.dto.LoginDto;

public class LoginDataFactory {
    public static final String USERNAME_ALUNO = "aluno.teste";
    public static final String PASSWORD_ALUNO = "Jvs9^@B809lP";
    public static final String USERNAME_INSTRUTOR = "instrutor.teste";
    public static final String PASSWORD_INSTRUTOR = "Jvs9^@B809lP";

    public static LoginDto loginAluno() {
        LoginDto loginAluno = new LoginDto();
        loginAluno.setUsername(USERNAME_ALUNO);
        loginAluno.setPassword(PASSWORD_ALUNO);
        return loginAluno;
    }

    public static LoginDto loginInstrutor() {
        LoginDto loginAluno = new LoginDto();
        loginAluno.setUsername(USERNAME_INSTRUTOR);
        loginAluno.setPassword(PASSWORD_INSTRUTOR);
        return loginAluno;
    }
}
