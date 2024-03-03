package com.vemser.correcao.data.factory;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.dto.EditarQuestaoDto;
import com.vemser.correcao.dto.ListaTodasQuestaoResponseDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.enums.Dificuldade;
import com.vemser.correcao.specs.QuestaoSpecs;
import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

import static io.restassured.RestAssured.given;

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
        return Dificuldade.values()[faker.number().numberBetween(0, 2)];
    }

    public static Linguagem linguagemFaker() {
        return Linguagem.values()[faker.number().numberBetween(0, 1)];
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

    public static QuestaoDto questaoDificuldadeInvalida(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(Dificuldade.IMPOSSIVEL);
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesCorretos(2));

        return questaoDto;
    }

    public static QuestaoDto questaoLinguagemInvalida(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(Linguagem.PYTHON);
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

    public static EditarQuestaoDto questaoEditada(){
        EditarQuestaoDto editarQuestaoDto = new EditarQuestaoDto();
        editarQuestaoDto.setCodigo(codigoFaker());
        editarQuestaoDto.setDescricao(descricaoFaker());
        editarQuestaoDto.setDificuldade(dificuldadeFaker());
        editarQuestaoDto.setLinguagem(linguagemFaker());
        editarQuestaoDto.setTitulo(tituloFaker());
        return editarQuestaoDto;
    }

    public static EditarQuestaoDto questaoEditadaCamposVazios(){
        EditarQuestaoDto editarQuestaoDto = new EditarQuestaoDto();
        editarQuestaoDto.setCodigo("");
        editarQuestaoDto.setDescricao("");
        editarQuestaoDto.setDificuldade(null);
        editarQuestaoDto.setLinguagem(null);
        editarQuestaoDto.setTitulo("");
        return editarQuestaoDto;
    }

    public static EditarQuestaoDto questaoEditadaTituloVazio(){
        EditarQuestaoDto editarQuestaoDto = new EditarQuestaoDto();
        editarQuestaoDto.setCodigo(codigoFaker());
        editarQuestaoDto.setDescricao(descricaoFaker());
        editarQuestaoDto.setDificuldade(dificuldadeFaker());
        editarQuestaoDto.setLinguagem(linguagemFaker());
        editarQuestaoDto.setTitulo("");
        return editarQuestaoDto;
    }

    public static EditarQuestaoDto questaoEditadaCodigoVazio(){
        EditarQuestaoDto editarQuestaoDto = new EditarQuestaoDto();
        editarQuestaoDto.setCodigo("");
        editarQuestaoDto.setDescricao(descricaoFaker());
        editarQuestaoDto.setDificuldade(dificuldadeFaker());
        editarQuestaoDto.setLinguagem(linguagemFaker());
        editarQuestaoDto.setTitulo(tituloFaker());
        return editarQuestaoDto;
    }

    public static EditarQuestaoDto questaoEditadaDescricaoVazia(){
        EditarQuestaoDto editarQuestaoDto = new EditarQuestaoDto();
        editarQuestaoDto.setCodigo(codigoFaker());
        editarQuestaoDto.setDescricao("");
        editarQuestaoDto.setDificuldade(dificuldadeFaker());
        editarQuestaoDto.setLinguagem(linguagemFaker());
        editarQuestaoDto.setTitulo(tituloFaker());
        return editarQuestaoDto;
    }
}
