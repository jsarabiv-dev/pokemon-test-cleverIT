package com.cleverit.springboottest.core.service;

import com.cleverit.springboottest.core.constant.ConstantMsgError;
import com.cleverit.springboottest.core.dto.MoveDTO;
import com.cleverit.springboottest.core.dto.PokemonDTO;
import com.cleverit.springboottest.core.dto.TypeAttackDTO;
import com.cleverit.springboottest.core.dto.TypePokemonDTO;
import com.cleverit.springboottest.core.mapper.MoveMapper;
import com.cleverit.springboottest.core.mapper.PokemonMapper;
import com.cleverit.springboottest.core.mapper.TypeAttackMapper;
import com.cleverit.springboottest.core.mapper.TypePokemonMapper;
import com.cleverit.springboottest.core.repository.MovesRepository;
import com.cleverit.springboottest.core.repository.PokemonRepository;
import com.cleverit.springboottest.core.repository.TypeAttackRepository;
import com.cleverit.springboottest.core.repository.TypePokemonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WSProviderServiceImpl implements WSProviderService {

    private final PokemonRepository pokemonRepository;
    private final MovesRepository movesRepository;
    private final TypeAttackRepository typeAttackRepository;
    private final TypePokemonRepository typePokemonRepository;

    @Override
    public List<MoveDTO> getMoves() {
        return movesRepository.findAll()
                .stream()
                .map(MoveMapper.INSTANCIA::MoveToMoveDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MoveDTO getMoveById(String id) {
        return movesRepository.findById(Long.parseLong(id))
                .map(MoveMapper.INSTANCIA::MoveToMoveDTO)
                .orElseThrow(() ->
                {
                    String s = "Move not found, whit id " ;

                    return new NoSuchElementException(ConstantMsgError.MOVE_NOT_FOUND + id) ;
                });
    }

    @Override
    public List<TypeAttackDTO> getPokemonAttacks() {
        return typeAttackRepository.findAll()
                .stream()
                .map(TypeAttackMapper.INSTANCIA::TypeAttackToTypeAttackDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TypeAttackDTO> getPokemonAttacksById(String id) {
        return pokemonRepository.findById(Long.parseLong(id))
                .map(p -> p.getMoves()
                        .stream()
                        .map(move -> TypeAttackMapper.INSTANCIA.TypeAttackToTypeAttackDTO(move.getTypeAttack()))
                        .collect(Collectors.toList()))
                .orElseThrow(() ->
                        new NoSuchElementException("Pokemon with id " + id + " not found"));
    }

    @Override
    public List<TypePokemonDTO> getTypesPokemon() {
        return typePokemonRepository.findAll()
                .stream()
                .map(TypePokemonMapper.INSTANCIA::TypePokemonToTypePokemonDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TypePokemonDTO getTypePokemonById(String id) {
        return pokemonRepository.findById(Long.parseLong(id))
                .map(p -> TypePokemonMapper.INSTANCIA.TypePokemonToTypePokemonDTO(p.getTypePokemon()))
                .orElseThrow(() ->
                        new NoSuchElementException("Pokemon with id " + id + " not found"));
    }

    @Override
    public List<PokemonDTO> getPokemons() {
        return pokemonRepository.findAll()
                .stream()
                .map(PokemonMapper.INSTANCIA::PokemonToPokemonDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PokemonDTO getPokemonById(String id) {
        return pokemonRepository.findById(Long.parseLong(id))
                .map(PokemonMapper.INSTANCIA::PokemonToPokemonDTO)
                .orElseThrow(() ->
                        new NoSuchElementException("Pokemon with id " + id + " not found"));
    }


}
