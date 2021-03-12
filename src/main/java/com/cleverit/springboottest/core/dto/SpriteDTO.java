package com.cleverit.springboottest.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SpriteDTO {

    Long id;

    String frontDefault;

    String backDefault;

    @JsonIgnore
    PokemonDTO pokemon;

}
