package com.cleverit.springboottest.core.mapper;

import com.cleverit.springboottest.core.dto.TypeAttackDTO;
import com.cleverit.springboottest.core.entity.TypeAttack;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TypeAttackMapper {

    TypeAttackMapper INSTANCIA= Mappers.getMapper(TypeAttackMapper.class);

    TypeAttackDTO TypeAttackToTypeAttackDTO(TypeAttack typeAttack);
}
