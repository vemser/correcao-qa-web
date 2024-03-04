package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.dto.atividade.CriarAtividadeDto;
import com.vemser.correcao.dto.atividade.CriarAtividadeResponseDto;
import com.vemser.correcao.dto.atividade.PaginacaoAtividadeInstrutorDto;
import com.vemser.correcao.dto.atividade.PaginacaoListarAtividadePorIdEstagiarioDto;
import com.vemser.correcao.dto.erro.ErroAlternativoDto;
import com.vemser.correcao.dto.erro.ErroDto;
import com.vemser.correcao.enums.QuestoesParametro;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Epic("Funcional Atividades Instrutor - GET")
@DisplayName("Atividades Instrutor - GET")
@Owner("Gabriel Sales")
public class AtividadeInstrutorGetFuncionalTest {
    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Atividades Do Instrutor Ao Informar Página e Tamanho Válidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as atividades do instrutor informando página e tamanho válido retorna 200 e todas as atividades cadastradas")
    public void testListarAtividadesInstrutor_informarPaginaETamanhoValidos_esperaSucesso() {
        String page = "0";
        String size = "10";

        PaginacaoAtividadeInstrutorDto questaoResult = AtividadesInstrutorClient.listarAtividades(page, size).then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeInstrutorDto.class);

        assertAll("Testes de listar atividades do instrutor informando página e tamanho válido",
                () -> assertEquals(Integer.valueOf(size), questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(Integer.valueOf(page), questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Atividades Do Instrutor Ao Informar Página Inválida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as atividades do instrutor informando página inválida retorna 200 e todas as atividades cadastradas")
    public void testListarAtividadesInstrutor_informarPaginaInvalida_esperaSucesso() {
        String page = "-1";
        String size = "10";

        PaginacaoAtividadeInstrutorDto questaoResult = AtividadesInstrutorClient.listarAtividades(page, size).then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeInstrutorDto.class);

        assertAll("Testes de listar atividades do instrutor informando página inválida",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Atividades Do Instrutor Ao Informar Tamanho Inválido")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as atividades do instrutor informando tamanho inválido retorna 200 e todas as atividades cadastradas")
    public void testListarAtividadesInstrutor_informarTamanhoInvalida_esperaSucesso() {
        String page = "0";
        String size = "-10";

        PaginacaoAtividadeInstrutorDto questaoResult = AtividadesInstrutorClient.listarAtividades(page, size).then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeInstrutorDto.class);

        assertAll("Testes de listar atividades do instrutor informando tamanho inválido",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Atividades Do Instrutor Sem Passar Parâmetros")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as atividades do instrutor não informando parâmetros retorna 200 e todas as atividades cadastradas")
    public void testListarAtividadesInstrutor_informarSemPassar_esperaSucesso() {
        PaginacaoAtividadeInstrutorDto questaoResult = AtividadesInstrutorClient.listarAtividades().then()
                .statusCode(200)
                .extract().as(PaginacaoAtividadeInstrutorDto.class);

        assertAll("Testes de listar atividades do instrutor informando tamanho inválido",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Atividades Do Instrutor Ao Não Informar Parâmetro De Tamanho Da Página")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as atividades do instrutor não informando parâmetro de tamanho da página retorna 200 e todas as atividades cadastradas")
    public void testListarAtividadesInstrutor_naoInformarParametroDeTamanhoPagina_esperaSucesso() {
        PaginacaoAtividadeInstrutorDto questaoResult = AtividadesInstrutorClient.listarAtividades(QuestoesParametro.page, "").then()
                .statusCode(200)
                .log().all()
                .extract().as(PaginacaoAtividadeInstrutorDto.class);

        assertAll("Testes de listar atividades do instrutor não informando tamanho da página",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Atividades Do Instrutor Ao Não Informar Parâmetro de Página Solicitada")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as atividades do instrutor não informando parâmetro de tamanho da página retorna 200 e todas as atividades cadastradas")
    public void testListarAtividadesInstrutor_naoInformarParametroDePaginaSolicitada_esperaSucesso() {
        PaginacaoAtividadeInstrutorDto questaoResult = AtividadesInstrutorClient.listarAtividades(QuestoesParametro.size, "").then()
                .statusCode(200)
                .log().all()
                .extract().as(PaginacaoAtividadeInstrutorDto.class);

        assertAll("Testes de listar atividades do instrutor não informando tamanho da página",
                () -> assertEquals(10, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar Atividades Do Instrutor Ao Não Informar Token")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as atividades do instrutor não informando o token retorna 403 e a mensagem 'Forbidden'")
    public void testListarAtividadesInstrutor_naoInformarToken_esperaSucesso() {
        ErroAlternativoDto erro = AtividadesInstrutorClient.listarAtividadesSemAutenticacao().then()
                .statusCode(403)
                .extract().as(ErroAlternativoDto.class);

        assertAll("Testes de listar atividades do instrutor não informando token",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertEquals(403, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Forbidden", erro.getError(), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar Atividades Do Instrutor Ao Não Informar Token")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as atividades do instrutor não informando o token retorna 403 e a mensagem 'Forbidden'")
    public void testListarAtividadesInstrutor_informarTokenAluno_esperaSucesso() {
        ErroAlternativoDto erro = AtividadesInstrutorClient.listarAtividadesComoAluno().then()
                .statusCode(403)
                .extract().as(ErroAlternativoDto.class);

        assertAll("Testes de listar atividades do instrutor não informando token",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertEquals(403, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Forbidden", erro.getError(), "Mensagem de erro deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar Atividades Do Instrutor Ao Informar Pagina Que Não Existe")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao listar as atividades do instrutor passando uma página que não existe retorna 404 e a mensagem 'Nenhuma atividade encontrada.")
    public void testListarAtividadesInstrutor_informarPaginaQueNaoExiste_esperaSucesso() {
        String page = "0";
        String size = "10";

        PaginacaoAtividadeInstrutorDto questaoResult = AtividadesInstrutorClient.listarAtividades(page, size).then()
                .extract().as(PaginacaoAtividadeInstrutorDto.class);

        String pageQueNaoExiste = Integer.toString(questaoResult.getTotalPages() + 1);

        ErroDto erro = AtividadesInstrutorClient.listarAtividades(pageQueNaoExiste, size).then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de listar questões informando página que não existe",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Nenhuma atividade encontrada.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Buscar Atividades Do Instrutor Por Id Ao Informar ID Existente")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar atividades do instrutor por ID informando ID existente retorna 200 e todos os dados da atividade")
    public void testBuscarAtividadePorIdInstrutor_informarIDExistente_esperaSucesso() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();

        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        PaginacaoListarAtividadePorIdEstagiarioDto questaoResult = AtividadesInstrutorClient.listarAtividadeEstagiarioPorId(atividadeResult.getAtividadeId()).then()
                .statusCode(200)
                .extract().as(PaginacaoListarAtividadePorIdEstagiarioDto.class);

        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId());
        QuestaoClient.excluirQuestao(atividadeResult.getQuestoesInt().get(0));
        QuestaoClient.excluirQuestao(atividadeResult.getQuestoesInt().get(1));

        assertAll("Testes de buscar atividades do instrutor por ID informando ID existente",
                () -> assertNotNull(questaoResult, "Questão buscada não deve ser nula"),
                () -> assertEquals(atividadeResult.getAtividadeId(), questaoResult.getContent().get(0).getAtividadesId(), "ID da atividade buscada deve ser igual ao ID da atividade criada"),
                () -> assertEquals(atividadeResult.getTitulo(), questaoResult.getContent().get(0).getAtividade().getTitulo(), "Titulo da atividade buscada deve ser igual ao titulo da atividade criada"),
                () -> assertEquals(atividadeResult.getDescricao(), questaoResult.getContent().get(0).getAtividade().getDescricao(), "Descrição da atividade buscada deve ser igual ao descrição da atividade criada"),
                () -> assertEquals(atividadeResult.getPrazoEntrega(), questaoResult.getContent().get(0).getAtividade().getPrazoEntrega(), "Prazo de entrega da atividade buscada deve ser igual ao prazo de entrega da atividade criada"),
                () -> assertEquals(atividadeResult.getTrilha(), questaoResult.getContent().get(0).getAtividade().getTrilha().toString(), "Trilha da atividade buscada deve ser igual ao trilha da atividade criada"),
                () -> assertEquals(4, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Buscar Atividades Do Instrutor Por Id Ao Informar ID Inexistente")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar atividades do instrutor por ID informando ID inexistente retorna 200 e nenhuma atividade")
    public void testBuscarAtividadePorIdInstrutor_informarPaginaETamanhoValidos_esperaSucesso() {
        PaginacaoListarAtividadePorIdEstagiarioDto questaoResult = AtividadesInstrutorClient.listarAtividadeEstagiarioPorId(9999999).then()
                .statusCode(200)
                .log().all()
                .extract().as(PaginacaoListarAtividadePorIdEstagiarioDto.class);

        assertAll("Testes de buscar atividades do instrutor por ID informando ID inexistente",
                () -> assertEquals(0, questaoResult.getNumberOfElements(), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(questaoResult.getContent().size(), questaoResult.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(0, questaoResult.getPageable().getPageNumber(),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Buscar Atividades Do Instrutor Por Id Ao Informar ID Maior Que O Limite")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar atividades do instrutor por ID informando ID maior que o limite 400 e a mensagem 'Houve um erro em um conversão. Verifique se os valores estão corretos.'")
    public void testBuscarAtividadePorIdInstrutor_informarIDMaiorQueOLimite_esperaSucesso() {
        ErroDto erro = AtividadesInstrutorClient.listarAtividadeEstagiarioPorId("99993333999").then()
                .statusCode(400)
                .log().all()
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar atividades do instrutor por ID informando ID maior que o limite",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(400, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Houve um erro em um conversão. Verifique se os valores estão corretos.", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Buscar Atividades Do Instrutor Por Id Ao Informar ID Inativo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao buscar atividades do instrutor por ID informando ID inativo retorna 404 e a mensagem 'Atividade não encontrada'")
    public void testBuscarAtividadePorIdInstrutor_informarIDInativo_esperaSucesso() {
        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();

        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        AtividadesInstrutorClient.excluirAtividade(atividadeResult.getAtividadeId());
        QuestaoClient.excluirQuestao(atividadeResult.getQuestoesInt().get(0));
        QuestaoClient.excluirQuestao(atividadeResult.getQuestoesInt().get(1));

        ErroDto erro = AtividadesInstrutorClient.listarAtividadeEstagiarioPorId(atividadeResult.getAtividadeId()).then()
                .statusCode(200)
                .extract().as(ErroDto.class);

        assertAll("Testes de buscar atividades do instrutor por ID informando ID inativo",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(404, erro.getStatus(), "Status do erro deve ser igual ao esperado"),
                () -> assertEquals("Atividade não encontrada", erro.getErrors().get("error"), "Mensagem de erro deve ser igual a esperada")
        );
    }
}
