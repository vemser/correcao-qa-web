package com.vemser.correcao.data.factory;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.dto.ListaTodasQuestaoResponseDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.enums.Dificuldade;
import com.vemser.correcao.specs.QuestaoSpecs;
import net.datafaker.Faker;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class QuestaoDataFactory {
    private static Faker faker = new Faker(new Locale("PT-BR"));

    private QuestaoDataFactory() {}

    public static QuestaoDto questaoAleatoria(){
        return novaQuestaoAleatoria();
    }

    public static QuestaoDto novaQuestaoAleatoria(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(faker.lorem().sentence());
        questaoDto.setDescricao(faker.lorem().paragraph());

        questaoDto.setDificuldade(Dificuldade.values()[faker.random().nextInt(Dificuldade.values().length)]);
        questaoDto.setLinguagem(Linguagem.JAVA);
        questaoDto.setCodigo(faker.lorem().sentence());
        questaoDto.setTestes(TesteDataFactory.criarListaDeTestesCorretos(2));

        return questaoDto;
    }

    public static QuestaoDto questaoSemTitulo(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo("");
        questaoDto.setDescricao(faker.lorem().paragraph());
        questaoDto.setDificuldade(Dificuldade.values()[faker.random().nextInt(Dificuldade.values().length)]);
        questaoDto.setLinguagem(Linguagem.JAVA);
        questaoDto.setCodigo(faker.lorem().sentence());
        questaoDto.setTestes(TesteDataFactory.criarListaDeTestesCorretos(2));

        return questaoDto;
    }

    public static QuestaoDto questaoInvalida(){
        return new QuestaoDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
