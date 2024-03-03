package com.vemser.correcao.data.factory;

import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.enums.Trilha;
import net.datafaker.Faker;
import java.util.ArrayList;
import java.util.Locale;

public class CriarAtividadeDataFactory {

    private static Faker faker = new Faker(new Locale("PT-BR"));
    private static final  int QUESTAO_UM_VALIDA = 634;
    private static final  int QUESTAO_DOIS_VALIDA = 631;
    private static final  int QUESTAO_INATIVA = 1;

    public static CriarAtividadeDto atividadeComDadosValidos() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(QUESTAO_UM_VALIDA);
        questoes.add(QUESTAO_DOIS_VALIDA);

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(questoes);
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static CriarAtividadeDto atividadeComQuestoesInativas() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(QUESTAO_INATIVA);

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(questoes);
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static CriarAtividadeDto atividadeComDataInvalida() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(QUESTAO_UM_VALIDA);
        questoes.add(QUESTAO_DOIS_VALIDA);

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2022-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(questoes);
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static CriarAtividadeDto atividadeSemAtribuirQuestoes() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();
        ArrayList<Integer> questoes = new ArrayList<>();

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2022-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(questoes);
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static CriarAtividadeDto atividadeSemPreencherTitulo() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(QUESTAO_UM_VALIDA);
        questoes.add(QUESTAO_DOIS_VALIDA);

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(questoes);
        criarAtividadeValida.setTitulo("");
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static CriarAtividadeDto atividadeSemPreencherDescricao() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(QUESTAO_UM_VALIDA);
        questoes.add(QUESTAO_DOIS_VALIDA);

        criarAtividadeValida.setDescricao("");
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(questoes);
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static CriarAtividadeDto atividadeSemPreencherEdicao() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(QUESTAO_UM_VALIDA);
        questoes.add(QUESTAO_DOIS_VALIDA);

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(questoes);
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static String atividadeSemPreencherTrilha(){
        return String.format("""
                {
                    "descricao": "%s",
                    "edicaoVemSer": "13",
                    "prazoEntrega": "2025-07-20T13:01:41.065Z",
                    "questoes": [
                        %d,
                        %d
                    ],
                    "titulo": "%s",
                    "trilha": ""
                    }
                """,faker.lorem(), QUESTAO_UM_VALIDA, QUESTAO_DOIS_VALIDA,  faker.book().title());
    }

    public static String atividadePreenchendoTrilhaInvalido(){
        return String.format("""
                {
                    "descricao": "%s",
                    "edicaoVemSer": "13",
                    "prazoEntrega": "2025-07-20T13:01:41.065Z",
                    "questoes": [
                        %d,
                        %d
                    ],
                    "titulo": "%s",
                    "trilha": "invalido"
                    }
                """,faker.lorem(), QUESTAO_UM_VALIDA, QUESTAO_DOIS_VALIDA,  faker.book().title());
    }

    public static CriarAtividadeDto atividadeComEdicaoInvalida() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(QUESTAO_UM_VALIDA);
        questoes.add(QUESTAO_DOIS_VALIDA);

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("Invalido");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(questoes);
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }
}
