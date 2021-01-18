package com.cleverit.springboottest.wsconsumer;

import com.cleverit.springboottest.core.dto.MoveDTO;
import com.cleverit.springboottest.core.exception.ErrorMessage;
import com.cleverit.springboottest.core.service.WSConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Slf4j
@WebMvcTest(WSConsumerController.class)
public class WSConsumerControllerTest {

    @MockBean
    private WSConsumerService wsConsumerService;

    @Autowired
    private WSConsumerController wsConsumerController;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void before() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(wsConsumerController)
                .build();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetAllMovimientos200() throws Exception {

        Mockito.when(wsConsumerService.getAllMovimientos())
                .thenReturn("Movimientos y Tipos de ataque guardados");

        String contentAsString =
                mockMvc.perform(get("/consumer/movimientos").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        log.info(contentAsString);

        assertThat(contentAsString, instanceOf(String.class));
        assertThat(contentAsString, equalTo("Movimientos y Tipos de ataque guardados"));

    }

    @Test
    public void testGetAllMovimientosWhitErrorMsg() throws Exception {

        Mockito.when(wsConsumerService.getAllMovimientos())
                .thenReturn("A ocurrido un error, intente nuevamente.");

        String contentAsString =
                mockMvc.perform(get("/consumer/movimientos").header("Content-type", "application/json"))
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        log.info(contentAsString);

        assertThat(contentAsString, instanceOf(String.class));
        assertThat(contentAsString, equalTo("A ocurrido un error, intente nuevamente."));
    }

}
