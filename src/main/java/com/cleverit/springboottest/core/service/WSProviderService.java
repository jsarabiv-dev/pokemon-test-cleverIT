package com.cleverit.springboottest.core.service;

import com.cleverit.springboottest.core.dto.MoveDTO;
import com.cleverit.springboottest.core.dto.PokemonDTO;
import com.cleverit.springboottest.core.dto.TypeAttackDTO;
import com.cleverit.springboottest.core.dto.TypePokemonDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Map;

public interface WSProviderService {

    List<MoveDTO> getMoves();

    MoveDTO getMoveById(String id);

    List<TypeAttackDTO> getPokemonAttacks();

    List<TypeAttackDTO> getPokemonAttacksById(String id);

    List<TypePokemonDTO> getTypesPokemon();

    TypePokemonDTO getTypePokemonById(String id);

    List<PokemonDTO> getPokemons();

    PokemonDTO getPokemonById(String id);
}
