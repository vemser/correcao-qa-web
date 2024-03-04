package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.CriarAtividadeResponseDto;
import com.vemser.correcao.dto.DeletarAtividadeResponseDto;
import com.vemser.correcao.dto.ErroDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Atividades Instrutor - DELETE")
@DisplayName("Atividades Instrutor - DELETE")
@Owner("Brayan Benet")
public class AtividadeInstrutorDeleteFuncionalTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar ID Existente")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao deletar uma atividade existente por ID a API retorna 200 e a mensagem 'Excluída com sucesso!'")
    public void testDeletarAtividade_informarIDExistente_esperaSucesso() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        DeletarAtividadeResponseDto response = AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
                .then()
                .statusCode(200)
                .extract().as(DeletarAtividadeResponseDto.class);

        assertEquals("Excluída com sucesso!", response.getMessage(), "Mensagem de sucesso deve ser igual ao esperado");
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar ID Inativo")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao deletar uma atividade inativa por ID a API retorna 404 e a mensagem 'Questão já se encontra excluída.'")
    public void testDeletarAtividade_informarIDInativo_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId());

        ErroDto erro = AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar atividade com id inativo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "Questão não encontrada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Deletar Atividade Ao Informar ID inválido")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao deletar uma atividade  por ID inválido a API retorna 400 e a mensagem 'Houve um erro em um conversão. Verifique se os valores estão corretos.'")
    public void testDeletarAtividade_informarIDInvalido_esperaErro() {
        ErroDto erro = AtividadesInstrutorClient.excluirAtividadeString("invalido")
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar atividade com id inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "Houve um erro em um conversão. Verifique se os valores estão corretos.")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX]Informar ID Inexistente")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao deletar uma atividade  por ID inexistente a API retorna 404 e a mensagem 'Nenhuma atividade encontrada.'")
    public void testDeletarAtividade_informarIDInexistente_esperaErro() {
        ErroDto erro = AtividadesInstrutorClient.excluirAtividade(1000000000)
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de deletar atividade com id inexistente",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "Nenhuma atividade encontrada.")
        );
    }
}
