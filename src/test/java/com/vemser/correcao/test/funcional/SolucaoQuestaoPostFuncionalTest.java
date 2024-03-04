package com.vemser.correcao.test.funcional;

import com.vemser.compilador.data.factory.CompiladorDataFactory;
import com.vemser.compilador.enums.Linguagem;
import com.vemser.correcao.client.AtividadesEnviadaClient;
import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.client.SolucaoQuestaoClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.data.factory.SolucaoQuestaoDataFactory;
import com.vemser.correcao.dto.atividade.CriarAtividadeDto;
import com.vemser.correcao.dto.atividade.CriarAtividadeResponseDto;
import com.vemser.correcao.dto.atividade.PaginacaoAtividadeEnviadaDto;
import com.vemser.correcao.dto.erro.ErroDto;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoResponseDto;
import com.vemser.correcao.dto.solucao.SolucaoQuestaoDto;
import com.vemser.correcao.dto.solucao.SolucaoQuestaoResponseDto;
import com.vemser.correcao.dto.solucao.SolucaoQuestaoTestarResponseDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


@Epic("Funcional Solucao Questão - POST")
@DisplayName("Solucao Questão - POST")
@Owner("Vitor Colombo")
public class SolucaoQuestaoPostFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Enviar Solucao De Questao Total Com Codigo JAVA Válido")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao enviar uma solução de questão total com codigo java valido. A API retorna 200 e os dados da solução.")
    public void testSolucaoQuestao_mandarSolucaoTotalEnvioJAVA_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidosTrilhaBack(questoes);
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
            .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        Integer atividadeId = atividadeResult.getAtividadeId();

        PaginacaoAtividadeEnviadaDto atividadesEnviadas = AtividadesEnviadaClient.listarAtividadesDoAluno()
            .then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeEnviadaDto.class);
        Integer atividadesEnviadasId = atividadesEnviadas.getContent().get(0).getAtividadesEnviadasId();

        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoJavaValida(atividadesEnviadasId, questoes);
        SolucaoQuestaoResponseDto solucaoResponse = SolucaoQuestaoClient.testarSolucaoCompleta(solucao)
            .then()
                .statusCode(200)
                .extract()
                .as(SolucaoQuestaoResponseDto.class);

        assertAll(
                () -> assertNotNull(solucaoResponse.getAtividadeEnviadaId()),
                () -> assertNotNull(solucaoResponse.getQuestaoId()),
                () -> assertNotNull(solucaoResponse.getCodigo()),
                () -> assertEquals(solucaoResponse.getAtividadeEnviadaId(), atividadesEnviadasId),
                () -> assertEquals(Linguagem.JAVA, solucaoResponse.getLinguagem()),
                () -> assertEquals(solucaoResponse.getQuestaoId(), questoes.get(0)),
                () -> assertEquals(solucaoResponse.getCodigo(), CompiladorDataFactory.compiladorJavaValido().getCodigo())
        );

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200);
        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
                .then()
                .statusCode(200);
    }
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Enviar Solucao De Questao Total Com Codigo JavaScript Válido")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao enviar uma solução de questão total com codigo javascript valido. A API retorna 200 e os dados da solução.")
    public void testSolucaoQuestao_mandarSolucaoTotalEnvioJS_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidosTrilhaBack(questoes);
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
            .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        Integer atividadeId = atividadeResult.getAtividadeId();

        PaginacaoAtividadeEnviadaDto atividadesEnviadas = AtividadesEnviadaClient.listarAtividadesDoAluno()
            .then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeEnviadaDto.class);
        Integer atividadesEnviadasId = atividadesEnviadas.getContent().get(0).getAtividadesEnviadasId();

        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoJavaScriptValida(atividadesEnviadasId, questoes);
        SolucaoQuestaoResponseDto solucaoResponse = SolucaoQuestaoClient.testarSolucaoCompleta(solucao)
            .then()
                .statusCode(200)
                .extract()
                .as(SolucaoQuestaoResponseDto.class);

        assertAll(
                () -> assertNotNull(solucaoResponse.getAtividadeEnviadaId()),
                () -> assertNotNull(solucaoResponse.getQuestaoId()),
                () -> assertNotNull(solucaoResponse.getCodigo()),
                () -> assertEquals(solucaoResponse.getAtividadeEnviadaId(), atividadesEnviadasId),
                () -> assertEquals(Linguagem.JAVASCRIPT, solucaoResponse.getLinguagem()),
                () -> assertEquals(solucaoResponse.getQuestaoId(), questoes.get(0)),
                () -> assertEquals(solucaoResponse.getCodigo(), CompiladorDataFactory.compiladorJavascriptValido().getCodigo())
        );

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);
        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
            .then()
                .statusCode(200);
    }
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Enviar Teste de Solucao De Questao JAVA Válido")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao testar uma solução de questão com exemplos JAVA. A API retorna 200 e os testes totais e testes passados.")
    public void testSolucaoQuestao_mandarSolucaoDeExemplosTeste_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);
        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidosTrilhaBack(questoes);
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
            .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        Integer atividadeId = atividadeResult.getAtividadeId();

        PaginacaoAtividadeEnviadaDto atividadesEnviadas = AtividadesEnviadaClient.listarAtividadesDoAluno()
            .then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeEnviadaDto.class);
        Integer atividadesEnviadasId = atividadesEnviadas.getContent().get(0).getAtividadesEnviadasId();

        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoJavaValida(atividadesEnviadasId, questoes);
        SolucaoQuestaoTestarResponseDto solucaoResponse = SolucaoQuestaoClient.testarSolucaoExemplos(solucao)
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(SolucaoQuestaoTestarResponseDto.class);
        Integer testesTotais = Integer.valueOf(solucaoResponse.getTestesTotais());
        assertAll(
                () -> assertNotNull(solucaoResponse.getTestesTotais()),
                () -> assertNotNull(solucaoResponse.getTestesPassados()),
                () -> assertTrue(testesTotais > 1)
        );

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);
        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
            .then()
                .statusCode(200);
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Enviar Teste de Solucao De Questao com questao nao pertencente a atividade")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao testar uma solução de questão com exemplos a API retorna 400 e a mensagem 'Esta questão não pertence a esta atividade.'")
    public void testSolucaoQuestao_mandarSolucaoDeExemplosComQuestaoNaoAssociada_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
            .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        PaginacaoAtividadeEnviadaDto atividadesEnviadas = AtividadesEnviadaClient.listarAtividadesDoAluno()
            .then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeEnviadaDto.class);
        Integer atividadesEnviadasId = atividadesEnviadas.getContent().get(0).getAtividadesEnviadasId();

        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());
        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoJavaValida(atividadesEnviadasId, questoes);
        ErroDto erro = SolucaoQuestaoClient.testarSolucaoExemplos(solucao)
            .then()
                .statusCode(400)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de enviar com questao nao pertencente a atividade",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Esta questão não pertence a esta atividade.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200);
        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
                .then()
                .statusCode(200);
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Enviar Solucao De Questao Total com questao nao pertencente a atividade")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar uma solução de questão total a API retorna 400 e a mensagem 'Esta questão não pertence a esta atividade.'")
    public void testSolucaoQuestao_mandarSolucaoTotalComQuestaoNaoAssociada_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
            .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        PaginacaoAtividadeEnviadaDto atividadesEnviadas = AtividadesEnviadaClient.listarAtividadesDoAluno()
            .then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeEnviadaDto.class);
        Integer atividadesEnviadasId = atividadesEnviadas.getContent().get(0).getAtividadesEnviadasId();

        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());
        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoJavaValida(atividadesEnviadasId, questoes);

        ErroDto erro = SolucaoQuestaoClient.testarSolucaoCompleta(solucao)
            .then()
                .statusCode(400)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de enviar com questao nao pertencente a atividade",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Esta questão não pertence a esta atividade.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);
        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
            .then()
                .statusCode(200);
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Enviar Teste de Solucao De Questao com questao não existente")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao testar uma solução de questão com exemplos a API retorna 404 e a mensagem 'Questão não encontrada'")
    public void testSolucaoQuestao_mandarSolucaoDeExemplosComQuestaoNaoExistente_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidosTrilhaBack(questoes);
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        Integer atividadeId = atividadeResult.getAtividadeId();

        PaginacaoAtividadeEnviadaDto atividadesEnviadas = AtividadesEnviadaClient.listarAtividadesDoAluno()
                .then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeEnviadaDto.class);
        Integer atividadesEnviadasId = atividadesEnviadas.getContent().get(0).getAtividadesEnviadasId();

        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoComIdQuestaoInexistente(atividadesEnviadasId, questao);
        ErroDto erro = SolucaoQuestaoClient.testarSolucaoExemplos(solucao)
                .then()
                .statusCode(404)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de enviar questao inexistente para solucao",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Questão não encontrada", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200);
        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
                .then()
                .statusCode(200);
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Enviar Solucao De Questao Total com questao não existente")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar uma solução de questão total a API retorna 404 e a mensagem 'Questão não encontrada'")
    public void testSolucaoQuestao_mandarSolucaoTotalComQuestaoNaoExistente_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidosTrilhaBack(questoes);
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        Integer atividadeId = atividadeResult.getAtividadeId();

        PaginacaoAtividadeEnviadaDto atividadesEnviadas = AtividadesEnviadaClient.listarAtividadesDoAluno()
                .then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeEnviadaDto.class);
        Integer atividadesEnviadasId = atividadesEnviadas.getContent().get(0).getAtividadesEnviadasId();

        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoComIdQuestaoInexistente(atividadesEnviadasId, questao);
        ErroDto erro = SolucaoQuestaoClient.testarSolucaoCompleta(solucao)
            .then()
                .statusCode(404)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de enviar questao inexistente para solucao",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Questão não encontrada", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200);
        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
                .then()
                .statusCode(200);
    }
    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Enviar Solucao De Questao Total Sem id de Questao")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar uma requisição sem id de questao a API retorna 404 e a mensagem 'Houve um erro ao tentar ler a requisição. Verifique se o corpo da requisição está correto'")
    public void testSolucaoQuestao_mandarSolucaoTotalSemIdQuestao_esperaErro() {
        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoSemIdQuestao();

        ErroDto erro = SolucaoQuestaoClient.testarSolucaoCompleta(solucao)
            .then()
                .statusCode(404)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de enviar questao inexistente para solucao",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Houve um erro ao tentar ler a requisição. Verifique se o corpo da requisição está correto", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Enviar Teste de Solucao De Questao com trilha errada")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao testar uma solução da trilha errada a API retorna 400 e a mensagem 'Esta atividade não pertence a este usuário.'")
    public void testSolucaoQuestao_mandarSolucaoDeExemplosComTrilhaErrada_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);
        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidosTrilhaFront(questoes);
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        Integer atividadeId = atividadeResult.getAtividadeId();

        PaginacaoAtividadeEnviadaDto atividadesEnviadas = AtividadesEnviadaClient.listarAtividadesDoAluno()
                .then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeEnviadaDto.class);
        Integer atividadesEnviadasId = atividadesEnviadas.getContent().get(0).getAtividadesEnviadasId();

        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoJavaScriptValida(atividadesEnviadasId, questoes);
        ErroDto erro = SolucaoQuestaoClient.testarSolucaoExemplos(solucao)
                .then()
                .statusCode(400)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de enviar solução com trilha errada",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Esta atividade não pertence a este usuário.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(200);
        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
                .then()
                .statusCode(200);
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Enviar Solucao De Questao Total com trilha errada")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao enviar uma solução da trilha errada a API retorna 400 e a mensagem 'Esta atividade não pertence a este usuário.'")
    public void testSolucaoQuestao_mandarSolucaoTotalComTrilhaErrada_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
            .then()
                .statusCode(201)
                .extract()
                .as(QuestaoResponseDto.class);
        ArrayList<Integer> questoes = new ArrayList<>();
        questoes.add(questaoResult.getQuestaoDTO().getQuestaoId());

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidosTrilhaFront(questoes);
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
            .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        PaginacaoAtividadeEnviadaDto atividadesEnviadas = AtividadesEnviadaClient.listarAtividadesDoAluno()
            .then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeEnviadaDto.class);
        Integer atividadesEnviadasId = atividadesEnviadas.getContent().get(0).getAtividadesEnviadasId();

        SolucaoQuestaoDto solucao = SolucaoQuestaoDataFactory.solucaoJavaScriptValida(atividadesEnviadasId, questoes);
        ErroDto erro = SolucaoQuestaoClient.testarSolucaoCompleta(solucao)
            .then()
                .statusCode(400)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de enviar solução com trilha errada",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Esta atividade não pertence a este usuário.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId())
            .then()
                .statusCode(200);
        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId())
            .then()
                .statusCode(200);
    }
}

