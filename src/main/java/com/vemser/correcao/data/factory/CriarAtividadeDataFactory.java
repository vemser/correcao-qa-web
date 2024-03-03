package com.vemser.correcao.data.factory;

import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.enums.Dificuldade;
import com.vemser.correcao.enums.Trilha;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CriarAtividadeDataFactory {



    public static CriarAtividadeDto atividadeComDadosValidos() {
        Faker faker = new Faker(new Locale("PT-BR"));
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(3);
        questoes.add(6);

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(questoes);
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }
}
