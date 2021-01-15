package com.cleverit.springboottest.core.repository;

import com.cleverit.springboottest.core.entity.TypePokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypePokemonRepository extends JpaRepository<TypePokemon, Long> {

    Optional<TypePokemon> findByName(String name);
}
