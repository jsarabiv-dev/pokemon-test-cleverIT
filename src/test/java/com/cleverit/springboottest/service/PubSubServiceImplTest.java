package com.cleverit.springboottest.service;

import com.cleverit.springboottest.core.dao.PokemonDAO;
import com.cleverit.springboottest.core.entity.Pokemon;
import com.cleverit.springboottest.core.service.PubSubService;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class PubSubServiceImplTest {

    @MockBean
    PokemonDAO pokemonDAO;

    @Autowired
    PubSubService pubSubService;


    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSavePokemonOK() throws Exception {

        String JSON = "{\n" +
                "   \"id\":56,\n" +
                "   \"name\":\"mankey\",\n" +
                "   \"weight\":280,\n" +
                "   \"height\":5,\n" +
                "   \"types\":[\n" +
                "      {\n" +
                "         \"slot\":1,\n" +
                "         \"type\":{\n" +
                "            \"id\":2,\n" +
                "            \"name\":\"fighting\"\n" +
                "         }\n" +
                "      }\n" +
                "   ],\n" +
                "   \"moves\":[\n" +
                "      {\n" +
                "         \"move\":{\n" +
                "            \"id\":92,\n" +
                "            \"name\":\"toxic\"\n" +
                "         }\n" +
                "      },\n" +
                "      {\n" +
                "         \"move\":{\n" +
                "            \"id\":154,\n" +
                "            \"name\":\"fury-swipes\"\n" +
                "         }\n" +
                "      },\n" +
                "      {\n" +
                "         \"move\":{\n" +
                "            \"id\":249,\n" +
                "            \"name\":\"rock-smash\"\n" +
                "         }\n" +
                "      },\n" +
                "      {\n" +
                "         \"move\":{\n" +
                "            \"id\":104,\n" +
                "            \"name\":\"double-team\"\n" +
                "         }\n" +
                "      }\n" +
                "   ],\n" +
                "   \"sprites\":{\n" +
                "      \"frontDefault\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/56.png\",\n" +
                "      \"backDefault\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/56.png\"\n" +
                "   }\n" +
                "}";

        Pokemon pokemon = new Pokemon();
        pokemon.setId(123456789L);
        Mockito.when(pokemonDAO.savePokemon(pokemon)).thenReturn(new Pokemon());

        String response = pubSubService.savePokemon(JSON);

        assertThat(response, instanceOf(String.class));
        assertThat(response, equalTo("Pokemon Guardado"));


    }


} 
