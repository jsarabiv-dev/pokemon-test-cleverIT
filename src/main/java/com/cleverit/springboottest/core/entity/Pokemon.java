package com.cleverit.springboottest.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "POKEMONS")
public class Pokemon {

    @Id
    @Column(name = "POKEMON_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Long id;

    @Column(name = "POKEMON_NAME")
    String name;

    @Column(name = "POKEMON_WEIGHT")
    Integer weight;

    @Column(name = "POKEMON_HEIGHT")
    Integer height;

    @ManyToMany(mappedBy = "pokemon", cascade = CascadeType.PERSIST)
    @JsonIgnore
    @Column(name = "MOVE_ID")
    List<Move> moves;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SPRITE_ID")
    @JsonIgnore
    Sprite sprites;

    @JoinColumn(name = "TP_ID")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    TypePokemon typePokemon;

}
