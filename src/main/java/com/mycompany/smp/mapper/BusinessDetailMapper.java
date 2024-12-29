package com.mycompany.smp.mapper;

import com.mycompany.smp.dto.BusinessRequestDTO;
import com.mycompany.smp.entity.BusinessDetailEntity;
import com.mycompany.smp.dto.BusinessResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusinessDetailMapper {

    BusinessDetailMapper INSTANCE = Mappers.getMapper(BusinessDetailMapper.class);

    @Mapping(source = "mobile", target = "phone1")
    @Mapping(source = "altNumber", target = "phone2")
    //@Mapping(target = "active", constant = "true")
    @Mapping(target = "active", source = "active", defaultExpression = "java(java.lang.Boolean.TRUE)")
    @Mapping(target = "createdAt", source = "createdAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", source = "updatedAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    BusinessDetailEntity toEntity(BusinessRequestDTO requestDTO);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "businessType.name", target = "businessType")
    @Mapping(source = "industryType.name", target = "industryType")
    BusinessResponseDTO toDto(BusinessDetailEntity serviceEntity);
}
