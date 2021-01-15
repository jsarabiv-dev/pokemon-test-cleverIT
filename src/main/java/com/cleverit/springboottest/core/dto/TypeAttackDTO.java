package com.cleverit.springboottest.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TypeAttackDTO {

    Long id;

    String name;

    List<MoveDTO> move;


}
