package com.vemser.correcao.data.factory;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.enums.Dificuldade;
import net.datafaker.Faker;

import java.util.Locale;

public class QuestaoDataFactory {
    private static Faker faker = new Faker(new Locale("PT-BR"));

    private QuestaoDataFactory() {}

    public static String tituloFaker() {
        return faker.lorem().sentence();
    }

    public static String descricaoFaker() {
        return faker.lorem().paragraph();
    }

    public static Dificuldade dificuldadeFaker() {
        return Dificuldade.values()[faker.random().nextInt(Dificuldade.values().length)];
    }

    public static Linguagem linguagemFaker() {
        return Linguagem.values()[faker.random().nextInt(Linguagem.values().length)];
    }

    public static String codigoFaker() {
        return faker.lorem().sentence();
    }

    public static QuestaoDto questaoDadosValidos(int numeroTestes){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesCorretos(numeroTestes));

        return questaoDto;
    }

    public static QuestaoDto questaoCodigoVazio(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo("");
        questaoDto.setTestes(TesteDataFactory.testesCorretos(2));

        return questaoDto;
    }

    public static QuestaoDto questaoTituloVazio(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo("");
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesCorretos(2));

        return questaoDto;
    }

    public static QuestaoDto questaoDescricaoVazia(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao("");
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesCorretos(2));

        return questaoDto;
    }

    public static String questaoDificuldadeVazia(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "",
                    "linguagem": "%s",
                    "codigo": "%s"
                    "testes": [
                        {
                            "exemplos": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10",
                        },
                        {
                            "exemplos": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10",
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), linguagemFaker(), codigoFaker());
    }

    public static String questaoLinguagemVazia(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "",
                    "codigo": "%s"
                    "testes": [
                        {
                            "exemplos": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10",
                        },
                        {
                            "exemplos": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10",
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), codigoFaker());
    }

    public static QuestaoDto questaoComTesteValorDeEntradaVazio() {
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesComValorDeEntradaVazio());

        return questaoDto;
    }

    public static QuestaoDto questaoComTesteRetornoEsperadoVazio() {
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesComRetornoEsperadoVazio());

        return questaoDto;
    }

    public static QuestaoDto questaoComApenasUmTeste() {
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesApenasUmTeste());

        return questaoDto;
    }

    public static QuestaoDto questaoSemTesteOculto() {
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesSomenteExemplos());

        return questaoDto;
    }

    public static QuestaoDto questaoSemTesteExemplo() {
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesSomenteNaoExemplos());

        return questaoDto;
    }

    public static QuestaoDto questaoCom4TestesExemplos() {
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesCom4Exemplos());

        return questaoDto;
    }

    public static QuestaoDto questaoCom8TestesOcultos() {
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesCom8NaoExemplos());

        return questaoDto;
    }

    public static QuestaoDto novaQuestaoLimiteMinimoTestes(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.limiteMinimoTestes());

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
