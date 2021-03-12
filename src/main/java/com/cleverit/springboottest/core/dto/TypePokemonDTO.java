package com.cleverit.springboottest.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TypePokemonDTO {

    Long id;

    String name;

    @JsonIgnore
    List<PokemonDTO> pokemons;



}
