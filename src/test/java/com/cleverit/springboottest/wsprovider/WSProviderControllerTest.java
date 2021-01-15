package com.cleverit.springboottest.wsprovider;

import com.cleverit.springboottest.core.constant.ConstantMsgError;
import com.cleverit.springboottest.core.dto.MoveDTO;
import com.cleverit.springboottest.core.dto.PokemonDTO;
import com.cleverit.springboottest.core.dto.TypeAttackDTO;
import com.cleverit.springboottest.core.dto.TypePokemonDTO;
import com.cleverit.springboottest.core.exception.ErrorController;
import com.cleverit.springboottest.core.exception.ErrorMessage;
import com.cleverit.springboottest.core.service.PubSubService;
import com.cleverit.springboottest.core.service.WSProviderService;
import com.cleverit.springboottest.pubsub.PubSub;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@WebMvcTest(WSProviderController.class)
public class WSProviderControllerTest {

//    @Autowired


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WSProviderController wsProviderController;

    @MockBean
    private WSProviderService wsProviderService;

    @MockBean
    private PubSubService pubSubService;

    @MockBean
    private PubSub.ReportPubSubOutboundGateway messagingGateway;

    @Autowired
    private ErrorController errorController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Before
    public void before() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(wsProviderController)
                .setControllerAdvice(errorController)
                .build();
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void testPublishMessage() throws Exception {
        PubSub.ReportPubSubOutboundGateway spy = Mockito.spy(messagingGateway);
        Mockito.doNothing().when(spy).sendToPubsub("msg");
        String msgReturn = wsProviderController.publishMessage("msg");
        assertThat(msgReturn, instanceOf(String.class));
        assertThat(msgReturn, equalTo("Mensaje Enviado"));
    }


    @Test
    public void testGetMoves200() throws Exception {

        List<MoveDTO> moves = new ArrayList<>();
        moves.add(new MoveDTO());
        moves.add(new MoveDTO());
        moves.add(new MoveDTO());

        Mockito.when(wsProviderService.getMoves()).thenReturn(moves);

        String contentAsString =
                mockMvc.perform(get("/provider/moves").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        MoveDTO[] moveReponse = objectMapper.readValue(contentAsString, MoveDTO[].class);

        assertThat(moveReponse, instanceOf(MoveDTO[].class));
        assertThat(moveReponse.length, equalTo(3));
        assertThat(moveReponse[0], instanceOf(MoveDTO.class));


    }

    @Test
    public void testGetMoves500() throws Exception {

        Mockito.when(wsProviderService.getMoves()).thenThrow(new RuntimeException());

        String contentAsString =
                mockMvc.perform(get("/provider/moves").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);
        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(500));
        assertThat(errorMessageFromJson.getMessage(), equalTo("looks like something went wrong"));

    }


    @Test
    public void testGetMoveById200() throws Exception {

        MoveDTO move = new MoveDTO();

        Mockito.when(wsProviderService.getMoveById("1")).thenReturn(move);

        String contentAsString =
                mockMvc.perform(get("/provider/moves/1").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        MoveDTO moveReponse = objectMapper.readValue(contentAsString, MoveDTO.class);

        assertThat(moveReponse, instanceOf(MoveDTO.class));

    }

    @Test
    public void testGetMoveById500() throws Exception {

        Mockito.when(wsProviderService.getMoveById("1")).thenThrow(new RuntimeException());

        String contentAsString =
                mockMvc.perform(get("/provider/moves/1").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);
        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(500));
        assertThat(errorMessageFromJson.getMessage(), equalTo("looks like something went wrong"));

    }

