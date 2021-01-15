package com.cleverit.springboottest.wsconsumer;

import com.cleverit.springboottest.core.service.WSConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("consumer")
public class WSConsumerController {

    private final WSConsumerService consumerService;

    @GetMapping("movimientos")
    @ResponseStatus(HttpStatus.OK)
    public String getAllMovimientos() throws IOException {
        return consumerService.getAllMovimientos();
    }

    @GetMapping("tipos")
    @ResponseStatus(HttpStatus.OK)
    public String getAllTipos(){
       return consumerService.getAlTypes();
    }


}
