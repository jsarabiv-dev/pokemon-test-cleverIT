package com.cleverit.springboottest.core.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
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
@Table(name = "MOVES")
public class Move {

    @Id
    @Column(name = "MOVE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Long id;

    @Column(name = "MOVE_NAME")
    String name;

    @Column(name = "MOVE_POWER")
    Integer power;

    @Column(name = "MOVE_PP")
    Integer pp;

    @Column(name = "MOVE_PRIORITY")
    Integer priority;

    @Column(name = "MOVE_ACCURACY")
    Integer accuracy;

    @JoinColumn(name = "TYPE_ID")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonAlias("type")
    TypeAttack typeAttack;


    @JoinColumn(name = "POKEMON_ID")
    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "POKEMON_ID")
    List<Pokemon> pokemon;


}
