package com.cleverit.springboottest.wsprovider;

import com.cleverit.springboottest.core.dto.MoveDTO;
import com.cleverit.springboottest.core.dto.PokemonDTO;
import com.cleverit.springboottest.core.dto.TypeAttackDTO;
import com.cleverit.springboottest.core.dto.TypePokemonDTO;
import com.cleverit.springboottest.core.service.PubSubService;
import com.cleverit.springboottest.core.service.WSProviderService;
import com.cleverit.springboottest.pubsub.PubSub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("provider")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class WSProviderController {


    private final PubSubService pubSubService;

    private final PubSub.ReportPubSubOutboundGateway messagingGateway;

    private final WSProviderService wsProviderService;

  /*  public WSProviderController(PubSubService pubSubService, PubSub.ReportPubSubOutboundGateway messagingGateway) {
        this.pubSubService = pubSubService;
        this.messagingGateway = messagingGateway;
    }*/


    @PostMapping("publish")
    public String publishMessage(@RequestBody String msg){
        messagingGateway.sendToPubsub(msg);
        return "Mensaje Enviado";
    }


    @GetMapping("moves")
    public List<MoveDTO> getMoves(){
        return wsProviderService.getMoves();
    }

    @GetMapping("moves/{id}")
    public MoveDTO getMoveById(@PathVariable String id){
        return wsProviderService.getMoveById(id);
    }

    @GetMapping("tipos-ataque")
    public  List<TypeAttackDTO> getPokemonAttacks(){
        return wsProviderService.getPokemonAttacks();
    }

    @GetMapping("tipos-ataque/{pokemonId}")
    public List<TypeAttackDTO> getPokemonAttacksById(@PathVariable String pokemonId){
        return wsProviderService.getPokemonAttacksById(pokemonId);
    }

    @GetMapping("tipos-pokemon")
    public List<TypePokemonDTO> getTypesPokemon(){
        return wsProviderService.getTypesPokemon();
    }

    @GetMapping("tipos-pokemon/{id}")
    public TypePokemonDTO getTypesPokemonById(@PathVariable String id){
        return wsProviderService.getTypePokemonById(id);
    }

    @GetMapping("pokemons")
    public List<PokemonDTO> getPokemons(){
        return wsProviderService.getPokemons();
    }

    @GetMapping("pokemon/{pokemonId}")
    public PokemonDTO getPokemonById(@PathVariable String pokemonId){
        return wsProviderService.getPokemonById(pokemonId);
    }















}
