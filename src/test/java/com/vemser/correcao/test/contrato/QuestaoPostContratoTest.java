package com.vemser.correcao.test.contrato;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.ErroDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoPostContratoTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos Para Validar Corpo Da Resposta De Sucesso")
    @Description("Teste que verifica se ao criar uma questão informando campos válidos retorna corpo da resposta de sucesso válido")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_informarCamposValidosParaValidarCorpoDaRespostaDeSucesso_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/questao-post-sucesso.json"))
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Não Informar Retorno Esperado Do Teste")
    @Description("Teste que verifica se ao criar uma questão não informando retorno esperado do teste retorna 201 e todos os dados da questão criada no body")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarRetornoEsperadoTeste_esperaSucesso() {
        String questao = QuestaoDataFactory.questaoSemCampoRetornoEsperadoDoTeste();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão não informando retorno esperado",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertTrue(questao.contains(questaoResult.getQuestaoDTO().getTitulo()), "Título da questão deve ser igual"),
                () -> assertTrue(questao.contains(questaoResult.getQuestaoDTO().getDificuldade().toString()), "Dificuldade da questão deve ser igual"),
                () -> assertTrue(questao.contains(questaoResult.getQuestaoDTO().getLinguagem().toString()), "Linguagem da questão deve ser igual"),
                () -> assertEquals(2, questaoResult.getTestes().size(), "Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Não Informar Valor De Entrada Do Teste")
    @Description("Teste que verifica se ao criar uma questão não informando valor de entrada do teste retorna 400 e a mensagem 'Opção não é válida, digite SIM ou NAO'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarValorEntradaTeste_esperaSucesso() {
        String questao = QuestaoDataFactory.questaoSemCampoRetornoEsperadoDoTeste();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão não informando valor de entrada",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertTrue(questao.contains(questaoResult.getQuestaoDTO().getTitulo()), "Título da questão deve ser igual"),
                () -> assertTrue(questao.contains(questaoResult.getQuestaoDTO().getDificuldade().toString()), "Dificuldade da questão deve ser igual"),
                () -> assertTrue(questao.contains(questaoResult.getQuestaoDTO().getLinguagem().toString()), "Linguagem da questão deve ser igual"),
                () -> assertEquals(2, questaoResult.getTestes().size(), "Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Titulo Para Validar Corpo Da Resposta De Erro")
    @Description("Teste que verifica se ao criar uma questão não informando titulo retorna corpo da resposta de erro válido")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarTituloParaValidarCorpoDaRespostaDeErro_esperaErro() {
        String questao = QuestaoDataFactory.questaoSemCampoTitulo();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/questao-post-erro.json"))
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão nao informando titulo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Título não pode ser vazio", erro.getErrors().get("titulo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Titulo")
    @Description("Teste que verifica se ao criar uma questão não informando titulo retorna 400 e a mensagem 'Título não pode ser vazio'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarTitulo_esperaErro() {
        String questao = QuestaoDataFactory.questaoSemCampoTitulo();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão nao informando titulo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Título não pode ser vazio", erro.getErrors().get("titulo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Descrição")
    @Description("Teste que verifica se ao criar uma questão não informando descrição retorna 400 e a mensagem 'Descrição não pode ser vazio'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarDescricao_esperaErro() {
        String questao = QuestaoDataFactory.questaoSemCampoDescricao();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão nao informando descrição",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Descrição não pode ser vazio", erro.getErrors().get("descricao"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Dificuldade")
    @Description("Teste que verifica se ao criar uma questão não informando dificuldade retorna 400 e a mensagem 'Dificuldade não é válida. Valores válidos: FACIL, MEDIO, DIFICIL'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarDificuldade_esperaErro() {
        String questao = QuestaoDataFactory.questaoSemCampoDificuldade();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão nao informando dificuldade",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Dificuldade não é válida. Valores válidos: FACIL, MEDIO, DIFICIL", erro.getErrors().get("dificuldade"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Linguagem")
    @Description("Teste que verifica se ao criar uma questão não informando linguagem retorna 400 e a mensagem 'Linguagem não é válida. Valores válidos: JAVA, JAVASCRIPT'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarLinguagem_esperaErro() {
        String questao = QuestaoDataFactory.questaoSemCampoLinguagem();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão nao informando linguagem",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Linguagem não é válida. Valores válidos: JAVA, JAVASCRIPT", erro.getErrors().get("linguagem"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Codigo")
    @Description("Teste que verifica se ao criar uma questão não informando código retorna 400 e a mensagem 'Código não pode ser vazio'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarCodigo_esperaErro() {
        String questao = QuestaoDataFactory.questaoSemCampoCodigo();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão nao informando código",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Código não pode ser vazio", erro.getErrors().get("codigo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Testes")
    @Description("Teste que verifica se ao criar uma questão não informando testes retorna 400 e a mensagem '?????'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarTestes_esperaErro() {
        String questao = QuestaoDataFactory.questaoSemCampoTestes();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão nao informando testes",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("???????", erro.getErrors().get("testes"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Exemplo Do Teste")
    @Description("Teste que verifica se ao criar uma questão não informando exemplo do teste retorna 400 e a mensagem 'Opção não é válida, digite SIM ou NAO'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_naoInformarExemploTeste_esperaErro() {
        String questao = QuestaoDataFactory.questaoSemCampoExemploDoTeste();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão nao informando exemplo do teste",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Opção não é válida, digite SIM ou NAO", erro.getErrors().get("testes[0].exemplo"), "Mensagem de erro deve ser igual a esperada")
        );
    }
}
