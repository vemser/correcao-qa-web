package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.QuestaoDataFactory;
import com.vemser.correcao.dto.erro.ErroAlternativoDto;
import com.vemser.correcao.dto.questao.ListaTodasQuestaoResponseDto;
import com.vemser.correcao.dto.questao.QuestaoDto;
import com.vemser.correcao.dto.questao.QuestaoResponseDto;
import com.vemser.correcao.enums.QuestoesParametro;
import com.vemser.correcao.dto.erro.ErroDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Questão - GET")
@DisplayName("Questão - GET")
@Owner("Italo Lacerda | Vitor Nunes")
public class QuestaoGetFuncionalTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA0106] Listar Questões Ao Informar Página e Tamanho Válidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões informando página e tamanho válido retorna 200 e todas as questões cadastradas")
    public void testListarQuestoes_informarPaginaETamanhoValidos_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao("0", "10")
                .then()
                    .statusCode(200)
                .log().all()
                    .extract()
                    .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Testes de listar questões informando página e tamanho válido",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA107] Listar Questões Ao Informar Página Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões informando página inválida retorna 200 e todas as questões cadastradas")
    public void testListarQuestoes_informarPaginaInvalida_esperaErro() {
        String paginaSolicitada = "-1";
        String tamanhoPagina = "10";

        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao(paginaSolicitada, tamanhoPagina)
                .then()
                .statusCode(200)
                .extract()
                .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Testes de listar questões informando página inválida",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(questaoResult.getPageable().getPageNumber(), 0,"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA108] Listar Questões Ao Informar Tamanho Inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões informando tamanho inválido retorna 404 e a mensagem 'Página não encontrada'")
    public void testListarQuestoes_informarTamanhoInvalido_esperaErro() {
        String paginaSolicitada = "0";
        String tamanhoPagina = "-10";

        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao(paginaSolicitada, tamanhoPagina)
                .then()
                .statusCode(200)
                .extract()
                .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Testes de listar questões informando tamanho inválido",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(questaoResult.getPageable().getPageNumber(), 0,"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA109] Listar Questões Ao Não Informar Token")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões não informando o token retorna 403 e a mensagem 'Forbidden'")
    public void testListarQuestoes_naoInformarToken_esperaErro() {
        ErroAlternativoDto erro = QuestaoClient.buscarTodasQuestaoSemEstarLogado("0", "10")
                .then()
                .statusCode(403)
                .extract()
                .as(ErroAlternativoDto.class);

        assertAll("Testes de listar questões não informando token",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertEquals(403, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Forbidden", erro.getError(), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA110] Listar Questões Informando Token De Aluno")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se é possivel listar todas as questões logado como aluno")
    public void testListarQuestoes_informarTokenDeAluno_esperaErro() {
        ErroAlternativoDto erro = QuestaoClient.buscarTodasQuestaoLogadoComoAluno("0", "10")
                .then()
                .statusCode(403)
                .extract()
                .as(ErroAlternativoDto.class);

        assertAll("Testes de listar questões não informando token",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertEquals(403, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Forbidden", erro.getError(), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA111] Listar Questões Ao Informar Página Que Não Existe")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as questões passando uma página que não existe retorna 404 e a mensagem 'Página não encontrada'")
    public void testListarQuestoes_informarPaginaQueNaoExiste_esperaErro() {
        ListaTodasQuestaoResponseDto questao = QuestaoClient.buscarTodasQuestao("0", "10")
                .as(ListaTodasQuestaoResponseDto.class);

        String paginaSolicitada = Integer.toString(questao.getTotalPages() + 1);

        ErroDto erro = QuestaoClient.buscarTodasQuestao(paginaSolicitada, "10")
                .then()
                    .statusCode(404)
                    .extract()
                    .as(ErroDto.class);

        assertAll("Testes de listar questões informando página que não existe",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Página não encontrada", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );

    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA112] Listar Questões Sem Passar Parâmetros")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar todas as questões não informando parâmetros retorna 200 e todas as atividades cadastradas")
    public void testQuestoes_listarQuestoesSemPassarParametro_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao()
                .then()
                .statusCode(200)
                .extract()
                .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Testes de listar questões sem passar parâmetros",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA113] Listar Questões Ao Não Informar Parâmetro de Tamanho Da Página")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar todas as questões sem o tamanho da página a API retorna 200 e a tamanho da página 10")
    public void testQuestoes_listarQuestoesSemParametroDeTamanhoPagina_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao(QuestoesParametro.page ,"0")
                .then()
                .statusCode(200)
                .extract()
                .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Testes de listar questões não informando parâmetro de tamanho da página",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA114] Listar Questões Ao Não Informar Parâmetro De Página Solicitada")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar todas as questões sem a pagina solicitada a API retorna 200 e a página 0")
    public void testQuestoes_listarQuestoesSemParametroDePaginaSolicitada_esperaSucesso() {
        ListaTodasQuestaoResponseDto questaoResult = QuestaoClient.buscarTodasQuestao(QuestoesParametro.size,"10")
                .then()
                .statusCode(200)
                .extract()
                .as(ListaTodasQuestaoResponseDto.class);

        assertAll("Testes de listar questões não informando parâmetro de página solicitada",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTA115] Buscar Questão Por ID Ao Informar ID Existente")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID Existente retorna 200 e todos os dados da questão")
    public void testBuscarQuestaoPorID_informarIDExistente_esperaSucesso() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoCriada = QuestaoClient.cadastrarQuestao(questao)
                .then()
                .   extract().as(QuestaoResponseDto.class);

        QuestaoResponseDto questaoBuscada = QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
                .then()
                    .statusCode(200)
                    .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoCriada.getQuestaoDTO().getQuestaoId());

        assertAll("Testes de buscar questão por ID informando ID existente",
                () -> assertNotNull(questaoBuscada, "Questão buscada não deve ser nula"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getQuestaoId(), questaoBuscada.getQuestaoDTO().getQuestaoId(), "ID da questão buscada deve ser igual ao ID da questão criada"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getTitulo(), questaoBuscada.getQuestaoDTO().getTitulo(), "Título da questão buscada deve ser igual ao título da questão criada"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getDescricao(), questaoBuscada.getQuestaoDTO().getDescricao(), "Descrição da questão buscada deve ser igual a descrição da questão criada"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getDificuldade(), questaoBuscada.getQuestaoDTO().getDificuldade(), "Dificuldade da questão buscada deve ser igual a dificuldade da questão criada"),
                () -> assertEquals(questaoCriada.getQuestaoDTO().getTestes(), questaoBuscada.getQuestaoDTO().getTestes(), "Testes da questão buscada deve ser igual aos testes da questão criada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA116] Buscar Questão Por ID Ao Informar ID Inexistente")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID inexistente retorna 400 e a mensagem 'Questão não encontrada com o ID fornecido'")
    public void testBuscarQuestaoPorID_informarIDInexistente_esperaErro() {

        ErroDto erro = QuestaoClient.buscarQuestaoPorIdInexistente()
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar questão por ID informando ID Inexistente",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Questão não encontrada", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA116] Buscar Questão Por ID Ao Informar ID Maior Que O Limite")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID maior que o limite retorna 400 e a mensagem 'Houve um erro em um conversão. Verifique se os valores estão corretos.'")
    public void testBuscarQuestaoPorId_informarIDMaiorQueOLimite_esperaErro() {
        ErroDto erro = QuestaoClient.buscarQuestaoPorIdMaiorQueOLimite()
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar questão informando ID maior que o limite",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Houve um erro em um conversão. Verifique se os valores estão corretos.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTA117] Buscar Questão Por ID Ao Informar ID Inativo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar uma questão informando ID inativo retorna 404 e a mensagem 'Questão não encontrada'")
    public void testBuscarQuestaoPorID_informarIDInativo_esperaErro() {
        QuestaoDto questao = QuestaoDataFactory.questaoDadosValidos(2);

        QuestaoResponseDto questaoCriada = QuestaoClient.cadastrarQuestao(questao)
                .then()
                    .extract().as(QuestaoResponseDto.class);

        QuestaoClient.excluirQuestao(questaoCriada.getQuestaoDTO().getQuestaoId())
                .then()
                    .statusCode(200);

        ErroDto erro = QuestaoClient.buscarQuestaoPorId(questaoCriada.getQuestaoDTO().getQuestaoId())
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar questão informando ID inativo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Questão não encontrada", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }
}
