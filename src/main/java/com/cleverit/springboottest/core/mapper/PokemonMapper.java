package com.cleverit.springboottest.core.mapper;

import com.cleverit.springboottest.core.dto.PokemonDTO;
import com.cleverit.springboottest.core.entity.Pokemon;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PokemonMapper {

    PokemonMapper INSTANCIA= Mappers.getMapper(PokemonMapper.class);

    PokemonDTO PokemonToPokemonDTO(Pokemon pokemon);
}
