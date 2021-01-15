package com.cleverit.springboottest.core.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.cleverit.springboottest.core.dto.*;
import com.cleverit.springboottest.core.entity.*;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-14T14:22:12-0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
@Component
public class TypePokemonMapperImpl implements TypePokemonMapper {

    @Override
    public TypePokemonDTO TypePokemonToTypePokemonDTO(TypePokemon typePokemon) {
        if ( typePokemon == null ) {
            return null;
        }

        TypePokemonDTO typePokemonDTO = new TypePokemonDTO();

        typePokemonDTO.setId( typePokemon.getId() );
        typePokemonDTO.setName( typePokemon.getName() );
        typePokemonDTO.setPokemons( null );

        return typePokemonDTO;
    }

    protected List<MoveDTO> moveListToMoveDTOList(List<Move> list) {
        if ( list == null ) {
            return null;
        }

        List<MoveDTO> list1 = new ArrayList<MoveDTO>( list.size() );
        for ( Move move : list ) {
            list1.add( moveToMoveDTO( move ) );
        }

        return list1;
    }

    protected TypeAttackDTO typeAttackToTypeAttackDTO(TypeAttack typeAttack) {
        if ( typeAttack == null ) {
            return null;
        }

        TypeAttackDTO typeAttackDTO = new TypeAttackDTO();

        typeAttackDTO.setId( typeAttack.getId() );
        typeAttackDTO.setName( typeAttack.getName() );
        typeAttackDTO.setMove( moveListToMoveDTOList( typeAttack.getMove() ) );

        return typeAttackDTO;
    }

    protected List<PokemonDTO> pokemonListToPokemonDTOList(List<Pokemon> list) {
        if ( list == null ) {
            return null;
        }

        List<PokemonDTO> list1 = new ArrayList<PokemonDTO>( list.size() );
        for ( Pokemon pokemon : list ) {
            list1.add( pokemonToPokemonDTO( pokemon ) );
        }

        return list1;
    }

    protected MoveDTO moveToMoveDTO(Move move) {
        if ( move == null ) {
            return null;
        }

        MoveDTO moveDTO = new MoveDTO();

        moveDTO.setId( move.getId() );
        moveDTO.setName( move.getName() );
        moveDTO.setPower( move.getPower() );
        moveDTO.setPp( move.getPp() );
        moveDTO.setPriority( move.getPriority() );
        moveDTO.setAccuracy( move.getAccuracy() );
        moveDTO.setTypeAttack( typeAttackToTypeAttackDTO( move.getTypeAttack() ) );
        moveDTO.setPokemon( pokemonListToPokemonDTOList( move.getPokemon() ) );

        return moveDTO;
    }

    protected SpriteDTO spriteToSpriteDTO(Sprite sprite) {
        if ( sprite == null ) {
            return null;
        }

        SpriteDTO spriteDTO = new SpriteDTO();

        spriteDTO.setId( sprite.getId() );
        spriteDTO.setFrontDefault( sprite.getFrontDefault() );
        spriteDTO.setBackDefault( sprite.getBackDefault() );
        spriteDTO.setPokemon( pokemonToPokemonDTO( sprite.getPokemon() ) );

        return spriteDTO;
    }

    protected PokemonDTO pokemonToPokemonDTO(Pokemon pokemon) {
        if ( pokemon == null ) {
            return null;
        }

        PokemonDTO pokemonDTO = new PokemonDTO();

        pokemonDTO.setId( pokemon.getId() );
        pokemonDTO.setName( pokemon.getName() );
        pokemonDTO.setWeight( pokemon.getWeight() );
        pokemonDTO.setHeight( pokemon.getHeight() );
        pokemonDTO.setMoves( moveListToMoveDTOList( pokemon.getMoves() ) );
        pokemonDTO.setSprites( spriteToSpriteDTO( pokemon.getSprites() ) );
        pokemonDTO.setTypePokemon( TypePokemonToTypePokemonDTO( pokemon.getTypePokemon() ) );

        return pokemonDTO;
    }
}
