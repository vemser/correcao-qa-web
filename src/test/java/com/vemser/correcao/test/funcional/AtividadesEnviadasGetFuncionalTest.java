package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.dto.ListaTodasQuestaoResponseDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Funcional Questão - GET")
@DisplayName("Atividades Enviadas - GET")
@Owner("Italo Lacerda")
public class AtividadesEnviadasGetFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Todas as Atividades do Aluno Logado Sem Passar Parametros")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste Listar Todas as Atividades do Aluno Logado Sem Passar Parametros")
    public void testListarQuestoes_informarPaginaETamanhoValidos_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao("0", "10")
                .then()
                .statusCode(200)
                .extract()
                .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Testes de listar questão informando página e tamanho válido",
                () -> assertEquals(questaoResult.getNumberOfElements(), 10, "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(questaoResult.getPageable().getPageNumber(), 0,"Número da página deve ser igual ao esperado")
        );
    }
}
