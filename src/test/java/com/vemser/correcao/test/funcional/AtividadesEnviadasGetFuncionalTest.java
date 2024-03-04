package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesEnviadaClient;
import com.vemser.correcao.dto.atividade.*;
import com.vemser.correcao.dto.erro.ErroDto;
import com.vemser.correcao.dto.erro.ErroForbiddenDto;
import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.enums.QuestoesParametro;
import com.vemser.correcao.enums.Trilha;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Epic("Funcional Atividades Enviadas - GET")
@DisplayName("Atividades Enviadas - GET")
@Owner("Italo Lacerda")
public class AtividadesEnviadasGetFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar Todas as Atividades do Aluno Logado Sem Passar Parâmetros")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica listar todas as atividades do aluno logado sem passar parâmetros retorna 200 e paginação correta")
    public void testListarAtividadesEnviadas_semInformarParametros_esperaSucesso() {
        PaginacaoAtividadeEnviadaDto retorno = AtividadesEnviadaClient.listarAtividadesDoAluno()
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .as(PaginacaoAtividadeEnviadaDto.class);

        assertAll("Testes de listar questão informando página e tamanho válido",
                () -> assertEquals(retorno.getNumberOfElements(), 10, "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(retorno.getContent().size(), retorno.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(retorno.getNumber(), 0,"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar todas as atividades do aluno logado sem passar o 'page' como parametro")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica listar todas as atividades do aluno logado sem passar o 'page' como parametro retorna 200 e paginação correta")
    public void testListarAtividadesEnviadas_semInformarPageComoParametro_esperaSucesso() {
        PaginacaoAtividadeEnviadaDto retorno = AtividadesEnviadaClient.listarAtividadesDoAluno(QuestoesParametro.size, "2")
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .as(PaginacaoAtividadeEnviadaDto.class);

        assertAll("Testes de listar questão informando página e tamanho válido",
                () -> assertEquals(retorno.getNumberOfElements(), 2, "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(retorno.getContent().size(), retorno.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(retorno.getNumber(), 0,"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar todas as atividades do aluno logado sem passar o 'size' como parametro")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica listar todas as atividades do aluno logado sem passar o 'size' como parametro retorna 200 e paginação correta")
    public void testListarAtividadesEnviadas_semInformarSizeComoParametro_esperaSucesso() {
        PaginacaoAtividadeEnviadaDto retorno = AtividadesEnviadaClient.listarAtividadesDoAluno(QuestoesParametro.page, "0")
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .as(PaginacaoAtividadeEnviadaDto.class);

        assertAll("Testes de listar questão informando página e tamanho válido",
                () -> assertEquals(retorno.getNumberOfElements(), 10, "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(retorno.getContent().size(), retorno.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(retorno.getNumber(), 0,"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Listar todas as atividades do aluno logado passando todos os parametros")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica listar todas as atividades do aluno passando todos os parametros retorna 200 e paginação correta")
    public void testListarAtividadesEnviadas_semInformarTodosOsParametros_esperaSucesso() {
        final String SIZE = "2";
        final String PAGE = "1";

        PaginacaoAtividadeEnviadaDto retorno = AtividadesEnviadaClient.listarAtividadesDoAluno(PAGE, SIZE)
                .then()
                .statusCode(200)
                .extract()
                .as(PaginacaoAtividadeEnviadaDto.class);

        assertAll("Testes de listar questão informando página e tamanho válido",
                () -> assertEquals(retorno.getNumberOfElements(), Integer.parseInt(SIZE), "Número de elementos deve ser igual ao esperado"),
                () -> assertEquals(retorno.getContent().size(), retorno.getNumberOfElements(), "Tamanho do conteúdo deve ser igual ao número de elementos"),
                () -> assertEquals(retorno.getNumber(), Integer.parseInt(PAGE),"Número da página deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar todas as atividades do aluno logado passando pagina invalida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se lista atividade ao informa um 'page' invalido retorna 404 e body com error correto")
    public void testListarAtividadesEnviadas_informarPaginaInvalida_esperaFalha() {
        final String SIZE = "10";
        final String PAGE = "-1";

        ErroDto erro = AtividadesEnviadaClient.listarAtividadesDoAluno(PAGE, SIZE)
                .then()
                .statusCode(404)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de listar questão informando página inválida",
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getErrors().get("error"), "Página não encontrada", "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Listar todas as atividades do aluno logado passando pagina invalida")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se lista atividade ao informa uma pagina que não existe retorna 404 e body com error correto")
    public void testListarAtividadesEnviadas_informarPaginaQueNaoExiste_esperaErro() {
        PaginacaoAtividadeEnviadaDto atividadesEnvidas = AtividadesEnviadaClient.listarAtividadesDoAluno("0","10")
                .as(PaginacaoAtividadeEnviadaDto.class);

        final String PAGE = Integer.toString(atividadesEnvidas.getTotalPages() + 1);
        final String SIZE = "10";

        ErroDto erro = AtividadesEnviadaClient.listarAtividadesDoAluno(PAGE, SIZE)
                .then()
                .statusCode(404)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de listar questão informando página inválida",
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getErrors().get("error"), "Página não encontrada", "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] listar todas as atividades sem estar logado")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Teste que verifica se lista atividade sem estar logado retorna 403 e body com error correto")
    public void testListarAtividadesEnviadas_semEstarLogado_esperaErro() {
        final String SIZE = "10";
        final String PAGE = "0";

        ErroForbiddenDto erro = AtividadesEnviadaClient.listarAtividadesDoAlunoSemEstarLogado(PAGE, SIZE)
                .then()
                .statusCode(403)
                .extract()
                .as(ErroForbiddenDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(erro.getStatus(), 403),
                () -> assertNotNull(erro.getTimestamp()),
                () -> assertNotNull(erro.getPath()),
                () -> assertEquals(erro.getError(),"Forbidden" )
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] listar todas as atividades logado como instrutor")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Teste que verifica se lista atividade logado como instrutor retorna 403 e body com error correto")
    public void testListarAtividadesEnviadas_logadoComoInstrutor_esperaErro() {
        final String SIZE = "10";
        final String PAGE = "0";

        ErroForbiddenDto erro = AtividadesEnviadaClient.listarAtividadesDoAlunoComoInstrutor(PAGE, SIZE)
                .then()
                .statusCode(403)
                .extract()
                .as(ErroForbiddenDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(erro.getStatus(), 403),
                () -> assertNotNull(erro.getTimestamp()),
                () -> assertNotNull(erro.getPath()),
                () -> assertEquals(erro.getError(),"Forbidden" )
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] ")
    @Severity(SeverityLevel.NORMAL)
    @Description("")
    public void testListarAtividadesEnviadas_comIdValido_esperaSucesso() {

        final String nomeUsuario = "aluno.teste";

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .extract().as(CriarAtividadeResponseDto.class);

        PaginacaoListarAtividadePorIdEstagiarioDto listarAtividadeAluno =
                AtividadesInstrutorClient.listarAtividadeEstagiarioPorId(atividadeResult.getAtividadeId())
                        .then()
                        .extract().as(PaginacaoListarAtividadePorIdEstagiarioDto.class);

        Integer idAtividadeEnviada = null;

        for (int i = 0; i < listarAtividadeAluno.getContent().size(); i++) {

            String userName = listarAtividadeAluno.getContent().get(i).getUserName();
            if (userName.equals("aluno.teste")) {
                idAtividadeEnviada = listarAtividadeAluno.getContent().get(i).getAtividadesEnviadasId();
            }
        }

        BuscarAtividadeEnviadasDto resposta = AtividadesEnviadaClient.buscarAtividadeRelatorioPeloId(idAtividadeEnviada)
                .then()
                .statusCode(200)
                .extract()
                .as(BuscarAtividadeEnviadasDto.class);

        Integer finalIdAtividadeEnviada = idAtividadeEnviada;

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(resposta.getUserName(), nomeUsuario),
                () -> assertEquals(resposta.getTrilha(), Trilha.BACK),
                () -> assertEquals(resposta.getAtividadesEnviadasId(), finalIdAtividadeEnviada),
                () -> assertNotNull(resposta.getPrazoEntrega()),
                () -> assertNotNull(resposta.getQuestoes())
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] ")
    @Severity(SeverityLevel.NORMAL)
    @Description("")
    public void testListarAtividadesEnviadas_idInvalido_esperaErro() {

        ErroDto erro = AtividadesEnviadaClient.buscarAtividadeRelatorioPeloId(-1)
                .then()
                .statusCode(404)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de listar questão informando página inválida",
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getErrors().get("error"), "Página não encontrada", "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] ")
    @Severity(SeverityLevel.NORMAL)
    @Description("")
    public void testListarAtividadesEnviadas_idInexistente_esperaErro() {
        PaginacaoAtividadeEnviadaDto paginacaoAtividadeEnviada = AtividadesEnviadaClient.listarAtividadesDoAluno()
                .then()
                .extract()
                .as(PaginacaoAtividadeEnviadaDto.class);

        PaginacaoAtividadeEnviadaDto ultimaPaginaAtividadeEnviada = AtividadesEnviadaClient
                .listarAtividadesDoAluno(QuestoesParametro.page, String.valueOf(paginacaoAtividadeEnviada.getTotalPages() - 1))
                .then()
                .extract()
                .as(PaginacaoAtividadeEnviadaDto.class);

        int tamanhoDaLista = ultimaPaginaAtividadeEnviada.getContent().size();
        Integer idInexistente = ultimaPaginaAtividadeEnviada.getContent()
                .get(tamanhoDaLista - 1)
                .getAtividadesEnviadasId() + 1;

        ErroDto erro = AtividadesEnviadaClient.buscarAtividadeRelatorioPeloId(idInexistente)
                .then()
                .statusCode(404)
                .extract()
                .as(ErroDto.class);

        assertAll("Testes de listar questão informando página inválida",
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getErrors().get("error"), "Página não encontrada", "Mensagem de erro deve ser igual a esperada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] ")
    @Severity(SeverityLevel.NORMAL)
    @Description("")
    public void testListarAtividadesEnviadasPorId_semEstarLogado_esperaErro() {
        final String nomeUsuario = "aluno.teste";

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .extract().as(CriarAtividadeResponseDto.class);

        PaginacaoListarAtividadePorIdEstagiarioDto listarAtividadeAluno =
                AtividadesInstrutorClient.listarAtividadeEstagiarioPorId(atividadeResult.getAtividadeId())
                        .then()
                        .extract().as(PaginacaoListarAtividadePorIdEstagiarioDto.class);

        Integer idAtividadeEnviada = null;

        for (int i = 0; i < listarAtividadeAluno.getContent().size(); i++) {
            String userName = listarAtividadeAluno.getContent().get(i).getUserName();
            if (userName.equals(nomeUsuario)) {
                idAtividadeEnviada = listarAtividadeAluno.getContent().get(i).getAtividadesEnviadasId();
            }
        }

        ErroForbiddenDto erro = AtividadesEnviadaClient.buscarAtividadeRelatorioPeloIdSemEstarLogado(idAtividadeEnviada)
                .then()
                .statusCode(403)
                .extract()
                .as(ErroForbiddenDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(erro.getStatus(), 403),
                () -> assertNotNull(erro.getTimestamp()),
                () -> assertNotNull(erro.getPath()),
                () -> assertEquals(erro.getError(),"Forbidden" )
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] ")
    @Severity(SeverityLevel.NORMAL)
    @Description("")
    public void testListarAtividadesEnviadasPorId_semLogadoComoInstrutor_esperaErro() {
        final String nomeUsuario = "aluno.teste";

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .extract().as(CriarAtividadeResponseDto.class);

        PaginacaoListarAtividadePorIdEstagiarioDto listarAtividadeAluno =
                AtividadesInstrutorClient.listarAtividadeEstagiarioPorId(atividadeResult.getAtividadeId())
                        .then()
                        .extract().as(PaginacaoListarAtividadePorIdEstagiarioDto.class);

        Integer idAtividadeEnviada = null;

        for (int i = 0; i < listarAtividadeAluno.getContent().size(); i++) {
            String userName = listarAtividadeAluno.getContent().get(i).getUserName();
            if (userName.equals(nomeUsuario)) {
                idAtividadeEnviada = listarAtividadeAluno.getContent().get(i).getAtividadesEnviadasId();
            }
        }

        ErroForbiddenDto erro = AtividadesEnviadaClient.buscarAtividadeRelatorioPeloIdLogadoComoInstrutor(idAtividadeEnviada)
                .then()
                .statusCode(403)
                .extract()
                .as(ErroForbiddenDto.class);

        assertAll("Verifica se retorna error correto",
                () -> assertEquals(erro.getStatus(), 403),
                () -> assertNotNull(erro.getTimestamp()),
                () -> assertNotNull(erro.getPath()),
                () -> assertEquals(erro.getError(),"Forbidden" )
        );
    }
}
