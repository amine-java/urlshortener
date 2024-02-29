package com.mbh.mapper;

import com.mbh.dto.ResourceUrlDTO;
import com.mbh.entity.ResourceUrlEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResourceUrlMapper {
    ResourceUrlMapper INSTANCE = Mappers.getMapper(ResourceUrlMapper.class);
    ResourceUrlDTO convert(ResourceUrlEntity resourceUrl);
}
