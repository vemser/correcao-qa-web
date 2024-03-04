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

@Epic("Funcional Atividades Instrutor - POST")
@DisplayName("Atividades Instrutor - POST")
@Owner("Brayan Benet")
public class AtividadeInstrutorPostFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade com todos os campos válidos a API retorna 200 e a todos os dados da atividade criada no body")
    public void testCriarAtividade_informarCamposValidos_esperaSucesso() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();

        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId());

        assertAll("Testes de criar atividade informando campos validos",
                () -> assertNotNull(atividadeResult.getAtividadeId(), "ID da Atividade não deve ser nulo"),
                () -> assertEquals(atividade.getTitulo(), atividadeResult.getTitulo(), "Título deve ser igual ao esperado"),
                () -> assertEquals(atividade.getDescricao(), atividadeResult.getDescricao(), "Descrição deve ser igual a esperada"),
                () -> assertEquals(atividade.getQuestoes(), atividadeResult.getQuestoes(), "Questões devem ser iguais as esperadas"),
                () -> assertEquals(atividade.getTrilha().name(), atividadeResult.getTrilha(), "Trilha deve ser igual a esperada"),
                () -> assertTrue(atividadeResult.getPrazoEntrega().contains(atividade.getPrazoEntrega().replace("Z", "")), "Prazo de Entrega deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Questões Inativas")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade com questões inativas a API retorna 404 e uma mensagem de erro na resposta")
    public void testCriarAtividade_informarQuestoesInativas_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComQuestoesInativas();

        ErroDto erro = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar atividade informando questões inativas",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "Questão não encontrada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Data Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade com data inválida a API retorna 400 e uma mensagem de erro na resposta")
    public void testCriarAtividade_informarDataInvalida_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDataInvalida();

        ErroDto erro = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar atividade informando prazo de entrega inválido",
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
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade sem atribuir questões a ela a API retorna 400 e uma mensagem de erro na resposta")
    public void testCriarAtividade_naoInformarQuestoes_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeSemAtribuirQuestoes();

        ErroDto erro = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar atividade sem atribuir questões",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("questoes"), "A lista de questões não pode ser nula ou vazia.")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Título Da Atividade")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade sem preencher título a ela a API retorna 400 e uma mensagem de erro na resposta")
    public void testCriarAtividade_naoInformarTituloDaAtividade_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeSemPreencherTitulo();

        ErroDto erro = AtividadesInstrutorClient.criarAtividade(atividade)
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
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Descrição Da Atividade")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade sem preencher descrição a ela a API retorna 400 e uma mensagem de erro na resposta")
    public void testCriarAtividade_naoInformarDescricaoDaAtividade_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeSemPreencherDescricao();

        ErroDto erro = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar atividade sem preencher descrição",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("descricao"), "A descrição é obrigatória")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Edição do Vem Ser Da Atividade")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade sem preencher a edição do VemSer a ela a API retorna 400 e uma mensagem de erro na resposta")
    public void testCriarAtividade_naoInformarEdicaoDoVemSerDaAtividade_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeSemPreencherEdicao();

        ErroDto erro = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar atividade sem preencher edição",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("edicaoVemSer"), "A edição vem ser é obrigatória")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Trilha Da Atividade")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade sem preencher a trilha da atividade a ela a API retorna 400 e uma mensagem de erro na resposta")
    public void testCriarAtividade_naoInformarTrilhaDaAtividade_esperaErro() {
        String atividade = CriarAtividadeDataFactory.atividadeSemPreencherTrilha();

        ErroDto erro = AtividadesInstrutorClient.criarAtividadeString(atividade)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar atividade sem preencher trilha",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("trilha"), "Trilha nula ou inválida. Valores válidos: BACK, FRONT, QA")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Trilha Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade preenchendo trilha inválida a ela a API retorna 400 e uma mensagem de erro na resposta")
    public void testCriarAtividade_informarTrilhaInvalida_esperaErro() {
        String atividade = CriarAtividadeDataFactory.atividadePreenchendoTrilhaInvalida();

        ErroDto erro = AtividadesInstrutorClient.criarAtividadeString(atividade)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar atividade preenchendo trilha inválida",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("trilha"), "Trilha nula ou inválida. Valores válidos: BACK, FRONT, QA")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Edição Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao criar uma atividade preenchendo edicao inválida a ela a API retorna 400 e uma mensagem de erro na resposta")
    public void testCriarAtividade_informarEdicaoInvalida_esperaErro() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComEdicaoInvalida();

        ErroDto erro = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar atividade sem preencher edição",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("edicaoVemSer"), "A edição vem ser deve ser um número")
        );
    }
}
