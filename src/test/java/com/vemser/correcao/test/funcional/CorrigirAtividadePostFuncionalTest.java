package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.data.factory.CorrigirAtividadeDataFactory;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.dto.CorrigirAtividadeDto;
import com.vemser.correcao.dto.CorrigirAtividadeResponseDto;
import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.CriarAtividadeResponseDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Atividades Instrutor - POST (CORRIGIR ATIVIDADE)")
@DisplayName("Atividades Instrutor - POST (CORRIGIR ATIVIDADE)")
@Owner("Brayan Benet")
public class CorrigirAtividadePostFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao corrigir uma atividade com todos os campos válidos a API retorna 201 e a todos os dados da atividade corrigida no body")
    public void testCorrigirAtividade_informarCamposValidos_esperaSucesso() {
        CorrigirAtividadeDto correcao = CorrigirAtividadeDataFactory.corrigirComDadosValidos();

        CorrigirAtividadeResponseDto correcaoResult = AtividadesInstrutorClient.corrigirAtividade(correcao)
                .then()
                .statusCode(200)
                .log().all()
                .extract().as(CorrigirAtividadeResponseDto.class);

        assertAll("Testes de corrigir atividade informando campos validos",
                () -> assertEquals("CORRIGIDO", correcaoResult.getStatus()),
                () -> assertEquals(correcao.getFeedbackProfessor(), correcaoResult.getFeedbackInstrutor()),
                () -> assertEquals(correcao.getNotaTestes(), correcaoResult.getNotaTestes())
        );
    }
}
