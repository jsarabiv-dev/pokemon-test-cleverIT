package com.cleverit.springboottest.dao;

import com.cleverit.springboottest.core.dao.PokemonDAO;
import com.cleverit.springboottest.core.dto.MoveDTO;
import com.cleverit.springboottest.core.entity.Move;
import com.cleverit.springboottest.core.entity.Pokemon;
import com.cleverit.springboottest.core.entity.TypeAttack;
import com.cleverit.springboottest.core.entity.TypePokemon;
import com.cleverit.springboottest.core.repository.MovesRepository;
import com.cleverit.springboottest.core.repository.PokemonRepository;
import com.cleverit.springboottest.core.repository.TypePokemonRepository;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class PokemonDAOImplTest {

    @MockBean
    PokemonRepository pokemonRepository;

    @MockBean
    MovesRepository movesRepository;

    @MockBean
    TypePokemonRepository typePokemonRepository;

    @Autowired
    PokemonDAO pokemonDAO;


@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 
@Test
public void testSavePokemon() throws Exception {

    TypePokemon typePokemon = new TypePokemon();
    typePokemon.setName("Nuevo Type Pokemon TEST");

    Move move = new Move();
    move.setName("Nuevo Move TEST");

    TypeAttack typeAttack = new TypeAttack();
    typeAttack.setName("Nuevo Type Attack TEST");


    List<Move> moves = new ArrayList<>();
    moves.add(move);

    Pokemon pokemon = new Pokemon();
    pokemon.setName("Nuevo Pokemon TEST");
    pokemon.setTypePokemon(typePokemon);
    pokemon.setMoves(moves);


    Mockito.when(typePokemonRepository.findByName(typePokemon.getName())).thenReturn(Optional.of(typePokemon));
    Mockito.when(movesRepository.findByName(move.getName())).thenReturn(Optional.of(move));
    Mockito.when(pokemonRepository.save(pokemon)).thenReturn(pokemon);

    Pokemon pokemonDB = pokemonDAO.savePokemon(pokemon);

   assertThat(pokemonDB, instanceOf(Pokemon.class));
    assertThat(pokemonDB.getName(), equalTo("Nuevo Pokemon TEST"));




}


} 
