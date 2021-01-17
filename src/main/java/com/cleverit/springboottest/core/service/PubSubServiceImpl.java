package com.cleverit.springboottest.core.service;

import com.cleverit.springboottest.core.dao.PokemonDAO;
import com.cleverit.springboottest.core.entity.Move;
import com.cleverit.springboottest.core.entity.Pokemon;
import com.cleverit.springboottest.core.entity.Sprite;
import com.cleverit.springboottest.core.entity.TypePokemon;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PubSubServiceImpl implements PubSubService {

    private final PokemonDAO pokemonDAO;

    @Override
    public String savePokemon(String json) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        /* Obtencion del JSON principal */
        JsonNode pokemonJson = mapper.readTree(json);

        /* typePokemon */
        TypePokemon typePokemon = mapper.treeToValue(pokemonJson.get("types").get(0).get("type"), TypePokemon.class);

        /* Moves Name */
        List<Move> moves = new ArrayList<>();
        for (JsonNode jsonNode : (ArrayNode) pokemonJson.get("moves")) {
            moves.add(mapper.treeToValue(jsonNode.get("move"), Move.class));
        }

        /* Sprites */
        Sprite sprite = mapper.treeToValue(pokemonJson.get("sprites"), Sprite.class);

        /* Pokemon */
        ((ObjectNode) pokemonJson).remove("types");
        Pokemon pokemon = mapper.treeToValue(pokemonJson, Pokemon.class);
        pokemon.setMoves(moves);
        pokemon.setSprites(sprite);
        pokemon.setTypePokemon(typePokemon);

        pokemon = pokemonDAO.savePokemon(pokemon);

//        log.debug("Pokemon guardado, con Id: {}", pokemon.getId());

        return "Pokemon Guardado";

    }
}
