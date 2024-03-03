package com.vemser.correcao.data.factory;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.enums.Trilha;
import net.datafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CriarAtividadeDataFactory {

    private static Faker faker = new Faker(new Locale("PT-BR"));

    private static final  int QUESTAO_INATIVA = 1;

    public static ArrayList<Integer> listaIdDuasQuestoesValidasParaAtividade() {
        ArrayList<Integer> questoes = new ArrayList<>();
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);
        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());

        return questoes;
    }

    public static CriarAtividadeDto atividadeComDadosValidos() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(listaIdDuasQuestoesValidasParaAtividade());
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

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2022-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(listaIdDuasQuestoesValidasParaAtividade());
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

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(listaIdDuasQuestoesValidasParaAtividade());
        criarAtividadeValida.setTitulo("");
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static CriarAtividadeDto atividadeSemPreencherDescricao() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();

        criarAtividadeValida.setDescricao("");
        criarAtividadeValida.setEdicaoVemSer("13");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(listaIdDuasQuestoesValidasParaAtividade());
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static CriarAtividadeDto atividadeSemPreencherEdicao() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(listaIdDuasQuestoesValidasParaAtividade());
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }

    public static String atividadeSemPreencherTrilha(){
        ArrayList<Integer> idQuestoes = listaIdDuasQuestoesValidasParaAtividade();
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
                """,faker.lorem(), idQuestoes.get(0), idQuestoes.get(1),  faker.book().title());
    }

    public static String atividadePreenchendoTrilhaInvalido(){
        ArrayList<Integer> idQuestoes = listaIdDuasQuestoesValidasParaAtividade();
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
                """,faker.lorem(), idQuestoes.get(0), idQuestoes.get(1), faker.book().title());
    }

    public static CriarAtividadeDto atividadeComEdicaoInvalida() {
        CriarAtividadeDto criarAtividadeValida = new CriarAtividadeDto();

        criarAtividadeValida.setDescricao(faker.lorem().paragraph());
        criarAtividadeValida.setEdicaoVemSer("Invalido");
        criarAtividadeValida.setPrazoEntrega("2025-03-01T13:01:41.065Z");
        criarAtividadeValida.setQuestoes(listaIdDuasQuestoesValidasParaAtividade());
        criarAtividadeValida.setTitulo(faker.book().title());
        criarAtividadeValida.setTrilha(Trilha.BACK);

        return criarAtividadeValida;
    }
}
