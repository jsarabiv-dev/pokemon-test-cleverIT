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
@Table(name = "TYPES")
public class TypeAttack {

    @Id
    @Column(name = "TYPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Long id;

    @Column(name = "TYPE_NAME")
    String name;

    @OneToMany(mappedBy = "typeAttack", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Move> move;


}
