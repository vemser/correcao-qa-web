package com.vemser.correcao.data.factory;

import com.vemser.correcao.client.AtividadesInstrutorClient;
import com.vemser.correcao.dto.CorrigirAtividadeDto;
import com.vemser.correcao.dto.CriarAtividadeDto;
import com.vemser.correcao.dto.CriarAtividadeResponseDto;
import com.vemser.correcao.dto.PaginacaoListarAtividadePorIdEstagiarioDto;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

public class CorrigirAtividadeDataFactory {

    private static Faker faker = new Faker();

    public static CorrigirAtividadeDto corrigirComDadosValidos(Integer id) {

        CorrigirAtividadeDto corrigirAtividadeDto = new CorrigirAtividadeDto();
        corrigirAtividadeDto.setAtividadeEnviadaID(id);
        corrigirAtividadeDto.setFeedbackProfessor(faker.book().title());
        corrigirAtividadeDto.setNotaTestes(faker.number().numberBetween(1, 100));

        return corrigirAtividadeDto;
    }
}
