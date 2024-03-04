package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.data.factory.CorrigirAtividadeDataFactory;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.dto.atividade.*;
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
    @Description("Teste que verifica se ao corrigir uma atividade com todos os campos válidos a API retorna 200 e a todos os dados da atividade corrigida no body")
    public void testCorrigirAtividade_informarCamposValidos_esperaSucesso() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .extract().as(CriarAtividadeResponseDto.class);

        PaginacaoListarAtividadePorIdEstagiarioDto listarAtividadeAluno =
                AtividadesInstrutorClient.listarAtividadeEstagiarioPorId(atividadeResult.getAtividadeId())
                        .then()
                        .extract().as(PaginacaoListarAtividadePorIdEstagiarioDto.class);

        CorrigirAtividadeDto correcao = CorrigirAtividadeDataFactory.corrigirComDadosValidos(listarAtividadeAluno.getContent().get(0).getAtividadesEnviadasId());

        CorrigirAtividadeResponseDto correcaoResult = AtividadesInstrutorClient.corrigirAtividade(correcao)
                .then()
                .statusCode(200)
                .log().all()
                .extract().as(CorrigirAtividadeResponseDto.class);

        assertAll("Testes de corrigir atividade com campos válidos",
                () -> assertEquals(correcaoResult.getAtividadesEnviadasId(), listarAtividadeAluno.getContent().get(0).getAtividadesEnviadasId()),
                () -> assertEquals(correcaoResult.getUserName(), listarAtividadeAluno.getContent().get(0).getUserName())

        );

        AtividadesInstrutorClient.excluirAtividade(correcaoResult.getAtividadesId());
    }
}
