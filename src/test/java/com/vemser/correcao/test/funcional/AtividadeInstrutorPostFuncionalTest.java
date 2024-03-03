package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.CriarAtividadeResponseDto;
import com.vemser.correcao.dto.ErrorDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Questões Inativas")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade com questões inativas a API retorna 404 e uma mensagem de erro na resposta")
    public void testCriarAtividade_comQuestoesInativas_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComQuestoesInativas();

        ErrorDto erro = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(404)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar atividade informando questões inativas",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "A questão de ID" + " "  + atividade.getQuestoes().get(0)  + " " + "não existe ou está inativa")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Data Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade com data inválida a API retorna 400 e uma mensagem de erro na resposta")
    public void testCriarAtividade_comDataInvalida_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDataInvalida();

        ErrorDto erro = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(400)
                .extract().as(ErrorDto.class);

        assertAll("Testes de criar atividade informando questões inativas",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("prazoEntrega"), "A data de entrega deve ser no futuro")
        );
    }
}
