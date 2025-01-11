package com.mycompany.smp.mapper;

import com.mycompany.smp.dto.BusinessItemDTO;
import com.mycompany.smp.dto.BusinessRequestDTO;
import com.mycompany.smp.dto.BusinessResponseDTO;
import com.mycompany.smp.entity.BusinessDetailEntity;
import com.mycompany.smp.entity.BusinessItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BusinessItemMapper {

    BusinessItemMapper INSTANCE = Mappers.getMapper(BusinessItemMapper.class);

    @Mapping(target = "createdAt", source = "createdAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", source = "updatedAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    BusinessItemEntity toEntity(BusinessItemDTO requestDTO);

    List<BusinessItemDTO> toDtoList(List<BusinessItemEntity> businessItemEntityList);
}
