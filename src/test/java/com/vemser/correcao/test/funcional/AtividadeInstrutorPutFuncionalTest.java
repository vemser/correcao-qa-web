package com.vemser.correcao.test.funcional;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.client.QuestaoClient;
import com.vemser.correcao.data.factory.CriarAtividadeDataFactory;
import com.vemser.correcao.dto.atividade.CriarAtividadeDto;
import com.vemser.correcao.dto.atividade.CriarAtividadeResponseDto;
import com.vemser.correcao.dto.erro.ErroDto;
import com.vemser.correcao.dto.erro.ErroForbiddenDto;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Funcional Atividades Instrutor - PUT")
@DisplayName("Atividades Instrutor - PUT")
@Owner("Luísa Santos")
public class AtividadeInstrutorPutFuncionalTest {

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Informar Campos Válidos")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao editar uma atividade existente a API retorna 200 e a atividade editada no body")
    public void testEditarAtividade_informarCamposValidos_esperaSucesso() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeComDadosValidos();

        CriarAtividadeResponseDto response = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(200)
                .extract().as(CriarAtividadeResponseDto.class);

        assertAll("Testes de editar atividade informando campos validos",
                () -> assertEquals(response.getAtividadeId(), atividadeResult.getAtividadeId(), "ID da Atividade não deve ser nulo"),
                () -> assertEquals(response.getTitulo(), atividadeEditada.getTitulo(), "Título deve ser igual ao esperado"),
                () -> assertEquals(response.getDescricao(), atividadeEditada.getDescricao(), "Descrição deve ser igual a esperada"),
                () -> assertEquals(response.getQuestoesInt(), atividadeEditada.getQuestoesInt(), "Trilha deve ser igual a esperada"),
                () -> assertTrue(response.getPrazoEntrega().contains(atividadeEditada.getPrazoEntrega().replace("Z", "")), "Prazo de Entrega deve ser igual ao esperado")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Questões Inativas")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade com questões inválidas a API retorna 404 e a mensagem 'Questão não encontrada'")
    public void testEditarAtividade_informarQuestoesInativas_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeComQuestoesInativas();

        ErroDto erro = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade com questões inativas",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "Questão não encontrada")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar sem enviar o ID a API retorna 404 e o erro 'Not Found'")
    public void testEditarAtividade_naoInformarId_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeComDadosValidos();

        ErroDto erro = AtividadesInstrutorClient.editarAtividadeSemId(atividadeEditada)
                .then()
                .statusCode(404)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade sem informar ID",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 404, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("error"), "Not Found")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Informar Data Inválida")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade com data inválida a API retorna 400 e a mensagem 'A data de entrega deve ser no futuro'")
    public void testEditarAtividade_informarDataInvalida_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeComDataInvalida();

        ErroDto erro = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade informando prazo de entrega inválido",
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
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade sem atribuir questões a API retorna 400 e a mensagem 'A lista de questões não pode ser nula ou vazia.'")
    public void testEditarAtividade_naoInformarQuestoes_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeSemAtribuirQuestoes();

        ErroDto erro = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade sem atribuir questões",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("questoes"), "A lista de questões não pode ser nula ou vazia.")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Titulo")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade sem um título a API retorna 400 e a mensagem 'O título é obrigatório'")
    public void testEditarAtividade_naoInformarTitulo_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeSemPreencherTitulo();

        ErroDto erro = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade sem preencher título",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("titulo"), "O título é obrigatório")
        );
    }

    @Test
    @Feature("Espera Sucesso")
    @Story("[CTAXXX] Não Informar Descrição")
    @Severity(SeverityLevel.NORMAL)
    @Description("Teste que verifica se ao editar uma atividade sem uma descrição a API retorna 200 e a atividade editada no body")
    public void testEditarAtividade_naoInformarDescricao_esperaSucesso() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeSemPreencherTitulo();

        CriarAtividadeResponseDto response = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        assertAll("Testes de editar atividade sem preencher descrição",
                () -> assertEquals(response.getAtividadeId(), atividadeResult.getAtividadeId(), "ID da Atividade não deve ser nulo"),
                () -> assertEquals(response.getTitulo(), atividadeEditada.getTitulo(), "Título deve ser igual ao esperado"),
                () -> assertEquals(response.getDescricao(), atividadeEditada.getDescricao(), "Descrição deve estar vazia"),
                () -> assertEquals(response.getQuestoesInt(), atividadeEditada.getQuestoesInt(), "Questões devem ser iguais as esperadas"),
                () -> assertEquals(response.getTrilha(), atividadeEditada.getTrilha(), "Trilha deve ser igual a esperada"),
                () -> assertTrue(response.getPrazoEntrega().contains(atividadeEditada.getPrazoEntrega().replace("Z", "")), "Prazo de Entrega deve ser igual ao esperado")
                );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Trilha Da Atividade")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade sem informar trilha a API retorna 400 e a mensagem 'Trilha nula ou inválida. Valores válidos: BACK, FRONT, QA'")
    public void testEditarAtividade_naoInformarTrilha_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        String atividadeEditada = CriarAtividadeDataFactory.atividadeSemPreencherTrilha();

        ErroDto erro = AtividadesInstrutorClient.editarAtividadeComoString(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade sem preencher trilha",
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
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade informando uma trilha inválida a API retorna 400 e a mensagem 'Trilha nula ou inválida. Valores válidos: BACK, FRONT, QA'")
    public void testEditarAtividade_InformarTrilhaInvalida_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        String atividadeEditada = CriarAtividadeDataFactory.atividadePreenchendoTrilhaInvalida();

        ErroDto erro = AtividadesInstrutorClient.editarAtividadeComoString(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade preenchendo trilha inválida",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("trilha"), "Trilha nula ou inválida. Valores válidos: BACK, FRONT, QA")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Não Informar Edição do Vem Ser")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar uma atividade sem informar a edição do Vem Ser a API retorna 400 e a mensagem 'A edição vem ser é obrigatória'")
    public void testEditarAtividade_naoInformarEdicao_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeSemPreencherEdicao();

        ErroDto erro = AtividadesInstrutorClient.editarAtividade(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(400)
                .extract().as(ErroDto.class);

        assertAll("Testes de editar atividade sem preencher edição",
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getStatus(), "Status da erro não deve ser nulo"),
                () -> assertFalse(erro.getErrors().isEmpty(), "Lista de erros não deve está vazia"),
                () -> assertEquals(erro.getStatus(), 400, "Status do erro deve ser igual ao esperado"),
                () -> assertEquals(erro.getErrors().get("edicaoVemSer"), "A edição vem ser é obrigatória")
        );
    }

    @Test
    @Feature("Espera Erro")
    @Story("[CTAXXX] Editar Sem Autenticação")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste que verifica se ao editar atividade sem ter autenticação a API retorna 403 e erro 'Você não tem autorização para acessar este serviço'")
    public void testEditarAtividade_naoInformarAutenticacao_esperaErro() {

        CriarAtividadeDto atividade = CriarAtividadeDataFactory.atividadeComDadosValidos();
        CriarAtividadeResponseDto atividadeResult = AtividadesInstrutorClient.criarAtividade(atividade)
                .then()
                .statusCode(201)
                .extract().as(CriarAtividadeResponseDto.class);

        CriarAtividadeDto atividadeEditada = CriarAtividadeDataFactory.atividadeComDadosValidos();

        ErroForbiddenDto erro = AtividadesInstrutorClient.editarAtividadeSemAutenticacao(atividadeResult.getAtividadeId(), atividadeEditada)
                .then()
                .statusCode(403)
                .extract()
                .as(ErroForbiddenDto.class);

        assertAll("Testes de editar atividade sem autenticação",
                () -> assertEquals(erro.getStatus(), 403, "Status da erro não deve ser nulo"),
                () -> assertNotNull(erro.getTimestamp(), "Timestamp do erro não deve ser nulo"),
                () -> assertNotNull(erro.getPath()),
                () -> assertEquals(erro.getError(),"Você não tem autorização para acessar este serviço" )
        );
    }
}
