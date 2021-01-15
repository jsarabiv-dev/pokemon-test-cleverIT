package com.cleverit.springboottest.core.dao;

import com.cleverit.springboottest.core.entity.Move;
import com.cleverit.springboottest.core.entity.Pokemon;
import com.cleverit.springboottest.core.entity.TypePokemon;
import com.cleverit.springboottest.core.repository.MovesRepository;
import com.cleverit.springboottest.core.repository.PokemonRepository;
import com.cleverit.springboottest.core.repository.TypeAttackRepository;
import com.cleverit.springboottest.core.repository.TypePokemonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor //TODO: COMO FUNCIONA ESTO?
@Slf4j
@Repository
public class PokemonDAOImpl implements PokemonDAO {

    private final PokemonRepository pokemonRepository;
    private final MovesRepository movesRepository;
    private final TypeAttackRepository typeAttackRepository;
    private final TypePokemonRepository typePokemonRepository;


    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public Pokemon savePokemon(Pokemon pokemon) {

        TypePokemon typePokemon = typePokemonRepository.findByName(pokemon.getTypePokemon().getName())
                .orElseGet(() -> pokemon.getTypePokemon());

        List<Move> moves = pokemon.getMoves().stream().map(
                move -> {
                    Move m = movesRepository.findByName(move.getName())
                            .orElseGet(() -> move);
                    return m;
                })
                .collect(Collectors.toList());


        pokemon.setTypePokemon(typePokemon);
        pokemon.setMoves(moves);

        return pokemonRepository.save(pokemon);
    }



}
