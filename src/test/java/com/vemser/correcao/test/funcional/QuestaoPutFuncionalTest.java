package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.data.factory.TesteDataFactory;
import com.vemser.correcao.dto.erro.ErroDto;
import com.vemser.correcao.dto.erro.ErroForbiddenDto;
import com.vemser.correcao.dto.questao.EditarQuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoResponseDto;
import com.vemser.correcao.dto.teste.EditarTesteDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Funcional Questão - PUT")
@DisplayName("Questão - PUT")
@Owner("Luísa Santos")
public class QuestaoPutFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA136] Informar Campos Válidos")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma questão com todos os campos válidos a API retorna 200 e a todos os dados da questão editada no body")
    public void testEditarQuestao_informarCamposValidos_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditada();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        QuestaoResponseDto response = QuestaoClient.editarQuestao(questaoEditada, questaoId)
            .then()
                .statusCode(200)
                .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão informando campos validos",
                () -> assertNotNull(response.getQuestaoDTO().getQuestaoId(), "Id da questão não deve ser nulo"),
                () -> assertEquals(response.getQuestaoDTO().getTitulo(), questaoEditada.getTitulo(), "Título da questão deve ser igual"),
                () -> assertEquals(response.getQuestaoDTO().getDificuldade(), questaoEditada.getDificuldade(), "Dificuldade da questão deve ser igual"),
                () -> assertEquals(response.getQuestaoDTO().getLinguagem(), questaoEditada.getLinguagem(),"Linguagem da questão deve ser igual"),
                () -> assertEquals(response.getTestes().size(), questaoEditada.getTestes().size(),"Quantidade de testes da questão deve ser igual")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA137] Informar Código Vazio")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao editar uma questão com o campo código vazio a API retorna 400 e a mensagem 'Código não pode ser nulo'")
    public void testEditarQuestao_informarCodigoVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);
        EditarTesteDto testeDto = new EditarTesteDto();

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditadaCodigoVazio();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoId)
                .then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão informando código vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Código não pode ser nulo", erro.getErrors().get("codigo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA138] Informar Título Vazio")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao editar uma questão com o campo título vazio a API retorna 400 e a mensagem 'Título não pode ser nulo'")
    public void testEditarQuestao_informarTituloVazio_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditadaTituloVazio();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoId)
            .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão informando título vazio",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Título não pode ser nulo", erro.getErrors().get("titulo"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA139] Informar Descrição Vazia")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao editar uma questão com o campo descrição vazio a API retorna 400 e a mensagem 'Descrição não pode ser nula'")
    public void testEditarQuestao_informarDescricaoVazia_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditadaDescricaoVazia();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoId)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão informando descrição vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Descrição não pode ser nula", erro.getErrors().get("descricao"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA140] Informar Dificuldade Vazia")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao editar uma questão com o campo dificuldade vazia a API retorna 400 e a mensagem 'Dificuldade não pode ser nula'")
    public void testEditarQuestao_informarDificuldadeVazia_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditadaDificuldadeVazia();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoId)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão informando dificuldade vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Dificuldade não é válida", erro.getErrors().get("dificuldade"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA141] Informar Dificuldade Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao editar uma questão com o campo dificuldade inválida a API retorna 400 e a mensagem 'Dificuldade não é válida'")
    public void testEditarQuestao_informarDificuldadeInvalida_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditadaDificuldadeInvalida();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoId)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);
        assertAll("Testes de editar questão informando dificuldade inválida",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Dificuldade não é válida", erro.getErrors().get("dificuldade"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA142] Informar Linguagem Vazia")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao editar uma questão com o campo linguagem vazio a API retorna 400 e a mensagem 'Linguagem não é válida'")
    public void testEditarQuestao_informarLinguagemVazia_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditadaLinguagemVazia();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoId)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão informando linguagem vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Linguagem não é válida", erro.getErrors().get("linguagem"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA143] Informar Linguagem Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao editar uma questão com o campo linguagem inválido a API retorna 400 e a mensagem 'Linguagem não é válida'")
    public void testEditarQuestao_informarLinguagemInvalida_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditadaLinguagemInvalida();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        ErroDto erro = QuestaoClient.editarQuestao(questaoEditada, questaoId)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de editar questão informando linguagem vazia",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Linguagem não é válida", erro.getErrors().get("linguagem"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA144] Editar Sem Autenticação")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar questão sem ter autenticação a API retorna 403 e erro 'Você não tem autorização para acessar este serviço'")
    public void testEditarQuestao_naoInformarAutenticacao_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(0);

        QuestaoResponseDto questaoResult = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .statusCode(201)
                .extract().as(QuestaoResponseDto.class);

        EditarQuestaoDto questaoEditada = QuestaoDataFactory.questaoEditada();
        Integer questaoId = questaoResult.getQuestaoDTO().getQuestaoId();

        questaoEditada.setTestes(TesteDataFactory.editarTesteValido(questaoResult));

        ErroForbiddenDto erro = QuestaoClient.editarQuestaoSemAutenticacao(questaoEditada, questaoId)
                .then()
                .statusCode(403)
                .extract().as(ErroForbiddenDto.class);

        QuestaoClient.excluirQuestao(questaoResult.getQuestaoDTO().getQuestaoId());


        assertAll("Testes de editar atividade sem autenticação",
                () -> assertEquals(erro.getStatus(), 403, "Status da erro não deve ser nulo"),
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getPath()),
                () -> assertEquals(erro.getError(),"Você não tem autorização para acessar este serviço" )
        );
    }
}
