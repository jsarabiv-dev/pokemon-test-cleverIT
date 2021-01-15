package com.cleverit.springboottest.core.mapper;

import com.cleverit.springboottest.core.dto.TypePokemonDTO;
import com.cleverit.springboottest.core.entity.TypePokemon;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TypePokemonMapper {

    TypePokemonMapper INSTANCIA= Mappers.getMapper(TypePokemonMapper.class);

    TypePokemonDTO TypePokemonToTypePokemonDTO(TypePokemon typePokemon);
}
