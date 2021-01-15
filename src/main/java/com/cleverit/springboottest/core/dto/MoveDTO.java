package com.cleverit.springboottest.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MoveDTO {

    Long id;

    String name;

    Integer power;

    Integer pp;

    Integer priority;

    Integer accuracy;

    TypeAttackDTO typeAttack;

    List<PokemonDTO> pokemon;
}
