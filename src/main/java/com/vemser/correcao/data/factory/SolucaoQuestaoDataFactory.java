package com.vemser.correcao.data.factory;

import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.dto.solucao.SolucaoQuestaoDto;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

public class SolucaoQuestaoDataFactory {
    private static Faker faker = new Faker(new Locale("PT-BR"));

    private SolucaoQuestaoDataFactory() {}

    public static Linguagem linguagemFaker() {
        return Linguagem.values()[faker.number().numberBetween(0, 1)];
    }

    public static String codigoFaker() {
        return faker.lorem().sentence();
    }

    public static SolucaoQuestaoDto solucaoSemIdQuestao(){
        SolucaoQuestaoDto solucao = new SolucaoQuestaoDto();
        solucao.setAtividadeEnviadaId(1);
        solucao.setCodigo(codigoFaker());
        solucao.setLinguagem(Linguagem.JAVA);

        return solucao;
    }

    public static SolucaoQuestaoDto solucaoJavaValida(Integer atividadesEnviadasId, ArrayList<Integer> questoes){
        SolucaoQuestaoDto solucao = new SolucaoQuestaoDto();
        solucao.setAtividadeEnviadaId(atividadesEnviadasId);
        solucao.setCodigo(CompiladorDataFactory.compiladorJavaValido().getCodigo());
        solucao.setLinguagem(Linguagem.JAVA);
        solucao.setQuestaoId(questoes.get(0));

        return solucao;
    }
    public static SolucaoQuestaoDto solucaoJavaScriptValida(Integer atividadesEnviadasId, ArrayList<Integer> questoes) {
        SolucaoQuestaoDto solucao = new SolucaoQuestaoDto();
        solucao.setAtividadeEnviadaId(atividadesEnviadasId);
        solucao.setCodigo(CompiladorDataFactory.compiladorJavascriptValido().getCodigo());
        solucao.setLinguagem(Linguagem.JAVASCRIPT);
        solucao.setQuestaoId(questoes.get(0));

        return solucao;
    }
    public static SolucaoQuestaoDto solucaoComIdQuestaoInexistente(Integer atividadesEnviadasId, QuestaoDto questao) {
        SolucaoQuestaoDto solucao = new SolucaoQuestaoDto();
        solucao.setAtividadeEnviadaId(atividadesEnviadasId);
        solucao.setCodigo(questao.getCodigo());
        solucao.setLinguagem(questao.getLinguagem());
        solucao.setQuestaoId(-1);

        return solucao;
    }
}
