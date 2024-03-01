package com.vemser.correcao.data.factory;

import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.TesteDto;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TesteDataFactory {
    private static Faker faker = new Faker(new Locale("PT-BR"));

    private TesteDataFactory() {}

    //gerador de respostas certas
    //cria o numero de testes desejados + 1 teste visivel e 1 oculto
    public static List<TesteDto> criarListaDeTestesCorretos(int quantidade) {
        List<TesteDto> testes = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            testes.add(criarTesteAleatorioDeSoma());
        }

        testes.add(testeSomaExemplo());
        testes.add(testeSomaNaoExemplo());
        return testes;
    }

    public static TesteDto criarTesteAleatorioDeSoma() {
        String exemplo = faker.options().option("SIM", "NAO");
        int valor1 = faker.number().numberBetween(0, 100);
        int valor2 = faker.number().numberBetween(0, 100);
        String valorEntrada = valor1 + " " + valor2;
        int resultado = valor1 + valor2;
        String retornoEsperado = String.valueOf(resultado);
        return new TesteDto(exemplo, retornoEsperado, valorEntrada);
    }

    public static List<TesteDto> criarListaDeTestesAleatorios(int quantidade) {
        List<TesteDto> testes = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            testes.add(novosTestesAleatorios());
        }
        return testes;
    }

    public static TesteDto novosTestesAleatorios(){
        return new TesteDto(
                faker.lorem().sentence(),
                faker.lorem().sentence(),
                faker.lorem().sentence()
        );
    }

    public static TesteDto testesInvalidos(){
        return new TesteDto(
                null,
                null,
                null
        );
    }

    public static TesteDto testeSomaNaoExemplo(){
        return new TesteDto(
                "NAO",
                "3",
                "1 2"
        );
    }

    public static TesteDto testeSomaExemplo(){
        return new TesteDto(
                "SIM",
                "3",
                "1 2"
        );
    }

    public static TesteDto testeComResultadoErrado(){
        return new TesteDto(
                "SIM",
                "4",
                "1 2"
        );
    }
}
