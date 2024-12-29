package com.mycompany.smp.service;

import com.mycompany.smp.constant.ERole;
import com.mycompany.smp.dto.BusinessItemDTO;
import com.mycompany.smp.dto.BusinessRequestDTO;
import com.mycompany.smp.dto.BusinessResponseDTO;
import com.mycompany.smp.entity.*;
import com.mycompany.smp.mapper.BusinessDetailMapper;
import com.mycompany.smp.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class NoAuthServiceImpl {

    @Autowired
    private BusinessDetailRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private IndustryRepository industryRepository;

    public Long addBusinessItem(BusinessItemDTO businessItemDTO){

        return null;
    }
    public BusinessResponseDTO onboardBusiness(BusinessRequestDTO request) {
        BusinessDetailEntity se = BusinessDetailMapper.INSTANCE.toEntity(request);
        UserEntity ue = new UserEntity();
        ue.setEmail(request.getEmail1());
        ue.setPhone(request.getMobile());
        ue.setPassword("secret");
        Optional<RoleEntity> optRoleMe = roleRepository.findByName(ERole.ROLE_PROVIDER);
        if(optRoleMe.isPresent()){
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(optRoleMe.get());
            ue.setRoles(roles);
        }
        Optional<CategoryEntity> optCe = categoryRepository.findById(request.getCategoryId());
        if(optCe.isPresent()){
            se.setCategory(optCe.get());
        }
        Optional<IndustryTypeEntity> optIt = industryRepository.findById(request.getIndustryTypeId());
        if(optIt.isPresent()){
            se.setIndustryType(optIt.get());
        }
        Optional<BusinessTypeEntity> optBt = businessRepository.findById(request.getBusinessTypeId());
        if(optBt.isPresent()){
            se.setBusinessType(optBt.get());
        }
        ue = userRepository.save(ue);
        BusinessResponseDTO sr = null;
        se.setProvider(ue);
        se = serviceRepository.save(se);
        sr = new BusinessResponseDTO();
        BeanUtils.copyProperties(se, sr);
        return sr;
    }
}
