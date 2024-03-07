package com.vemser.correcao.data.factory;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.dto.questao.EditarQuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.enums.Dificuldade;
import net.datafaker.Faker;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class QuestaoDataFactory {
    private static Faker faker = new Faker(new Locale("PT-BR"));

    private QuestaoDataFactory() {}

    public static String tituloFaker() {
        return faker.lorem().characters(60);
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

    public static QuestaoDto questaoTestesVazio(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesVazio());

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
    public static QuestaoDto questaoExemploInvalido(){
        QuestaoDto questaoDto = new QuestaoDto();
        questaoDto.setTitulo(tituloFaker());
        questaoDto.setDescricao(descricaoFaker());
        questaoDto.setDificuldade(dificuldadeFaker());
        questaoDto.setLinguagem(linguagemFaker());
        questaoDto.setCodigo(codigoFaker());
        questaoDto.setTestes(TesteDataFactory.testesExemploInvalido());

        return questaoDto;
    }

    public static String questaoDificuldadeVazia(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
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
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), codigoFaker());
    }

    public static String questaoExemploTesteVazio(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), linguagemFaker(), codigoFaker());
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

    public static EditarQuestaoDto questaoEditadaDificuldadeVazia(){
        EditarQuestaoDto editarQuestaoDto = new EditarQuestaoDto();
        editarQuestaoDto.setCodigo(codigoFaker());
        editarQuestaoDto.setDescricao(descricaoFaker());
        editarQuestaoDto.setDificuldade(null);
        editarQuestaoDto.setLinguagem(linguagemFaker());
        editarQuestaoDto.setTitulo(tituloFaker());
        return editarQuestaoDto;
    }

    public static EditarQuestaoDto questaoEditadaDificuldadeInvalida(){
        EditarQuestaoDto editarQuestaoDto = new EditarQuestaoDto();
        editarQuestaoDto.setCodigo(codigoFaker());
        editarQuestaoDto.setDescricao(descricaoFaker());
        editarQuestaoDto.setDificuldade(Dificuldade.IMPOSSIVEL);
        editarQuestaoDto.setLinguagem(linguagemFaker());
        editarQuestaoDto.setTitulo(tituloFaker());
        return editarQuestaoDto;
    }

    public static EditarQuestaoDto questaoEditadaLinguagemVazia(){
        EditarQuestaoDto editarQuestaoDto = new EditarQuestaoDto();
        editarQuestaoDto.setCodigo(codigoFaker());
        editarQuestaoDto.setDescricao(descricaoFaker());
        editarQuestaoDto.setDificuldade(dificuldadeFaker());
        editarQuestaoDto.setLinguagem(null);
        editarQuestaoDto.setTitulo(tituloFaker());
        return editarQuestaoDto;
    }

    public static EditarQuestaoDto questaoEditadaLinguagemInvalida(){
        EditarQuestaoDto editarQuestaoDto = new EditarQuestaoDto();
        editarQuestaoDto.setCodigo(codigoFaker());
        editarQuestaoDto.setDescricao(descricaoFaker());
        editarQuestaoDto.setDificuldade(dificuldadeFaker());
        editarQuestaoDto.setLinguagem(Linguagem.PYTHON);
        editarQuestaoDto.setTitulo(tituloFaker());
        return editarQuestaoDto;
    }

    public static String questaoSemCampoTitulo(){
        return String.format("""
                {
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, descricaoFaker(), dificuldadeFaker(), linguagemFaker(), codigoFaker());
    }

    public static String questaoSemCampoDescricao(){
        return String.format("""
                {
                    "titulo": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, tituloFaker(), dificuldadeFaker(), linguagemFaker(), codigoFaker());
    }

    public static String questaoSemCampoDificuldade(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), linguagemFaker(), codigoFaker());
    }

    public static String questaoSemCampoLinguagem(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), codigoFaker());
    }

    public static String questaoSemCampoCodigo(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "testes": [
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), linguagemFaker());
    }

    public static String questaoSemCampoTestes(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s"
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), linguagemFaker(), codigoFaker());
    }

    public static String questaoSemCampoExemploDoTeste(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), linguagemFaker(), codigoFaker());
    }

    public static String questaoEditadaSemCampoExemploDoTeste(Integer testeId, Integer testId2){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10",
                            "testeId": %d
                        },
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10",
                            "testeId": %d
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), linguagemFaker(), codigoFaker(), testeId, testId2);
    }

    public static String questaoEditadaSemCampoIDDoTeste(Integer testId2){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10",
                            "testeId": %d
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), linguagemFaker(), codigoFaker(), testId2);
    }

    public static String questaoEditadaSemCampoRetornoEsperado(String titulo, String dificuldade, String linguagem, Integer testId, Integer testId2){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "SIM",
                            "valorEntrada": "10 10",
                            "testeId": %d
                        },
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10",
                            "testeId": %d
                        }
                    ]
                }
                """, titulo, descricaoFaker(), dificuldade, linguagem, codigoFaker(), testId, testId2);
    }

    public static String questaoEditadaSemCampoValorEntrada(String titulo, String dificuldade, String linguagem, Integer testId, Integer testId2){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20",
                            "testeId": %d
                        },
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10",
                            "testeId": %d
                        }
                    ]
                }
                """, titulo, descricaoFaker(), dificuldade, linguagem, codigoFaker(), testId, testId2);
    }

    public static String questaoSemCampoRetornoEsperadoDoTeste(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "SIM",
                            "valorEntrada": "10 10"
                        },
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), linguagemFaker(), codigoFaker());
    }

    public static String questaoSemCampoValorEntradaDoTeste(){
        return String.format("""
                {
                    "titulo": "%s",
                    "descricao": "%s",
                    "dificuldade": "%s",
                    "linguagem": "%s",
                    "codigo": "%s",
                    "testes": [
                        {
                            "exemplo": "SIM",
                            "retornoEsperado": "20"
                        },
                        {
                            "exemplo": "NAO",
                            "retornoEsperado": "20",
                            "valorEntrada": "10 10"
                        }
                    ]
                }
                """, tituloFaker(), descricaoFaker(), dificuldadeFaker(), linguagemFaker(), codigoFaker());
    }
}
