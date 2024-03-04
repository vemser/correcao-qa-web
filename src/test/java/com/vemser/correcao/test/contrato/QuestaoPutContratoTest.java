package com.vemser.correcao.test.contrato;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.data.factory.TesteDataFactory;
import com.vemser.correcao.dto.erro.ErroDto;
import com.vemser.correcao.dto.questao.EditarQuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoResponseDto;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Contrato Questões - PUT")
@DisplayName("Questões - PUT")
@Owner("Gabriel Sales")
public class QuestaoPutContratoTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos Para Validar Corpo Da Resposta De Sucesso")
    @Description("Teste que verifica se ao editar uma questão informando campos válidos retorna corpo válido da resposta de sucesso")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_informarCamposValidosParaValidarCorpoDaRespostaDeSucesso_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditada();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        QuestaoClient.editarQuestao(questaoEditada, questaoId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/questao-put-sucesso.json"))
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Não Informar Retorno Esperado Do Teste")
    @Description("Teste que verifica se ao editar uma questão informando não informando retorno esperado do teste retorna 200 e todos os dados da questão editada no body")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarRetornoEsperadoTeste_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .log().all()
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoEditadaSemCampoRetornoEsperado(
                questaoResult.getQuestaoDTO().getTitulo(),
                questaoResult.getQuestaoDTO().getDificuldade().toString(),
                questaoResult.getQuestaoDTO().getLinguagem().toString(),
                questaoResult.getTestes().get(0).getTesteId(),
                questaoResult.getTestes().get(1).getTesteId()
        );

        QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200)
                .log().all()
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão não informando retorno esperado",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertTrue(questaoEditada.contains(questaoResult.getQuestaoDTO().getTitulo()), "Título da questão deve ser igual"),
                () -> assertTrue(questaoEditada.contains(questaoResult.getQuestaoDTO().getDificuldade().toString()), "Dificuldade da questão deve ser igual"),
                () -> assertTrue(questaoEditada.contains(questaoResult.getQuestaoDTO().getLinguagem().toString()), "Linguagem da questão deve ser igual"),
                () -> assertEquals(2, questaoResult.getTestes().size(), "Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Não Informar Valor De Entrada Do Teste")
    @Description("Teste que verifica se ao editar uma questão não informando valor de entrada do teste retorna 200 e todos os dados da questão editada no body")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarValorEntradaTeste_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .log().all()
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoEditadaSemCampoValorEntrada(
                questaoResult.getQuestaoDTO().getTitulo(),
                questaoResult.getQuestaoDTO().getDificuldade().toString(),
                questaoResult.getQuestaoDTO().getLinguagem().toString(),
                questaoResult.getTestes().get(0).getTesteId(),
                questaoResult.getTestes().get(1).getTesteId()
        );

        QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200)
                .log().all()
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão não informando valor de entrada",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertTrue(questaoEditada.contains(questaoResult.getQuestaoDTO().getTitulo()), "Título da questão deve ser igual"),
                () -> assertTrue(questaoEditada.contains(questaoResult.getQuestaoDTO().getDificuldade().toString()), "Dificuldade da questão deve ser igual"),
                () -> assertTrue(questaoEditada.contains(questaoResult.getQuestaoDTO().getLinguagem().toString()), "Linguagem da questão deve ser igual"),
                () -> assertEquals(2, questaoResult.getTestes().size(), "Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Titulo Para Validar Corpo Da Resposta De Erro")
    @Description("Teste que verifica se ao editar uma questão não informando titulo retorna corpo válido de resposta de erro")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarTituloParaValidarCorpoDaRespostaDeErro_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoSemCampoTitulo();

        QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId()).then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/erro.json"))
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Titulo")
    @Description("Teste que verifica se ao editar uma questão não informando titulo retorna 400 e a mensagem 'Título não pode ser nulo'")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarTitulo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoSemCampoTitulo();

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId()).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão nao informando titulo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Título não pode ser nulo", erro.getErrors().get("titulo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Descrição")
    @Description("Teste que verifica se ao editar uma questão não informando descrição retorna 400 e a mensagem 'Descrição não pode ser nula'")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarDescricao_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoSemCampoDescricao();

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId()).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão nao informando descrição",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Descrição não pode ser nula", erro.getErrors().get("descricao"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Dificuldade")
    @Description("Teste que verifica se ao editar uma questão não informando dificuldade retorna 400 e a mensagem 'Dificuldade não é válida'")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarDificuldade_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoSemCampoDificuldade();

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId()).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão nao informando dificuldade",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Dificuldade não é válida", erro.getErrors().get("dificuldade"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Linguagem")
    @Description("Teste que verifica se ao editar uma questão não informando linguagem retorna 400 e a mensagem 'Linguagem não é válida'")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarLinguagem_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoSemCampoLinguagem();

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId()).then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão nao informando linguagem",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Linguagem não é válida", erro.getErrors().get("linguagem"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Código")
    @Description("Teste que verifica se ao editar uma questão não informando código retorna 400 e a mensagem 'Código não pode ser nulo'")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarCodigo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoSemCampoCodigo();

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId()).then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão nao informando código",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Código não pode ser nulo", erro.getErrors().get("codigo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Testes")
    @Description("Teste que verifica se ao editar uma questão não informando testes retorna 400 e a mensagem 'Testes não pode ser nulo'")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarTestes_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoSemCampoTestes();

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId()).then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão nao informando testes",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Testes não pode ser nulo", erro.getErrors().get("testes"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Exemplo do Teste")
    @Description("Teste que verifica se ao editar uma questão não informando exemplo do teste retorna 400 e a mensagem 'Opção não é válida, digite SIM ou NAO'")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarExemploDoTeste_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .log().all()
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoEditadaSemCampoExemploDoTeste(questaoResult.getTestes().get(0).getTesteId(), questaoResult.getTestes().get(1).getTesteId());

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId()).then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão nao informando exemplo do teste",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Opção não é válida, digite SIM ou NAO", erro.getErrors().get("testes[0].exemplo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar ID Do Teste")
    @Description("Teste que verifica se ao editar uma questão não informando ID do teste retorna 400 e a mensagem 'Id do teste não pode ser nulo.'")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditarQuestao_naoInformarIDDoTeste_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .log().all()
                .extract().as(QuestaoResponseDto.class);

        String questaoEditada = QuestaoDataFactory.questaoEditadaSemCampoIDDoTeste(questaoResult.getTestes().get(1).getTesteId());

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoResult.getQuestaoDTO().getQuestaoId()).then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão nao informando ID do teste",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Id do teste não pode ser nulo.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }
}
