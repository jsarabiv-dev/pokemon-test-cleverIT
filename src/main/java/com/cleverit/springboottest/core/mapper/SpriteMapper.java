package com.cleverit.springboottest.core.mapper;

import com.cleverit.springboottest.core.dto.SpriteDTO;
import com.cleverit.springboottest.core.entity.Sprite;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SpriteMapper {

    SpriteMapper INSTANCIA= Mappers.getMapper(SpriteMapper.class);

    SpriteDTO SpriteToSpriteDTO(Sprite sprite);
}
