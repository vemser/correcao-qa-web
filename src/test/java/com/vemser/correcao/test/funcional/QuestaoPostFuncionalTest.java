package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.dto.ErroDto;
import com.vemser.correcao.dto.QuestaoDto;
import com.vemser.correcao.dto.QuestaoResponseDto;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestaoPostFuncionalTest {
    // CENÁRIOS POSITIVOS
    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar Campos Válidos (Espera Sucesso)")
    public void testCriarQuestao_informarCamposValidos_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);
  
        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão informando campos validos",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertEquals(questao.getTitulo(), questaoResult.getQuestaoDTO().getTitulo(), "Título da questão deve ser igual"),
                () -> assertEquals(questao.getDificuldade(), questaoResult.getQuestaoDTO().getDificuldade(), "Dificuldade da questão deve ser igual"),
                () -> assertEquals(questao.getLinguagem(), questaoResult.getQuestaoDTO().getLinguagem(), "Linguagem da questão deve ser igual"),
                () -> assertEquals(questao.getTestes().size(), questaoResult.getTestes().size(), "Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar Código Prévio Vazio (Espera Sucesso)")
    public void testCriarQuestao_informarCodigoPrevioVazio_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoCodigoVazio();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de criar questão informando código prévio vazio",
                () -> assertNotNull(questaoResult.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertEquals(questao.getTitulo(), questaoResult.getQuestaoDTO().getTitulo(), "Título da questão deve ser igual"),
                () -> assertEquals(questao.getDificuldade(), questaoResult.getQuestaoDTO().getDificuldade(), "Dificuldade da questão deve ser igual"),
                () -> assertEquals(questao.getLinguagem(), questaoResult.getQuestaoDTO().getLinguagem(), "Linguagem da questão deve ser igual"),
                () -> assertEquals(questao.getTestes().size(), questaoResult.getTestes().size(), "Quantidade de testes da questão deve ser igual")
        );
    }

    // CENÁRIOS NEGATIVO
    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar Título Vazio (Espera Erro)")
    public void testCriarQuestao_informarTituloVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoTituloVazio();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando título vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar Descrição Vazia (Espera Erro)")
    public void testCriarQuestao_informarDescricaoVazia_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDescricaoVazia();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando descrição vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar Dificuldade Vazia (Espera Erro)")
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
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar Linguagem Vazia (Espera Erro)")
    public void testCriarQuestao_informarLinguagemVazia_esperaErro() {
        String questao = QuestaoDataFactory.questaoLinguagemVazia();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando linguagem vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Não Informar Teste Oculto (Espera Erro)")
    public void testCriarQuestao_naoInformarTesteOculto_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoSemTesteOculto();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão não informando teste oculto",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("É necessário pelo menos um exemplo e um não-exemplo."), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Não Informar Teste De Exemplo (Espera Erro)")
    public void testCriarQuestao_naoInformarTesteDeExemplo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoSemTesteExemplo();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão não informando teste de exemplo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("??????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar 4 Testes De Exemplo (Espera Erro)")
    public void testCriarQuestao_informar4TestesDeExemplo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoCom4TestesExemplos();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando 4 testes de exemplo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("A questão atingiu o limite máximo de exemplos."), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar 8 Testes Ocultos (Espera Erro)")
    public void testCriarQuestao_informar8TestesOcultos_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoCom8TestesOcultos();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando 8 testes oculto",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("A questão atingiu o limite máximo de não-exemplos."), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar Apenas Um Teste (Espera Erro)")
    public void testCriarQuestao_informarApenasUmTeste_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoComApenasUmTeste();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando apenas um teste",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("É necessário pelo menos um exemplo e um não-exemplo."), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar Testes Com Valor De Entrada Vazio (Espera Erro)")
    public void testCriarQuestao_informarTestesComValorDeEntradaVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoComTesteValorDeEntradaVazio();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando teste com valor de entrada vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("??????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @DisplayName("[CTAXXX] Criar Questão - Informar Testes Com Retorno Esperado Vazio (Espera Erro)")
    public void testCriarQuestao_informarTestesComRetornoEsperadoVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoComTesteRetornoEsperadoVazio();

        ErroDto erro = QuestaoClient.cadastrarQuestao(questao).then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de criar questão informando teste com retorno esperado vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertTrue(erro.getErrors().contains("??????????"), "Mensagem de erro deve ser igual ao esperado")
        );
    }
}