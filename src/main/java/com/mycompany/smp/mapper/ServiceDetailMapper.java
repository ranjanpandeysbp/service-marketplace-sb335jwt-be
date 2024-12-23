package com.mycompany.smp.mapper;

import com.mycompany.smp.dto.ServiceRequestDTO;
import com.mycompany.smp.entity.ServiceEntity;
import com.mycompany.smp.service.ServiceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceDetailMapper {

    ServiceDetailMapper INSTANCE = Mappers.getMapper(ServiceDetailMapper.class);

    @Mapping(source = "mobile", target = "phone1")
    @Mapping(source = "altNumber", target = "phone2")
    //@Mapping(target = "active", constant = "true")
    @Mapping(target = "active", source = "active", defaultExpression = "java(java.lang.Boolean.TRUE)")
    @Mapping(target = "createdAt", source = "createdAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", source = "updatedAt", defaultExpression = "java(java.time.LocalDateTime.now())")
    ServiceEntity toEntity(ServiceRequestDTO requestDTO);

    @Mapping(source = "mobile", target = "phone1")
    @Mapping(source = "altNumber", target = "phone2")
    ServiceResponseDTO toDto(ServiceEntity serviceEntity);
}
