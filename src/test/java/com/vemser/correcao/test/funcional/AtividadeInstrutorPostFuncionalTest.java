package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.CriarAtividadeResponseDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Funcional Atividades Instrutor - POST")
@DisplayName("Atividades Instrutor - POST")
@Owner("Brayan Benet")
public class AtividadeInstrutorPostFuncionalTest {

    //Ajustar validação do prazo de entrega
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade com todos os campos válidos a API retorna 200 e a todos os dados da atividade criada no body")
    public void testCriarAtividade_informarCamposValidos_esperaSucesso() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();

        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(CriarAtividadeResponseDto.class);

        assertAll("Testes de criar atividade informando campos validos",
                () -> assertNotNull(atividadeResult.getAtividadeId()),
                () -> assertEquals(atividade.getTitulo(),atividadeResult.getTitulo()),
                () -> assertEquals(atividade.getDescricao(),atividadeResult.getDescricao()),
                () -> assertEquals(atividade.getQuestoes(),atividadeResult.getQuestoes()),
                () -> assertEquals(atividade.getTrilha().name(),atividadeResult.getTrilha())
//                () -> assertEquals(atividade.getPrazoEntrega(),atividadeResult.getPrazoEntrega())
        );
    }
}
