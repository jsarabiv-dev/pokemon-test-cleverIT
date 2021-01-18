package com.cleverit.springboottest.service;

import com.cleverit.springboottest.core.dto.MoveDTO;
import com.cleverit.springboottest.core.dto.PokemonDTO;
import com.cleverit.springboottest.core.dto.TypeAttackDTO;
import com.cleverit.springboottest.core.dto.TypePokemonDTO;
import com.cleverit.springboottest.core.entity.Move;
import com.cleverit.springboottest.core.entity.Pokemon;
import com.cleverit.springboottest.core.entity.TypeAttack;
import com.cleverit.springboottest.core.entity.TypePokemon;
import com.cleverit.springboottest.core.repository.MovesRepository;
import com.cleverit.springboottest.core.repository.PokemonRepository;
import com.cleverit.springboottest.core.repository.TypeAttackRepository;
import com.cleverit.springboottest.core.repository.TypePokemonRepository;
import com.cleverit.springboottest.core.service.WSProviderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class WSProviderServiceImplTest {

    @MockBean
    PokemonRepository pokemonRepository;

    @MockBean
    MovesRepository movesRepository;

    @MockBean
    TypeAttackRepository typeAttackRepository;

    @MockBean
    TypePokemonRepository typePokemonRepository;

    @Autowired
    WSProviderService wsProviderService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetMoves() throws Exception {

        List<Move> moves = new ArrayList<>();
        moves.add(new Move());
        moves.add(new Move());
        moves.add(new Move());

        Mockito.when(movesRepository.findAll()).thenReturn(moves);

        List<MoveDTO> movesDTO = wsProviderService.getMoves();

        assertThat(movesDTO, instanceOf(ArrayList.class));
        assertThat(movesDTO.size(), equalTo(3));
        assertThat(movesDTO.get(0), instanceOf(MoveDTO.class));

    }

    @Test
    public void testGetMoveByIdOK() throws Exception {
        Mockito.when(movesRepository.findById(1L)).thenReturn(java.util.Optional.of(new Move()));
        MoveDTO movesDTO = wsProviderService.getMoveById("1");
        assertThat(movesDTO, instanceOf(MoveDTO.class));
    }

    @Test(expected = NoSuchElementException.class )
    public void testGetMoveByIdNotFound() throws Exception {
        Mockito.when(movesRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(wsProviderService.getMoveById("1"), instanceOf(NoSuchElementException.class));

    }

    @Test
    public void testGetPokemonAttacks() throws Exception {

        List<TypeAttack> typeAttacks = new ArrayList<>();
        typeAttacks.add(new TypeAttack());
        typeAttacks.add(new TypeAttack());
        typeAttacks.add(new TypeAttack());

        Mockito.when(typeAttackRepository.findAll()).thenReturn(typeAttacks);

        List<TypeAttackDTO> typeAttackDTOS = wsProviderService.getPokemonAttacks();

        assertThat(typeAttackDTOS, instanceOf(ArrayList.class));
        assertThat(typeAttackDTOS.size(), equalTo(3));
        assertThat(typeAttackDTOS.get(0), instanceOf(TypeAttackDTO.class));

    }

    @Test
    public void testGetPokemonAttacksByIdOK() throws Exception {

        Pokemon pokemon = new Pokemon();
        Move move = new Move();
        move.setTypeAttack(new TypeAttack());
        List<Move> moves = new ArrayList<>();
        moves.add(move);
        pokemon.setMoves(moves);

        Mockito.when(pokemonRepository.findById(1L)).thenReturn(Optional.of(pokemon));

        List<TypeAttackDTO> typeAttackResponse = wsProviderService.getPokemonAttacksById("1");

        assertThat(typeAttackResponse, instanceOf(ArrayList.class));
        assertThat(typeAttackResponse.size(), equalTo(1));
        assertThat(typeAttackResponse.get(0), instanceOf(TypeAttackDTO.class));
    }

    @Test(expected = NoSuchElementException.class )
    public void testGetPokemonAttacksByIdNotFound() throws Exception {
        Mockito.when(pokemonRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(wsProviderService.getMoveById("1"), instanceOf(NoSuchElementException.class));
    }

    @Test
    public void testGetTypesPokemon() throws Exception {

        List<TypePokemon> typesPokemon = new ArrayList<>();
        typesPokemon.add(new TypePokemon());
        typesPokemon.add(new TypePokemon());
        typesPokemon.add(new TypePokemon());

        Mockito.when(typePokemonRepository.findAll()).thenReturn(typesPokemon);

        List<TypePokemonDTO> typesPokemonResponse = wsProviderService.getTypesPokemon();

        assertThat(typesPokemonResponse, instanceOf(ArrayList.class));
        assertThat(typesPokemonResponse.size(), equalTo(3));
        assertThat(typesPokemonResponse.get(0), instanceOf(TypePokemonDTO.class));

    }

    @Test
    public void testGetTypePokemonById() throws Exception {
        Pokemon pokemon = new Pokemon();
        pokemon.setTypePokemon(new TypePokemon());

        Mockito.when(pokemonRepository.findById(1L)).thenReturn(Optional.of(pokemon));

        TypePokemonDTO typePokemonDTOResponse = wsProviderService.getTypePokemonById("1");

        assertThat(typePokemonDTOResponse, instanceOf(TypePokemonDTO.class));


    }

    @Test(expected = NoSuchElementException.class )
    public void testGetTypePokemonByIdNotFound() throws Exception {
        Mockito.when(pokemonRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(wsProviderService.getTypePokemonById("1"), instanceOf(NoSuchElementException.class));
    }

    @Test
    public void testGetPokemons() throws Exception {

        List<Pokemon> pokemons = new ArrayList<>();
        pokemons.add(new Pokemon());
        pokemons.add(new Pokemon());
        pokemons.add(new Pokemon());

        Mockito.when(pokemonRepository.findAll()).thenReturn(pokemons);

        List<PokemonDTO> pokemonsDTOResponse = wsProviderService.getPokemons();

        assertThat(pokemonsDTOResponse, instanceOf(ArrayList.class));
        assertThat(pokemonsDTOResponse.size(), equalTo(3));
        assertThat(pokemonsDTOResponse.get(0), instanceOf(PokemonDTO.class));

    }

    @Test
    public void testGetPokemonByIdOK() throws Exception {
        Mockito.when(pokemonRepository.findById(1L)).thenReturn(Optional.of(new Pokemon()));
        PokemonDTO pokemonDTOResponse = wsProviderService.getPokemonById("1");
        assertThat(pokemonDTOResponse, instanceOf(PokemonDTO.class));
    }

    @Test(expected = NoSuchElementException.class )
    public void testGetPokemonByIdNotFound() throws Exception {
        Mockito.when(pokemonRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(wsProviderService.getPokemonById("1"), instanceOf(NoSuchElementException.class));
    }


} 
