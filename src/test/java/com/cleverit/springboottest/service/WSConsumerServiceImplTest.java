package com.cleverit.springboottest.service;

import com.cleverit.springboottest.core.dto.MoveDTO;
import com.cleverit.springboottest.core.entity.Move;
import com.cleverit.springboottest.core.repository.MovesRepository;
import com.cleverit.springboottest.core.service.WSConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class WSConsumerServiceImplTest {

    @Autowired
    WSConsumerService wsConsumerService;

    @MockBean
    MovesRepository movesRepository;

    @MockBean
    RestTemplate restTemplate;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetAllMovimientosOK() throws Exception {

        String JSON = "{\n" +
                "  \"moves\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"pound\",\n" +
                "      \"power\": 40,\n" +
                "      \"pp\": 35,\n" +
                "      \"priority\": 0,\n" +
                "      \"accuracy\": 100,\n" +
                "      \"type\": {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"normal\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        List<Move> moves =  new ArrayList<Move>();

        Mockito.when(restTemplate.getForObject("https://pokemonapi-4gm7aqn32q-nn.a.run.app/moves/", String.class))
                .thenReturn(JSON);

        Mockito.when(movesRepository.saveAll(moves)).thenReturn(moves);

        String response = wsConsumerService.getAllMovimientos();

        assertThat(response, instanceOf(String.class));
        assertThat(response, equalTo("Movimientos y Tipos de ataque guardados"));
    }

    @Test
    public void testGetAllMovimientosError() throws Exception {

        Mockito.when(restTemplate.getForObject("https://pokemonapi-4gm7aqn32q-nn.a.run.app/moves/", String.class))
                .thenThrow(new RuntimeException());

        String response = wsConsumerService.getAllMovimientos();

        assertThat(response, instanceOf(String.class));
        assertThat(response, equalTo("A ocurrido un error, intente nuevamente."));
    }

} 