    @Test
    public void testGetMoveById404() throws Exception {

        String id = "1";
        Mockito.when(wsProviderService.getMoveById(id)).thenThrow(new NoSuchElementException(ConstantMsgError.MOVE_NOT_FOUND + id));

        String contentAsString =
                mockMvc.perform(get("/provider/moves/" + id).header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);
        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(404));
        assertThat(errorMessageFromJson.getMessage(), equalTo(ConstantMsgError.MOVE_NOT_FOUND + id));
    }

    @Test
    public void testGetPokemonAttacks200() throws Exception {

        List<TypeAttackDTO> typeAttackDTOS = new ArrayList<>();
        typeAttackDTOS.add(new TypeAttackDTO());
        typeAttackDTOS.add(new TypeAttackDTO());
        typeAttackDTOS.add(new TypeAttackDTO());

        Mockito.when(wsProviderService.getPokemonAttacks()).thenReturn(typeAttackDTOS);

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-ataque").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        TypeAttackDTO[] TypeAttackDTOReponse = objectMapper.readValue(contentAsString, TypeAttackDTO[].class);

        assertThat(TypeAttackDTOReponse, instanceOf(TypeAttackDTO[].class));
        assertThat(TypeAttackDTOReponse.length, equalTo(3));
        assertThat(TypeAttackDTOReponse[0], instanceOf(TypeAttackDTO.class));

    }

    @Test
    public void testGetPokemonAttacks500() throws Exception {

        Mockito.when(wsProviderService.getPokemonAttacks()).thenThrow(new RuntimeException());

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-ataque").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);

        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(500));
        assertThat(errorMessageFromJson.getMessage(), equalTo("looks like something went wrong"));

    }

    @Test
    public void testGetPokemonAttacksById200() throws Exception {

        List<TypeAttackDTO> typeAttackDTOs = new ArrayList<>();
        typeAttackDTOs.add(new TypeAttackDTO());
        typeAttackDTOs.add(new TypeAttackDTO());
        typeAttackDTOs.add(new TypeAttackDTO());

        Mockito.when(wsProviderService.getPokemonAttacksById("1")).thenReturn(typeAttackDTOs);

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-ataque/1").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        TypeAttackDTO[] TypeAttackDTOReponse = objectMapper.readValue(contentAsString, TypeAttackDTO[].class);

        assertThat(TypeAttackDTOReponse, instanceOf(TypeAttackDTO[].class));
        assertThat(TypeAttackDTOReponse.length, equalTo(3));
        assertThat(TypeAttackDTOReponse[0], instanceOf(TypeAttackDTO.class));
    }

    @Test
    public void testGetPokemonAttacksById404() throws Exception {

        String id = "1";
        Mockito.when(wsProviderService.getPokemonAttacksById(id)).thenThrow(new NoSuchElementException("Pokemon with id " + id + " not found"));

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-ataque/" + id).header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);
        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(404));
        assertThat(errorMessageFromJson.getMessage(), equalTo("Pokemon with id " + id + " not found"));

    }

    @Test
    public void testGetPokemonAttacksById500() throws Exception {

        Mockito.when(wsProviderService.getPokemonAttacksById("1")).thenThrow(new RuntimeException());

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-ataque/1").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);

        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(500));
        assertThat(errorMessageFromJson.getMessage(), equalTo("looks like something went wrong"));

    }

    @Test
    public void testGetTypesPokemon200() throws Exception {

        List<TypePokemonDTO> typePokemonDTOS = new ArrayList<>();
        typePokemonDTOS.add(new TypePokemonDTO());
        typePokemonDTOS.add(new TypePokemonDTO());
        typePokemonDTOS.add(new TypePokemonDTO());

        Mockito.when(wsProviderService.getTypesPokemon()).thenReturn(typePokemonDTOS);

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-pokemon").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        TypePokemonDTO[] typePokemonDTOSReponse = objectMapper.readValue(contentAsString, TypePokemonDTO[].class);

        assertThat(typePokemonDTOSReponse, instanceOf(TypePokemonDTO[].class));
        assertThat(typePokemonDTOSReponse.length, equalTo(3));
    }

    @Test
    public void testGetTypesPokemon500() throws Exception {

        Mockito.when(wsProviderService.getTypesPokemon()).thenThrow(new RuntimeException());

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-pokemon").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);

        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(500));
        assertThat(errorMessageFromJson.getMessage(), equalTo("looks like something went wrong"));

    }

    @Test
    public void testGetTypesPokemonById200() throws Exception {

        TypePokemonDTO typePokemonDTO = new TypePokemonDTO();

        Mockito.when(wsProviderService.getTypePokemonById("1")).thenReturn(typePokemonDTO);

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-pokemon/1").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        TypePokemonDTO typePokemonDTOReponse = objectMapper.readValue(contentAsString, TypePokemonDTO.class);

        assertThat(typePokemonDTOReponse, instanceOf(TypePokemonDTO.class));

    }

    @Test
    public void testGetTypesPokemonById404() throws Exception {

        String id = "1";
        Mockito.when(wsProviderService.getTypePokemonById(id)).thenThrow(new NoSuchElementException("Pokemon with id " + id + " not found"));

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-pokemon/" + id).header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);
        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(404));

    }

    @Test
    public void testGetTypesPokemonById500() throws Exception {

        String id = "1";
        Mockito.when(wsProviderService.getTypePokemonById(id)).thenThrow(new RuntimeException());

        String contentAsString =
                mockMvc.perform(get("/provider/tipos-pokemon/" + id).header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);

        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(500));
        assertThat(errorMessageFromJson.getMessage(), equalTo("looks like something went wrong"));

    }

    @Test
    public void testGetPokemons200() throws Exception {

        List<PokemonDTO> pokemonDTOS = new ArrayList<>();
        pokemonDTOS.add(new PokemonDTO());
        pokemonDTOS.add(new PokemonDTO());
        pokemonDTOS.add(new PokemonDTO());

        Mockito.when(wsProviderService.getPokemons()).thenReturn(pokemonDTOS);

        String contentAsString =
                mockMvc.perform(get("/provider/pokemons").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        PokemonDTO[] pokemonDTOSReponse = objectMapper.readValue(contentAsString, PokemonDTO[].class);

        assertThat(pokemonDTOSReponse, instanceOf(PokemonDTO[].class));
        assertThat(pokemonDTOSReponse.length, equalTo(3));

    }



    @Test
    public void testGetPokemons500() throws Exception {

        Mockito.when(wsProviderService.getPokemons()).thenThrow(new RuntimeException());

        String contentAsString =
                mockMvc.perform(get("/provider/pokemons").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        ErrorMessage errorMessageFromJson = objectMapper.readValue(contentAsString, ErrorMessage.class);

        assertThat(errorMessageFromJson, instanceOf(ErrorMessage.class));
        assertThat(errorMessageFromJson.getStatusCode(), equalTo(500));
        assertThat(errorMessageFromJson.getMessage(), equalTo("looks like something went wrong"));

    }

    @Test
    public void testGetPokemonById200() throws Exception {




    }


} 
