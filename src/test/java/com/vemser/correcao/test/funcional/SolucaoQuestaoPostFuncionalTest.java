package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.CriarAtividadeResponseDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


@Epic("Funcional Solucao Questão - POST")
@DisplayName("Solucao Questão - POST")
@Owner("Vitor Colombo")
public class SolucaoQuestaoPostFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Enviar Solucao De Questao Total")
    @Severity(SeverityLevel.CRITICAL)
    @Description("")
    public void testSolucaoQuestao_mandarSolucaoTotal_esperaSucesso() {

    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Enviar Solucao De Questao Exemplos")
    @Severity(SeverityLevel.CRITICAL)
    @Description("")
    public void testSolucaoQuestao_mandarSolucaoDeExemplos_esperaSucesso() {

    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Enviar Solucao De Questao Exemplos")
    @Severity(SeverityLevel.CRITICAL)
    @Description("")
    public void testSolucaoQuestao_mandarSolucaoDeExemplos_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questao.getQuestaoId());

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidosTrilhaBack(questoes);
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
            .then()
                .statusCode(200)
                .extract().as(CriarAtividadeResponseDto.class);
        System.out.println(atividadeResult.getAtividadeId());

    }
    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Enviar Solucao De Questao Total")
    @Severity(SeverityLevel.CRITICAL)
    @Description("")
    public void testSolucaoQuestao_mandarSolucaoTotal_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);
        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);
        //criar atividade com questao

    }
}
