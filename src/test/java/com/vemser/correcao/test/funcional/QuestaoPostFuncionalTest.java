package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.dto.erro.ErroDto;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoResponseDto;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Questão - POST")
@DisplayName("Questão - POST")
@Owner("Gabriel Sales")
public class QuestaoPostFuncionalTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA015] Informar Campos Válidos")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando campos válidos retorna 201 e todos os dados da questão criada no body")
    public void testCriarQuestao_informarCamposValidos_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão informando campos validos",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertEquals(questaoResult.getQuestaoDTO().getTitulo(), questao.getTitulo(), "Título da questão deve ser igual"),
                () -> assertEquals(questaoResult.getQuestaoDTO().getDificuldade(), questao.getDificuldade(), "Dificuldade da questão deve ser igual"),
                () -> assertEquals(questaoResult.getQuestaoDTO().getLinguagem(), questao.getLinguagem(),"Linguagem da questão deve ser igual"),
                () -> assertEquals(questaoResult.getTestes().size(), questao.getTestes().size(),"Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA016] Informar Testes Com Valor De Entrada Vazio")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando testes com valor de entrada vazio retorna 201 e todos os dados da questão criada no body")
    public void testCriarQuestao_informarTestesComValorDeEntradaVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoComTesteValorDeEntradaVazio();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão informando campos validos",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertEquals(questaoResult.getQuestaoDTO().getTitulo(), questao.getTitulo(), "Título da questão deve ser igual"),
                () -> assertEquals(questaoResult.getQuestaoDTO().getDificuldade(), questao.getDificuldade(), "Dificuldade da questão deve ser igual"),
                () -> assertEquals(questaoResult.getQuestaoDTO().getLinguagem(), questao.getLinguagem(),"Linguagem da questão deve ser igual"),
                () -> assertEquals(questaoResult.getTestes().size(), questao.getTestes().size(),"Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA017] Informar Testes Com Retorno Esperado Vazio")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando testes com retorno vazio retorna 201 e todos os dados da questão criada no body")
    public void testCriarQuestao_informarTestesComRetornoEsperadoVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoComTesteRetornoEsperadoVazio();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão informando campos validos",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertEquals(questaoResult.getQuestaoDTO().getTitulo(), questao.getTitulo(), "Título da questão deve ser igual"),
                () -> assertEquals(questaoResult.getQuestaoDTO().getDificuldade(), questao.getDificuldade(), "Dificuldade da questão deve ser igual"),
                () -> assertEquals(questaoResult.getQuestaoDTO().getLinguagem(), questao.getLinguagem(),"Linguagem da questão deve ser igual"),
                () -> assertEquals(questaoResult.getTestes().size(), questao.getTestes().size(),"Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA018] Informar Código Vazio")
    @Description("Teste que verifica se ao criar uma questão informando código vazio retorna 400 e a mensagem 'Código não pode ser vazio'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_informarCodigoVazio_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoCodigoVazio();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando código prévio vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Código não pode ser vazio", erro.getErrors().get("codigo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA019] Informar Título Vazio")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando título vazio retorna 400 e a mensagem 'Título não pode ser vazio'")
    public void testCriarQuestao_informarTituloVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoTituloVazio();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando título vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Título não pode ser vazio", erro.getErrors().get("titulo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA020] Informar Descrição Vazia")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando descrição vazia retorna 400 e a mensagem 'Descrição não pode ser vazio'")
    public void testCriarQuestao_informarDescricaoVazia_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDescricaoVazia();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando descrição vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Descrição não pode ser vazio", erro.getErrors().get("descricao"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA021] Informar Dificuldade Vazia")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando dificuldade vazia retorna 400 e a mensagem 'Dificuldade não é válida. Valores válidos: FACIL, MEDIO, DIFICIL'")
    public void testCriarQuestao_informarDificuldadeVazia_esperaErro() {
        String questao = QuestaoDataFactory.questaoDificuldadeVazia();

        System.out.println(questao);

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando dificuldade vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Dificuldade não é válida. Valores válidos: FACIL, MEDIO, DIFICIL", erro.getErrors().get("dificuldade"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA022] Informar Linguagem Vazia")
    @Description("Teste que verifica se ao criar uma questão informando linguagem vazia retorna 400 e a mensagem 'Linguagem não é válida. Valores válidos: JAVA, JAVASCRIPT'")
    @Severity(SeverityLevel.CRITICAL)
    public void testCriarQuestao_informarLinguagemVazia_esperaErro() {
        String questao = QuestaoDataFactory.questaoLinguagemVazia();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando linguagem vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Linguagem não é válida. Valores válidos: JAVA, JAVASCRIPT", erro.getErrors().get("linguagem"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA023] Não Informar Teste Oculto")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão não informando teste oculto retorna 400 e a mensagem 'É necessário pelo menos um exemplo e um não-exemplo.'")
    public void testCriarQuestao_naoInformarTesteOculto_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoSemTesteOculto();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão não informando teste oculto",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("É necessário pelo menos um exemplo e um não-exemplo.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA024] Não Informar Teste De Exemplo")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão não informando teste de exemplo retorna 400 e a mensagem 'É necessário pelo menos um exemplo e um não-exemplo.'")
    public void testCriarQuestao_naoInformarTesteDeExemplo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoSemTesteExemplo();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão não informando teste de exemplo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("É necessário pelo menos um exemplo e um não-exemplo.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA025] Informar 4 Testes De Exemplo")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando quatro testes de exemplo retorna 400 e a mensagem 'A questão atingiu o limite máximo de exemplos.'")
    public void testCriarQuestao_informar4TestesDeExemplo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoCom4TestesExemplos();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando quatro testes de exemplo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("A questão atingiu o limite máximo de exemplos.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA026] Informar 8 Testes Ocultos")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando oito testes ocultos retorna 400 e a mensagem 'A questão atingiu o limite máximo de não-exemplos.'")
    public void testCriarQuestao_informar8TestesOcultos_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoCom8TestesOcultos();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando oito testes de ocultos",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("A questão atingiu o limite máximo de não-exemplos.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA027] Informar Apenas Um Teste")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando apenas um teste retorna 400 e a mensagem 'É necessário pelo menos um exemplo e um não-exemplo.'")
    public void testCriarQuestao_informarApenasUmTeste_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoComApenasUmTeste();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando apenas um teste",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("É necessário pelo menos um exemplo e um não-exemplo.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA028] Informar Dificuldade Inválida")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando dificuldade inválida retorna 400 e a mensagem 'Dificuldade não é válida. Valores válidos: FACIL, MEDIO, DIFICIL'")
    public void testCriarQuestao_informarDificuldadeInvalida_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDificuldadeInvalida();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando dificuldade inválida",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Dificuldade não é válida. Valores válidos: FACIL, MEDIO, DIFICIL", erro.getErrors().get("dificuldade"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA029] Informar Linguagem Inválida")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando linguagem inválida retorna 400 e a mensagem 'Trilha não é válida. Valores válidos: JAVA, JAVASCRIPT'")
    public void testCriarQuestao_informarLinguagemInvalida_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoLinguagemInvalida();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando linguagem inválida",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Linguagem não é válida. Valores válidos: JAVA, JAVASCRIPT", erro.getErrors().get("linguagem"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA030] Informar Teste Com Exemplo Vazio")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando teste com exemplo vazio retorna 400 e a mensagem 'Exemplo não pode ser vazio'")
    public void testCriarQuestao_informarTesteComExemploVazio_esperaErro() {
        String questao = QuestaoDataFactory.questaoExemploTesteVazio();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando teste com exemplo vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                // TODO: VERIFICAR MENSAGEM DE ERRO
                () -> assertTrue(erro.getErrors().get("testes[0].exemplo").equals("Exemplo não pode ser vazio") || erro.getErrors().get("testes[0].exemplo").equals("Opção não é válida, digite SIM ou NAO"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA031] Informar Teste Com Exemplo Inválido")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando teste com exemplo inválido retorna 400 e a mensagem 'Opção não é válida, digite SIM ou NAO'")
    public void testCriarQuestao_informarTesteComExemploInvalido_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoExemploInvalido();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando teste com exemplo inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Opção não é válida, digite SIM ou NAO", erro.getErrors().get("testes[0].exemplo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA031] Informar Testes Vazio")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao criar uma questão informando testes vazio retorna 400 e a mensagem 'É necessário pelo menos um exemplo e um não-exemplo.'")
    public void testCriarQuestao_informarTestesVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoTestesVazio();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando teste com exemplo inválido",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("É necessário pelo menos um exemplo e um não-exemplo.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }
}