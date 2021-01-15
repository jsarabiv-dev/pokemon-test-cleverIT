package com.cleverit.springboottest.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SPRITES")
public class Sprite {

    @Id
    @Column(name = "SPRITE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Long id;

    @Column(name = "SPRITE_FRONT")
    String frontDefault;

    @Column(name = "SPRITE_BACK")
    String backDefault;

    @OneToOne(mappedBy = "sprites")
    Pokemon pokemon;


}
