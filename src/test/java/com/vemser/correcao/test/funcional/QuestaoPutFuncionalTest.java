package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.EditarQuestaoDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Epic("Funcional Questão - PUT")
@DisplayName("Questão - PUT")
@Owner("Luísa Santos")
public class QuestaoPutFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma questão com todos os campos válidos a API retorna 200 e a todos os dados da questão editada no body")
    public void testEditarQuestao_informarCamposValidos_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditada();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();
        for (int i=0; i<2; i++) {
            questaoEditada.getTestes().get(i).setExemplo(questaoResult.getTestes().get(i).getExemplo());
            questaoEditada.getTestes().get(i).setRetornoEsperado(questaoResult.getTestes().get(i).getRetornoEsperado());
            questaoEditada.getTestes().get(i).setTesteId(questaoEditada.getTestes().get(i).getTesteId());
            questaoEditada.getTestes().get(i).setValorEntrada(questaoEditada.getTestes().get(i).getValorEntrada());
        }

        QuestaoResponseDto response = QuestaoClient.editarQuestao(questaoEditada, questaoId)
            .then()
                .statusCode(200)
                .extract().as(QuestaoResponseDto.class);
        assertNotEquals(questaoResult, response);


    }

}
