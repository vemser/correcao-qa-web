package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.CriarAtividadeResponseDto;
import com.vemser.correcao.dto.ErroDto;
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

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Questões Inativas")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade com questões inválidas a API retorna 404 e a mensagem 'Questão não encontrada'")
    public void testEditarAtividade_comQuestoesInativas_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeComQuestoesInativas();

        ErroDto erro = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade com questões inativas",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "Questão não encontrada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar sem enviar o ID a API retorna 400 e o erro 'O Id não pode ser nulo ou vazio'")
    public void testEditarAtividade_naoInformarId_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeComDadosValidos();

        ErroDto erro = AtividadesInstrutorClient.editarAtividadeSemId(atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade sem informar ID",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "O Id não pode ser nulo ou vazio")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Data Inválida")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade com data inválida a API retorna 400 e a mensagem 'A data de entrega deve ser no futuro'")
    public void testEditarAtividade_comDataInvalida_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeComDataInvalida();

        ErroDto erro = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade informando prazo de entrega inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("prazoEntrega"), "A data de entrega deve ser no futuro")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Questões")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade sem atribuir questões a API retorna 400 e a mensagem 'A lista de questões não pode ser nula ou vazia.'")
    public void testEditarAtividade_semQuestoes_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeSemAtribuirQuestoes();

        ErroDto erro = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade sem atribuir questões",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("questoes"), "A lista de questões não pode ser nula ou vazia.")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Titulo")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade sem um título a API retorna 400 e a mensagem 'O título é obrigatório'")
    public void testEditarAtividade_semTitulo_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeSemPreencherTitulo();

        ErroDto erro = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar atividade sem preencher título",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("titulo"), "O título é obrigatório")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Não Informar Descrição")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade sem uma descrição a API retorna 200 e a ")
    public void testEditarAtividade_semDescricao_esperaSucesso() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeSemPreencherTitulo();

        CriarAtividadeResponseDto response = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(CriarAtividadeResponseDto.class);

        assertAll("Testes de criar atividade sem preencher título",
                () -> assertEquals(response.getAtividadeId(), atividadeResult.getAtividadeId()),
                () -> assertEquals(response.getTitulo(), atividadeEditada.getTitulo()),
                () -> assertEquals(response.getDescricao(), atividadeEditada.getDescricao()),
                () -> assertEquals(response.getQuestoes(), atividadeEditada.getQuestoes()),
                () -> assertTrue(response.getPrazoEntrega().contains(atividadeEditada.getPrazoEntrega().replace("Z", "")))
                );
    }
}
