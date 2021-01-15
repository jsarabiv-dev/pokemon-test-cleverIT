package com.cleverit.springboottest.core.repository;

import com.cleverit.springboottest.core.entity.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovesRepository extends JpaRepository<Move,Long> {

    Optional<Move> findByName(String name);
}
