package com.cleverit.springboottest.core.mapper;

import com.cleverit.springboottest.core.dto.MoveDTO;
import com.cleverit.springboottest.core.entity.Move;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MoveMapper {


    MoveMapper INSTANCIA= Mappers.getMapper(MoveMapper.class);

    MoveDTO MoveToMoveDTO(Move move);


}
