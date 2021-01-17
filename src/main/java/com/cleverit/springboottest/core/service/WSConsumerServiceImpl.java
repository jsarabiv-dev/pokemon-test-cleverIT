package com.cleverit.springboottest.core.service;

import com.cleverit.springboottest.core.repository.MovesRepository;
import com.cleverit.springboottest.core.repository.TypeAttackRepository;
import com.cleverit.springboottest.core.entity.Move;
import com.cleverit.springboottest.core.entity.TypeAttack;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WSConsumerServiceImpl implements WSConsumerService {

    private final RestTemplate restTemplate;

    private final MovesRepository movesRepository;

    @Override
    public String getAllMovimientos() throws IOException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String movesString = restTemplate.getForObject("https://pokemonapi-4gm7aqn32q-nn.a.run.app/moves/", String.class);

            JsonNode movesNode = mapper.readTree(movesString).get("moves");

            Move[] moves = mapper.treeToValue(movesNode, Move[].class);

            Map<TypeAttack, List<Move>> mapCollect = Arrays.asList(moves).stream().collect(Collectors.groupingBy(Move::getTypeAttack));

            mapCollect.entrySet()
                    .stream()
                    .forEach(ent ->
                    {
                        TypeAttack typeAttack = ent.getKey();
                        ent.getValue().stream().forEach(move -> move.setTypeAttack(typeAttack));
                    });
            movesRepository.saveAll(Arrays.asList(moves));
            String msg = "Movimientos y Tipos de ataque guardados";
            log.info(msg);
            return msg;
        } catch (RuntimeException re) {
            String msgError = "A ocurrido un error, intente nuevamente.";
            log.error(msgError);
            return msgError;
        }
    }

  /*  @Override
    public String getAlTypes() {

        TypeAttack[] resultado = restTemplate.getForObject("https://pokemonapi-4gm7aqn32q-nn.a.run.app/types/", TypeAttack[].class);

        Arrays.asList(resultado).forEach(
                t -> {
                    log.info("=====================");
                    log.info(t.toString());
                }
        );
        //typesRepository.saveAll(Arrays.asList(resultado));

        return  "";
    }*/
}
