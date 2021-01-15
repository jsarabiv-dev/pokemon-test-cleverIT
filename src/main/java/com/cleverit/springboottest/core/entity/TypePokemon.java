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
@Table(name = "TYPE_POKEMON")
public class TypePokemon {

    @Id
    @Column(name = "TP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Long id;

    @Column(name = "TP_NAME")
    String name;

    @OneToMany(mappedBy = "typePokemon")
    List<Pokemon> pokemons;

}
