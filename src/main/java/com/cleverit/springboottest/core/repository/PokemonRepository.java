package com.cleverit.springboottest.core.repository;

import com.cleverit.springboottest.core.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    Pokemon findByName(String name);
}
