package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.CriarAtividadeResponseDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Atividades Instrutor - PUT")
@DisplayName("Atividades Instrutor - PUT")
@Owner("Luísa Santos")
public class AtividadeInstrutorPutFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade existente a API retorna 200 e a atividade editada no body")
    public void testEditarAtividade_informarCamposValidos_esperaSucesso() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeComDadosValidos();

        CriarAtividadeResponseDto response = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(200)
                .extract().as(CriarAtividadeResponseDto.class);

        assertAll("Testes de editar atividade informando campos validos",
                () -> assertEquals(response.getAtividadeId(), atividadeResult.getAtividadeId()),
                () -> assertEquals(response.getTitulo(), atividadeEditada.getTitulo()),
                () -> assertEquals(response.getDescricao(), atividadeEditada.getDescricao()),
                () -> assertEquals(response.getQuestoes(), atividadeEditada.getQuestoes()),
                () -> assertTrue(response.getPrazoEntrega().contains(atividadeEditada.getPrazoEntrega().replace("Z", "")))
        );
    }
}
