package com.cleverit.springboottest.core.dto;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonDTO {

    Long id;

    String name;

    Integer weight;

    Integer height;

    List<MoveDTO> moves;

    SpriteDTO sprites;

    TypePokemonDTO typePokemon;
}
