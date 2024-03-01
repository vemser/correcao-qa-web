package com.vemser.correcao.factory;

import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.enums.Dificuldade;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        questaoDto.setTestes(TesteDataFactory.criarListaDeTestesCorretos(3));

        return questaoDto;
    }

    public static QuestaoDto questaoSemTitulo(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo("");
        questaoDto.setDescricao(faker.lorem().paragraph());
        questaoDto.setDificuldade(Dificuldade.values()[faker.random().nextInt(Dificuldade.values().length)]);
        questaoDto.setLinguagem(Linguagem.JAVA);
        questaoDto.setCodigo(faker.lorem().sentence());
        questaoDto.setTestes(TesteDataFactory.criarListaDeTestesCorretos(3));

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
                null
        );
    }
}
